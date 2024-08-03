package com.xm.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.xm.entity.Tick;
import com.xm.repository.model.OpenHighLowClose;
import com.xm.repository.model.SymbolMinMax;

public interface PriceRepository extends JpaRepository<Tick, Long> {

    @Query("SELECT new com.xm.repository.model.SymbolMinMax(t.symbol.name, MIN(t.price), MAX(t.price)) FROM Tick AS t GROUP BY t.symbol")
    List<SymbolMinMax> findSymbolMinMax();

    /**
     * Calculates min, max price and groups by symbol
     *
     * @param from inclusive
     * @param to exclusive
     */
    @Query("SELECT new com.xm.repository.model.SymbolMinMax(t.symbol.name, MIN(t.price), MAX(t.price)) FROM Tick AS t WHERE :from >= t.dateTime AND t.dateTime < :to GROUP BY t.symbol")
    List<SymbolMinMax> findSymbolMinMaxBetweenDates(LocalDateTime from, LocalDateTime to);

    @Query("SELECT new com.xm.repository.model.OpenHighLowClose("
            + "(SELECT to.price from Tick to WHERE to.symbol.name = :symbol ORDER BY to.dateTime LIMIT 1), "
            + "MAX(t.price), "
            + "MIN(t.price), "
            + "(SELECT tc.price from Tick tc WHERE tc.symbol.name = :symbol ORDER BY tc.dateTime DESC LIMIT 1)) "
            + "FROM Tick AS t WHERE t.symbol.name = :symbol")
    OpenHighLowClose findOHLCBySymbol(String symbol);

    @Query("SELECT new com.xm.repository.model.OpenHighLowClose("
            + "(SELECT to.price from Tick to WHERE to.symbol.name = :symbol AND :from >= to.dateTime AND to.dateTime < :to ORDER BY to.dateTime LIMIT 1), "
            + "MAX(t.price), "
            + "MIN(t.price), "
            + "(SELECT tc.price from Tick tc WHERE tc.symbol.name = :symbol AND :from >= tc.dateTime AND tc.dateTime < :to ORDER BY tc.dateTime DESC LIMIT 1)) "
            + "FROM Tick AS t WHERE t.symbol.name = :symbol AND :from >= t.dateTime AND t.dateTime < :to")
    OpenHighLowClose findOHLCBySymbolBetweenDates(String symbol, LocalDateTime from, LocalDateTime to);
}
