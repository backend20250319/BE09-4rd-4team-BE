package olive.oliveyoung.member.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import olive.oliveyoung.member.order.entity.OrderItems;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {

    private Long orderItemId;
    private Long productId;
    private String brandName;
    private String productName;
    private Integer discountedPrice;
    private Integer quantity;

    public static OrderItemResponse from(OrderItems item) {
        OrderItemResponse res =  new OrderItemResponse();
        res.setOrderItemId(item.getOrderItemId());
        res.setProductId(item.getProduct().getProductId());
        res.setBrandName(item.getProduct().getBrand().getBrandName());
        res.setProductName(item.getProduct().getProductName());
        res.setDiscountedPrice(item.getProduct().getDiscountedPrice());
        res.setQuantity(item.getQuantity());
        return res;
    }
}
