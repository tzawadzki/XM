package com.xm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.xm.model.Tick;

public interface PriceRepository extends JpaRepository<Tick, Long> {

}
