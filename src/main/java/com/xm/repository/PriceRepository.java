package com.xm.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xm.entity.Tick;
import com.xm.repository.model.SymbolMinMax;

public interface PriceRepository extends JpaRepository<Tick, Long> {

    @Query("SELECT new com.xm.repository.model.SymbolMinMax(t.symbol.name, MIN(t.price), MAX(t.price)) FROM Tick AS t GROUP BY t.symbol")
    List<SymbolMinMax> findSymbolMinMax();

    @Query("SELECT new com.xm.repository.model.SymbolMinMax(t.symbol.name, MIN(t.price), MAX(t.price)) FROM Tick AS t WHERE t.dateTime BETWEEN :from AND :to GROUP BY t.symbol")
    List<SymbolMinMax> findSymbolMinMaxBetweenDates(LocalDateTime from, LocalDateTime to);

}
