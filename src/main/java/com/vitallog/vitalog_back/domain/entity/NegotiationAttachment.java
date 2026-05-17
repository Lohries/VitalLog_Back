package com.vitallog.vitalog_back.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "negociacao_anexos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NegotiationAttachment {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "negociacao_id")
    private Negotiation negotiation;

    @Column(name = "nome_arquivo", length = 200)
    private String name;

    @Column(name = "tamanho_bytes")
    private Long size;
}
