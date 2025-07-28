package com.clotheswarehouse.model;

public enum Role {
    ADMIN("Admin"),
    WAREHOUSE_EMPLOYEE("Warehouse Employee"),
    REGULAR_USER("Regular User");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
