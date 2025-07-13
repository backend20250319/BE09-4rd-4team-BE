package olive.oliveyoung.member.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import olive.oliveyoung.member.user.domain.Address;

@Getter
@Builder
public class AddressResponse {
    private Long addressId;
    private String addressName;
    private String recipientName;
    private String phone;
    private String streetAddress;
    private String detailAddress;
    private boolean isDefault;

    public static AddressResponse from(Address address) {
        return AddressResponse.builder()
                .addressId(address.getAddressId())
                .addressName(address.getAddressName())
                .recipientName(address.getRecipientName())
                .phone(address.getPhone())
                .streetAddress(address.getStreetAddress())
                .detailAddress(address.getDetailAddress())
                .isDefault(address.isDefault())
                .build();
    }
}
