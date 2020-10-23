package com.hackthemidlands.processblinders.api;

public enum OrderStatus {
    PENDING(0), ACCEPTED(1), COMPLETED(2), CANCELLED(3);
    int i;

    OrderStatus(int i) {
        this.i = i;
    }

    public OrderStatus getFromCode(int j) {
        switch (j) {
            case 0:
                return PENDING;
            case 1:
                return ACCEPTED;
            case 2:
                return COMPLETED;
            case 3:
                return CANCELLED;
            default:
                return null;
        }
    }
}
