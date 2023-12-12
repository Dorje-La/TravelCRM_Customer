package com.weekytripstravelcrm.repository;

import com.weekytripstravelcrm.entity.AdminInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<AdminInfo, String> {
    @Override
    Optional<AdminInfo> findById(String s);

    Optional<AdminInfo> findByAdminEmail(String userName);
    List<AdminInfo> findAll();
    @Query("SELECT MAX(CAST(SUBSTRING(A.adminId, 6) AS long)) FROM AdminInfo A")
    Long findMaxAdminIdAsLong();
}
