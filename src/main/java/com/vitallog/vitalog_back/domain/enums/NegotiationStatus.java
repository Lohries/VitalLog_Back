package com.vitallog.vitalog_back.domain.enums;

public enum NegotiationStatus {
    EM_CRIACAO("Em criação"),
    FATURADO("Faturado");

    private final String label;

    NegotiationStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
