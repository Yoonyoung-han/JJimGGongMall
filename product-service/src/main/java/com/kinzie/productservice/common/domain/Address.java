package com.kinzie.productservice.common.domain;

import com.kinzie.productservice.common.BaseEntity;
import com.kinzie.productservice.common.dto.response.AddressResponseDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "shipping_addresses")
@Slf4j
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_address_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String alias;
    private String recipientName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private Boolean isDefault;

    @Builder
    public Address(String alias, String recipientName, String addressLine1, String addressLine2, String city, String state, String postalCode,
                   String country, Boolean isDefault, User user) {
        this.alias = alias;
        this.recipientName = recipientName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.isDefault = isDefault;
        this.user = user;
    }


    // 연관관계 편의 메서드 -> Address 쪽에서 한번에 저장
    public void setUser(User user) {
        this.user = user;
    }


    // 주소를 한 줄의 문자열로 반환하는 메서드
    public String getSingleLineAddress() {
        StringBuilder sb = new StringBuilder();

        // 받는 사람, 우편번호, 서울시 구로구 구로동로 14 3-109
        sb.append(recipientName).append(", ")
                .append(postalCode).append(", ")
                .append(city).append(" ")
                .append(state).append(" ")
                .append(addressLine1).append(" ");

        if (addressLine2 != null) {
            sb.append(addressLine2);
        }
        return sb.toString();
    }

    public static Address of(AddressResponseDto responseDto){
        return Address.builder()
                .addressLine1(responseDto.getAddressLine1())
                .addressLine2(responseDto.getAddressLine2())
                .city(responseDto.getCity())
                .alias(responseDto.getAlias())
                .country(responseDto.getCountry())
                .postalCode(responseDto.getPostalCode())
                .state(responseDto.getState())
                .recipientName(responseDto.getRecipientName())
                .isDefault(responseDto.getIsDefault())
                .build();
    }
}
