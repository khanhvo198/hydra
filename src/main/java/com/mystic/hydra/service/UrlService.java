package com.mystic.hydra.service;

import com.mystic.hydra.entity.UrlEntity;
import com.mystic.hydra.repository.UrlRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class UrlService {

    private static final String ALPHABET =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int CODE_LENGTH = 7;

    private final SecureRandom random = new SecureRandom();

    private final UrlRepository urlRepository;


    @Transactional
    public String shorten(String url) {
        String code;
        do {
            code = randomCode();
        } while (urlRepository.existsByShortenUrl(code));

        urlRepository.save(UrlEntity.builder().shortenUrl(code).originalUrl(url).build());
        return code;
    }

    private String randomCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}
