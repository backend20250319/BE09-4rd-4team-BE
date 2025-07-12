package olive.oliveyoung.member.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import olive.oliveyoung.member.order.entity.CartItems;
import olive.oliveyoung.member.product.entity.Badges;

import java.util.List;
import java.util.stream.Collectors;

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
    private String imageUrl;
    private List<String> badges;

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
        res.setImageUrl(item.getProduct().getImageUrl());

        res.setBadges(
                item.getProduct().getBadges()
                        .stream()
                        .map(Badges::getName)
                        .collect(Collectors.toList())
        );
        return res;
    }
}
