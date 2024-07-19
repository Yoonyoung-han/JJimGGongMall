package com.kinzie.productservice.common.domain;

import com.kinzie.productservice.common.BaseEntity;
import com.kinzie.productservice.common.domain.constant.Role;
import com.kinzie.productservice.common.dto.response.UserResponseDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Slf4j
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String accountName;
    private String email;
    private String username;
    private String password;
    private String phoneNumber;
    private String birthday;
    private String gender;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // 양방향 설정 , 빈열로 초기화
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @Builder
    public User(String accountName, String username, String email, String password, String phoneNumber,
                String birthday, String gender) {
        this.accountName = accountName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.gender = gender;
        this.role = Role.USER;
    }

    // Member <-> Address 연관관계 편의 메서드
    public void addAddress(Address address) {
        if (!this.addresses.contains(address)) {
            this.addresses.add(address);
        }
        address.setUser(this);
    }

    public static User of(UserResponseDto response){
        User user = User.builder()
                .accountName(response.getAccountName())
                .birthday(response.getBirthday())
                .email(response.getEmail())
                .gender(response.getGender())
                .phoneNumber(response.getPhoneNumber())
                .username(response.getUsername())
                .build();

        user.setId(response.getUserId());
        return user;
    }

}
