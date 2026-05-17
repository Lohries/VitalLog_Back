package com.vitallog.vitalog_back.domain.enums;

public enum Priority {
    ALTA("Alta"),
    MEDIA("Média"),
    BAIXA("Baixa");

    private final String label;

    Priority(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
