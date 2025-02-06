package org.some.eshop.repository;

import org.some.eshop.model.Customer;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepositoryImplementation<Customer, Long> {

    Optional<Customer> findByUsername(String username);
}
