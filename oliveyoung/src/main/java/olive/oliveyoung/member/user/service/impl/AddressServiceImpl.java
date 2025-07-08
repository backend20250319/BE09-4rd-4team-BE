package olive.oliveyoung.member.user.service.impl;

import lombok.RequiredArgsConstructor;
import olive.oliveyoung.member.user.domain.Address;
import olive.oliveyoung.member.user.domain.User;
import olive.oliveyoung.member.user.dto.request.AddressRequest;
import olive.oliveyoung.member.user.dto.response.AddressResponse;
import olive.oliveyoung.member.user.repository.AddressRepository;
import olive.oliveyoung.member.user.repository.UserRepository;
import olive.oliveyoung.member.user.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addAddress(String userId, AddressRequest addressRequest) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Address address = Address.builder()
                .addressName(addressRequest.getAddressName())
                .recipientName(addressRequest.getRecipientName())
                .phone(addressRequest.getPhone())
                .zipcode(addressRequest.getZipcode())
                .streetAddress(addressRequest.getStreetAddress())
                .streetNumber(addressRequest.getStreetNumber())
                .detailAddress(addressRequest.getDetailAddress())
                .isDefault(addressRequest.isDefault())
                .build();

        user.addAddress(address);
        addressRepository.save(address);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponse> getAddresses(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return user.getAddresses().stream()
                .map(AddressResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateAddress(String userId, Long addressId, AddressRequest addressRequest) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("주소를 찾을 수 없습니다."));

        if (!address.getUser().equals(user)) {
            throw new IllegalArgumentException("권한이 없는 사용자입니다.");
        }

        address.updateAddress(
                addressRequest.getAddressName(),
                addressRequest.getRecipientName(),
                addressRequest.getPhone(),
                addressRequest.getZipcode(),
                addressRequest.getStreetAddress(),
                addressRequest.getStreetNumber(),
                addressRequest.getDetailAddress(),
                addressRequest.isDefault()
        );
    }

    @Override
    @Transactional
    public void deleteAddress(String userId, Long addressId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("주소를 찾을 수 없습니다."));

        if (!address.getUser().equals(user)) {
            throw new IllegalArgumentException("권한이 없는 사용자입니다.");
        }

        addressRepository.delete(address);
    }

    @Override
    public void setDefaultAddress(String userId, Long addressId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Address newDefaultAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("주소를 찾을 수 없습니다."));

        if (!newDefaultAddress.getUser().equals(user)) {
            throw new IllegalArgumentException("권한이 없는 사용자입니다.");
        }

        user.getAddresses().forEach(address -> {
            if (address.isDefault()) {
                address.setDefault(false);
            }
        });

        newDefaultAddress.setDefault(true);
    }
}