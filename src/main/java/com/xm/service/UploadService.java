package com.xm.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.xm.exception.XMException;
import com.xm.model.Symbol;
import com.xm.model.Tick;
import com.xm.repository.PriceRepository;
import com.xm.repository.SymbolRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
// TODO service could be split to separate parsing, validation and adding to database
public class UploadService {

    private static final String COLUMN_SEPARATOR = ",";
    private final PriceRepository priceRepository;
    private final SymbolRepository symbolRepository;

    record FileEntry(String timestamp, String symbol, String price) {
    }

    @Transactional
    public void uploadFile(InputStream inputStream) {

        var fileEntries = read(inputStream).stream()
                // TODO add validation for any non consistent entries and throw exception, add all file or nothing
                .filter(fileEntry -> StringUtils.isNumeric(fileEntry.timestamp()))
                .toList();

        var nameToSymbol = fileEntries.stream()
                .map(FileEntry::symbol)
                .distinct()
                .map(symbol -> Optional.ofNullable(symbolRepository.findByName(symbol))
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
        Instant instant = Instant.ofEpochMilli(Long.valueOf(timestampInMillis));
        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
        return localDateTime;
    }

    public static List<FileEntry> read(InputStream stream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8.name()));
            return reader.lines()
                    .map(line -> line.split(COLUMN_SEPARATOR))
                    .filter(line -> line.length == 3)
                    .map(strings -> new FileEntry(strings[0], strings[1], strings[2]))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new XMException(e);
        }
    }

}
