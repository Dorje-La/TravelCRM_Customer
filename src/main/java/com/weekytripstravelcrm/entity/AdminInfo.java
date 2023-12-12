package com.weekytripstravelcrm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;

import javax.persistence.*;

/**
 * This is entity class for admin detail
 * The class for Admin Registration and Login purpose.
 */

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="Admin_info")
public class AdminInfo {
    @Id
    private String adminId;
    @Column
    @NonNull
    private String adminFirstName;
    @Column
    @NonNull
    private String adminLastName;
    @Column(unique = true)
    @NonNull
    private String adminEmail;
    @Column
    @NonNull
    private String mobile;
    @Column
    @NonNull
    private String password;
    @Column
    @NonNull
    private String confirmPassword;
    @Column
    private String roll;
    @Column
    private Boolean isActive;
}
