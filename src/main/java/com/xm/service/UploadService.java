package com.xm.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.xm.model.Tick;
import com.xm.model.Symbol;
import com.xm.repository.PriceRepository;
import com.xm.repository.SymbolRepository;
import com.xm.util.CsvUtils;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UploadService {

    private final PriceRepository priceRepository;
    private final SymbolRepository symbolRepository;

    record FileEntry(String timestamp, String symbol, String price) {

    }

    @Transactional
    public void uploadFile(InputStream inputStream) {
        var fileEntries = CsvUtils.read(FileEntry.class, inputStream).stream()
                .filter(fileEntry -> StringUtils.isBlank(fileEntry.price) || StringUtils.isBlank(fileEntry.symbol) || StringUtils.isBlank(fileEntry.timestamp))
                .toList();

        var nameToSymbol = fileEntries.stream()
                .map(FileEntry::symbol)
                .map(symbol -> Optional.of(symbolRepository.findByName(symbol))
                        .orElse(symbolRepository.save(Symbol.builder().name(symbol).build())))
                .collect(Collectors.toMap(Symbol::getName, Function.identity()));

        fileEntries.stream()
                .forEach(fileEntry -> priceRepository.save(
                        Tick.builder()
                        .symbol(nameToSymbol.get(fileEntry.symbol()))
                        .dateTime(convertTimestamp(fileEntry.timestamp()))
                        .price(new BigDecimal(fileEntry.price()))
                        .build()
                ));

    }

    private LocalDateTime convertTimestamp(String timestampInMillis) {
        Instant instant = Instant.ofEpochMilli(Long.getLong(timestampInMillis));
        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
        return localDateTime;
    }


}
