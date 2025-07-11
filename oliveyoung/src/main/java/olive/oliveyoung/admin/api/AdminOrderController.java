package olive.oliveyoung.admin.api;

import lombok.RequiredArgsConstructor;
import olive.oliveyoung.member.order.dto.OrderResponse;
import olive.oliveyoung.member.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    // 관리자: 전체 주문 목록 조회
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrdersForAdmin() {
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}
