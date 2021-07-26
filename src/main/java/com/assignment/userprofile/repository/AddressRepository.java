package com.assignment.userprofile.repository;

import com.assignment.userprofile.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
