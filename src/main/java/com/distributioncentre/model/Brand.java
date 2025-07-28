package com.distributioncentre.model;

public enum Brand {
    BALENCIAGA("Balenciaga"),
    STONE_ISLAND("Stone Island"),
    DIOR("Dior"),
    GUCCI("Gucci"),
    PRADA("Prada"),
    VERSACE("Versace"),
    ARMANI("Armani"),
    CHANEL("Chanel");

    private final String displayName;

    Brand(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
