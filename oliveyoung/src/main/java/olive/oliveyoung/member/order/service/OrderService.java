package olive.oliveyoung.member.order.service;

import olive.oliveyoung.member.order.controller.OrderIdGenerator;
import olive.oliveyoung.member.order.dto.OrderItemResponse;
import olive.oliveyoung.member.order.dto.OrderRequest;
import olive.oliveyoung.member.order.dto.OrderResponse;
import olive.oliveyoung.member.order.entity.*;
import olive.oliveyoung.member.order.repository.CartItemRepository;
import olive.oliveyoung.member.order.repository.CartRepository;
import olive.oliveyoung.member.order.repository.OrderRepository;
import olive.oliveyoung.member.user.domain.Address;
import olive.oliveyoung.member.user.domain.User;
import olive.oliveyoung.member.user.repository.AddressRepository;
import olive.oliveyoung.member.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;

    private final OrderStatusScheduler orderStatusScheduler;

    public OrderService(UserRepository userRepository,
                        AddressRepository addressRepository,
                        CartRepository cartRepository,
                        CartItemRepository cartItemRepository,
                        OrderRepository orderRepository,
                        OrderStatusScheduler orderStatusScheduler) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderStatusScheduler = orderStatusScheduler;
    }

    // 배송/주문 정보 생성하기
    public OrderResponse createOrder(String userId, OrderRequest request) {

        // 사용자 조회
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("해당 사용자는 존재하지 않습니다."));

        // 배송지 조회
        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new RuntimeException("해당 배송지는 존재하지 않습니다."));

        // 장바구니 조회
        Carts cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("해당 장바구니는 존재하지 않습니다."));

        // 선택된 cartItems들만 조회
        List<CartItems> selectedItems = cartItemRepository.findAllById(request.getCartItemIds());

        if (selectedItems.isEmpty()) {
            throw new RuntimeException("선택된 상품이 없습니다.");
        }

        Orders order = new Orders();
        order.setOrderId(OrderIdGenerator.generate());
        order.setUser(user);
        order.setAddress(address);
        order.setStatus(DeliveryStatus.RECEIVED);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItems> orderItems = selectedItems.stream()
                .map(cartItem -> new OrderItems(
                        null,
                        order,
                        cartItem.getProduct(),
                        cartItem.getQuantity()
                ))
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);

        Orders savedOrder = orderRepository.save(order);
        orderStatusScheduler.changeStatusAfterDelay(savedOrder.getOrderId());

        // 선택된 cartItem만 장바구니에서 제거
        cart.getCartItems().removeAll(selectedItems); // 객체 관계 제거
        cartItemRepository.deleteAll(selectedItems);  // DB에서 제거

        List<OrderItemResponse> itemResponse = savedOrder.getOrderItems().stream()
                .map(OrderItemResponse::from)
                .collect(Collectors.toList());

        return new OrderResponse(
                savedOrder.getOrderId(),
                savedOrder.getCreatedAt(),
                savedOrder.getStatus(),
                itemResponse);

    }

    // 배송/주문 정보 조회하기
    public List<OrderResponse> getOrders(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("해당 사용자는 존재하지 않습니다."));

        List<Orders> orderList = orderRepository.findAllByUser(user);

        return orderList.stream()
                .map(order -> {
                    List<OrderItemResponse> itemResponses = order.getOrderItems().stream()
                            .map(OrderItemResponse::from)
                            .collect(Collectors.toList());

                    return new OrderResponse(
                            order.getOrderId(),
                            order.getCreatedAt(),
                            order.getStatus(),
                            itemResponses
                    );
                })
                .collect(Collectors.toList());
    }
}
