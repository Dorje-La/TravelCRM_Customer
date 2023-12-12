package com.weekytripstravelcrm.repository;

import com.weekytripstravelcrm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Manages customer registration data in the database.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String > {
    /**
     * Find a customer by email.
     * @param email The email address to search for.
     * @return The associated customer entity, or null if not found.
     */
    Customer findByEmail(String email);

    /**
     * Check if a customer with the given mobile number exists.
     * @param mobileNumber The mobile number to check.
     * @return True if a matching customer exists, otherwise false.
     */
    boolean existsByMobileNumber(String mobileNumber);

    /**
     * Get the maximum customer registration ID.
     * @return The highest registration ID, or null if no records exist.
     */
    @Query("SELECT MAX(CAST(SUBSTRING(c.customerID, 4) AS long)) FROM Customer c")
    Long findMaxCustomerID();

}
