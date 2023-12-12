package com.weekytripstravelcrm.repository;

import com.weekytripstravelcrm.entity.AdminInfo;
import com.weekytripstravelcrm.entity.Agent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentRepository extends CrudRepository<Agent, Integer> {
    List<Agent> findAll();
    Agent findByEmail(String email);
}
