package com.mystic.hydra.controller;
import com.mystic.hydra.api.UrlsApi;


import com.mystic.hydra.api.model.CreateUrlRequest;
import com.mystic.hydra.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UrlsController implements UrlsApi {


    private final UrlService urlService;

    @Override
    public ResponseEntity<String> createShortUrl(CreateUrlRequest createUrlRequest) {
        return ResponseEntity.ok(urlService.shorten(createUrlRequest.getUrl()));
    }
}
