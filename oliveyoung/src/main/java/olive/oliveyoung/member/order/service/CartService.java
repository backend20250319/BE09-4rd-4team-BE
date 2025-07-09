package olive.oliveyoung.member.order.service;

import olive.oliveyoung.member.order.dto.CartItemRequest;
import olive.oliveyoung.member.order.dto.CartItemResponse;
import olive.oliveyoung.member.order.entity.CartItems;
import olive.oliveyoung.member.order.entity.Carts;
import olive.oliveyoung.member.order.repository.CartItemRepository;
import olive.oliveyoung.member.order.repository.CartRepository;
import olive.oliveyoung.member.product.entity.Products;
import olive.oliveyoung.member.product.repository.ProductRepository;
import olive.oliveyoung.member.user.domain.User;
import olive.oliveyoung.member.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(UserRepository userRepository,
                       ProductRepository productRepository,
                       CartRepository cartRepository,
                       CartItemRepository cartItemRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    // 장바구니에 상품 추가
    public CartItemResponse addCartItem(CartItemRequest request) {

        // 1. 사용자 찾기
        User user = userRepository.findByUserId(String.valueOf(request.getUserId()))
                .orElseThrow(() -> new RuntimeException("해당 사용자는 존재하지 않습니다."));

        // 2. 상품 찾기
        Products product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("해당 상품은 존재하지 않습니다."));

        // 3. 사용자의 창바구니 찾기
        Carts cart = cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(Carts.create(user)));

        // 4. 기존 장바구니에 같은 상품이 담겨 있는지 확인
        Optional<CartItems> optionalItem = cartItemRepository.findByCartAndProduct(cart, product);

        CartItems savedItem;
        if (optionalItem.isPresent()) {
            // 4-1. 같은 상품이 있을 시, 수량만 증가
            CartItems existingItem = optionalItem.get();
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            savedItem = cartItemRepository.save(existingItem);
        } else {
            // 4-2. 같은 상품이 없을 시, 새로 추가
            CartItems newItem = new CartItems();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(request.getQuantity());
            savedItem = cartItemRepository.save(newItem);
        }

        return CartItemResponse.from(savedItem);
    }

    // 사용자별 장바구니 조회
    public List<CartItemResponse> getCartItems(String userId) {

        // 1. 유저 조회
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("해당 사용자는 존재하지 않습니다."));

        // 2. 장바구니 조회
        Carts cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("장바구니가 존재하지 않습니다."));

        // 3. 해당 장바구니의 모든 상품 정보 가져오기
        List<CartItems> cartItems = cartItemRepository.findByCart(cart);

        // 4. 응답 DTO로 변환
        return cartItems.stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList());
    }
}
