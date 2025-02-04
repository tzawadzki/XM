package com.xm.service;

import static com.xm.service.TestUtils.FILE_BTC;
import static com.xm.service.TestUtils.FILE_DOGE;
import static com.xm.service.TestUtils.FILE_ETH;
import static com.xm.service.TestUtils.FILE_LTC;
import static com.xm.service.TestUtils.FILE_XRP;
import static com.xm.service.TestUtils.getFileInputStream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.xm.entity.CryptoNotAllowed;
import com.xm.exception.XMException;
import com.xm.repository.CryptoNotAllowedRepository;
import com.xm.repository.SymbolRepository;
import com.xm.repository.TickRepository;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
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
        uploadService.uploadFile(getFileInputStream(FILE_BTC));
        uploadService.uploadFile(getFileInputStream(FILE_DOGE));
        uploadService.uploadFile(getFileInputStream(FILE_ETH));
        uploadService.uploadFile(getFileInputStream(FILE_LTC));
        uploadService.uploadFile(getFileInputStream(FILE_XRP));

        assertThat(symbolRepository.count()).isEqualTo(5);
        assertThat(tickRepository.count()).isEqualTo(450);
    }

    @Test
    void testNotAllowed() {
        cryptoNotAllowedRepository.save(CryptoNotAllowed.builder().name("BTC").build());

        Exception exception = assertThrows(XMException.class, () -> {
            uploadService.uploadFile(getFileInputStream(FILE_BTC));
        });

        assertTrue(exception.getMessage().contains("Crypto not allowed: BTC"));
    }

    // TODO add more test with uploading same file, inconsistent data, changed columns etc
}
