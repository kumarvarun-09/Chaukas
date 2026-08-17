package com.chaukas.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String name;
    private String email;
    private String phone;

    User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

}
