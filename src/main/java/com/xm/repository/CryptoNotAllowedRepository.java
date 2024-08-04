package com.xm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xm.entity.CryptoNotAllowed;

public interface CryptoNotAllowedRepository extends JpaRepository<CryptoNotAllowed, Long> {

    CryptoNotAllowed findByName(String name);
}
