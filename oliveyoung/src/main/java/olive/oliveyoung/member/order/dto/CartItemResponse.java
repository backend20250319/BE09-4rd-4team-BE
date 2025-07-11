package olive.oliveyoung.member.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import olive.oliveyoung.member.order.entity.CartItems;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {

    private Long cartItemId;
    private String userId;
    private Long productId;
    private String brandName;
    private String productName;
    private Integer originalPrice;
    private Integer discountedPrice;
    private Integer quantity;

    public static CartItemResponse from(CartItems item) {
        CartItemResponse res =  new CartItemResponse();
        res.setCartItemId(item.getCartItemId());
        res.setUserId(item.getCart().getUser().getUserId());
        res.setProductId(item.getProduct().getProductId());
        res.setBrandName(item.getProduct().getBrand().getBrandName());
        res.setProductName(item.getProduct().getProductName());
        res.setOriginalPrice(item.getProduct().getOriginalPrice());
        res.setDiscountedPrice(item.getProduct().getDiscountedPrice());
        res.setQuantity(item.getQuantity());
        return res;
    }
}
