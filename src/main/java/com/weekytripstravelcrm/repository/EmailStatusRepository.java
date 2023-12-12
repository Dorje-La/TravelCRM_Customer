package com.weekytripstravelcrm.repository;

import com.weekytripstravelcrm.email.EmailStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EmailStatusRepository extends CrudRepository<EmailStatus, String> {
    @Query("SELECT MAX(CAST(SUBSTRING(e.emailStatusId, 3) AS long)) FROM EmailStatus e")
    Long findMaxEmailStatusIdAsLong();

    EmailStatus findByEmailStatusId(String emailStatusId);
}
