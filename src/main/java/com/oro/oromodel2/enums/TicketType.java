package com.oro.oromodel2.enums;

public enum TicketType {
    ADULT("Adult"),
    CHILD("Child"),
    STUDENT("Student");

    private final String displayName;

    TicketType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
