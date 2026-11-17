package com.mystic.hydra.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "urls")
@Getter
@Setter
@Builder
public class UrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String originalUrl;

    @Column(unique = true)
    private String shortenUrl;

}
