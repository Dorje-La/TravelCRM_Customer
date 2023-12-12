package com.weekytripstravelcrm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "customer_order")
@Getter
@Setter
@NoArgsConstructor
public class CustomerOrderEntity {
    @Id
    @Column(name = "customer_order_id")
    private String customerOrderId;

    @Column
    private LocalDateTime orderDateTime;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "package_id")
    private PackageEntity packageEntity;

    @Column(name = "final_price")
    private Double finalPrice;

}
