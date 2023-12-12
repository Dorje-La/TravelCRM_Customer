package com.weekytripstravelcrm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="agent_info")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Column
    @NonNull
    private String agencyName;
    @Column
    @NonNull
    private String address1;
    @Column
    private String address2;
    @Column
    private String city;
    @Column
    private String state;
    @Column
    @NonNull
    private String country;
    @Column
    @NonNull
    private String email;
    @Column
    @NonNull
    private String contact;
    @Column
    @NonNull
    private String managerName;
    @Column
    @NonNull
    private String password;
    @Column
    private String roll;
    @Column
    private String document;
}
