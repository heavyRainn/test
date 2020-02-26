package com.epam.interview.repository;

import com.epam.interview.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
