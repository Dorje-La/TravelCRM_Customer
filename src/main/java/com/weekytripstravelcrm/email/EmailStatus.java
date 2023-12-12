package com.weekytripstravelcrm.email;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="emailStatus")
@Getter
@Setter
public class EmailStatus {
    @Id
    @Column
    private String emailStatusId;
    @Column
    private String toEmailAddress;
    @Column
    private String fromEmailAddress;
    @Column
    private String subject;
    @Column
    private String status;
}
