package com.xm.util;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.xm.repository.SymbolRepository;
import com.xm.service.UploadService;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Profile("init")
@AllArgsConstructor
@Service
public class InitDataService {

    private final UploadService uploadService;
    private final SymbolRepository symbolRepository;

    @PostConstruct
    void init() {
        if (symbolRepository.count() == 0) {
            uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/BTC_values.csv"));
            uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/DOGE_values.csv"));
            uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/ETH_values.csv"));
            uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/LTC_values.csv"));
            uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/XRP_values.csv"));
        }
    }
}
