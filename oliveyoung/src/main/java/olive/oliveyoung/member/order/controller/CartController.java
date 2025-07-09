package olive.oliveyoung.member.order.controller;

import olive.oliveyoung.member.order.dto.CartItemRequest;
import olive.oliveyoung.member.order.dto.CartItemResponse;
import olive.oliveyoung.member.order.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 1. CREATE: 사용자 장바구니에 물품 넣기
    @PostMapping("/items")
    public ResponseEntity<CartItemResponse> addCartItem(@RequestBody CartItemRequest request) {
        CartItemResponse response = cartService.addCartItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // READ: 사용자 장바구니 물품 읽어오기
    @GetMapping("/items/{userId}")
    public ResponseEntity<List<CartItemResponse>> getCartItems(@PathVariable String userId) {
        List<CartItemResponse> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }

    // UPDATE: 사용자 장바구니 물품 수량 수정하기

    // DELETE: 사용자 장바구니 물품 삭제하기
}
