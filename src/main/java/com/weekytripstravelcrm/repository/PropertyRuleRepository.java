package com.weekytripstravelcrm.repository;

import com.weekytripstravelcrm.entity.PropertyRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRuleRepository extends JpaRepository<PropertyRule, Long> {
    PropertyRule findByRuleId(long ruleId);


}
