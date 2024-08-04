package com.xm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xm.entity.CryptoNotAllowed;
import com.xm.exception.XMException;
import com.xm.repository.CryptoNotAllowedRepository;
import com.xm.repository.SymbolRepository;
import com.xm.repository.TickRepository;

@SpringBootTest
class UploadServiceTests {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private SymbolRepository symbolRepository;

    @Autowired
    private TickRepository tickRepository;

    @Autowired
    private CryptoNotAllowedRepository cryptoNotAllowedRepository;

    @Test
    void testUpload() {
        uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/BTC_values.csv"));
        uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/DOGE_values.csv"));
        uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/ETH_values.csv"));
        uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/LTC_values.csv"));
        uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/XRP_values.csv"));

        assertThat(symbolRepository.count()).isEqualTo(5);
        assertThat(tickRepository.count()).isEqualTo(450);
    }

    @Test
    void testNotAllowed() {
        cryptoNotAllowedRepository.save(CryptoNotAllowed.builder().name("BTC").build());

        Exception exception = assertThrows(XMException.class, () -> {
            uploadService.uploadFile(getClass().getClassLoader().getResourceAsStream("files/BTC_values.csv"));
        });

        assertTrue(exception.getMessage().contains("Crypto not allowed: BTC"));
    }

    // TODO add more test with uploading same file, inconsistent data, changed columns etc
}
