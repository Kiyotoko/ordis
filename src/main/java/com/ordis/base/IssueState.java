package com.ordis.base;

public enum IssueState {
    OPEN, FAILURE, CLOSED;

    public boolean isOpen() {
        return this == OPEN;
    }

    public boolean isClosed() {
        return this == CLOSED;
    }

}
