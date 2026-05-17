package com.vitallog.vitalog_back.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "preferencias_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreferences {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private User user;

    @Column(name = "moeda")
    private String currency;

    @Column(name = "tema")
    private String theme;

    @Column(name = "notificacoes_ativas")
    private Boolean notificationsEnabled;

    public String getCurrency() { return currency != null ? currency : "R$"; }
    public String getTheme() { return theme != null ? theme : "light"; }
    public boolean isNotificationsEnabled() { return notificationsEnabled == null || notificationsEnabled; }
}
