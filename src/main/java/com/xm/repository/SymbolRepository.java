package com.xm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xm.entity.Symbol;

public interface SymbolRepository extends JpaRepository<Symbol, Long> {

    Symbol findByName(String name);

}
