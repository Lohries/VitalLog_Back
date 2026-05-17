package com.vitallog.vitalog_back.domain.entity;

import com.vitallog.vitalog_back.domain.enums.NegotiationStatus;
import com.vitallog.vitalog_back.domain.enums.Priority;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "negociacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Negotiation {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private User user;

    @Column(name = "cliente", length = 150)
    private String client;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String description;

    @Column(name = "valor_estimado", precision = 10, scale = 2)
    private BigDecimal estimatedValue;

    @Column(name = "atendimento", columnDefinition = "TEXT")
    private String attendance;

    @Column(name = "nf", length = 50)
    private String nf;

    @Column(name = "xml_nfe", columnDefinition = "TEXT")
    private String xml;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    @Builder.Default
    private NegotiationStatus status = NegotiationStatus.EM_CRIACAO;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade", length = 10)
    @Builder.Default
    private Priority priority = Priority.MEDIA;

    @Column(name = "servico_id", columnDefinition = "uuid")
    private UUID linkedServiceId;

    @Column(name = "data_criacao")
    private LocalDateTime createdAt;

    @Column(name = "data_faturamento")
    private LocalDateTime invoicedAt;

    @OneToMany(mappedBy = "negotiation", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<NegotiationAttachment> attachments = new ArrayList<>();

    @PrePersist
    void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}
