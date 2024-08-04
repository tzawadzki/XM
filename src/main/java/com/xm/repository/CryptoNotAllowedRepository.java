package com.xm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.xm.entity.CryptoNotAllowed;

@RepositoryRestResource
public interface CryptoNotAllowedRepository extends JpaRepository<CryptoNotAllowed, Long> {

    CryptoNotAllowed findByName(String name);
}
