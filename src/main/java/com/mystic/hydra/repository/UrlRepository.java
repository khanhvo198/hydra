package com.mystic.hydra.repository;

import com.mystic.hydra.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.net.URLEncoder;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, UUID> {
    Optional<UrlEntity> findByShortenUrl(String shortenUrl);
    boolean existsByShortenUrl(String shortenUrl);
}
