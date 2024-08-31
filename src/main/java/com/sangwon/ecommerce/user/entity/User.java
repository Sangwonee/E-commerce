package com.sangwon.ecommerce.user.entity;

import com.sangwon.ecommerce.global.audit.Timestamped;
import com.sangwon.ecommerce.order.entity.Order;
import com.sangwon.ecommerce.user.dto.UserCreateRequestDto;
import com.sangwon.ecommerce.wishlist.entity.Wishlist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String name;
    private String password;
    private String address;
    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private Wishlist wishlist;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public User(UserCreateRequestDto userCreateRequestDto) {
        this.email = userCreateRequestDto.getEmail();
        this.name = userCreateRequestDto.getName();
        this.password = userCreateRequestDto.getPassword();
        this.address = userCreateRequestDto.getAddress();
        this.phoneNumber = userCreateRequestDto.getPhoneNumber();
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void encryptSensitiveData(BytesEncryptor aesBytesEncryptor) {
        this.name = encrypt(this.name, aesBytesEncryptor);
        this.address = encrypt(this.address, aesBytesEncryptor);
        this.email = encrypt(this.email, aesBytesEncryptor);
    }

    public void decryptSensitiveData(BytesEncryptor aesBytesEncryptor) {
        this.name = decrypt(this.name, aesBytesEncryptor);
        this.address = decrypt(this.address, aesBytesEncryptor);
        this.email = decrypt(this.email, aesBytesEncryptor);
    }

    private String encrypt(String data, BytesEncryptor encryptor) {
        return Base64.getEncoder().encodeToString(encryptor.encrypt(data.getBytes()));
    }

    private String decrypt(String encryptedData, BytesEncryptor encryptor) {
        return new String(encryptor.decrypt(Base64.getDecoder().decode(encryptedData)));
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
