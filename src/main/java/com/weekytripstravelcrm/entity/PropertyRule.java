package com.weekytripstravelcrm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="property_rule")
public class PropertyRule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ruleId;
    @Column
    private String ruleTopics;
    @Column
    private String description;
}
