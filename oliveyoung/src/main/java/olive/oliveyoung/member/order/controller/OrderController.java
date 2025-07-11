package olive.oliveyoung.member.order.controller;

import olive.oliveyoung.member.order.dto.OrderRequest;
import olive.oliveyoung.member.order.dto.OrderResponse;
import olive.oliveyoung.member.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 1. CREATE: 배송/주문 정보 생성하기
    @PostMapping("/{userId}")
    public ResponseEntity<OrderResponse> createOrder(
            @PathVariable String userId,
            @RequestBody OrderRequest request
    ){
        OrderResponse response = orderService.createOrder(userId, request);
        return ResponseEntity.ok(response);
    }

    // 2. Read: 배송/주문 정보 조회하기
    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrders(@PathVariable String userId) {
        List<OrderResponse> orders = orderService.getOrders(userId);
        return ResponseEntity.ok(orders);
    }
}
