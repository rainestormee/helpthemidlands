package com.hackthemidlands.processblinders.api;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING(0, "Pending"), ACCEPTED(1, "Accepted"), COMPLETED(2, "Completed"), CANCELLED(3, "Cancelled");

    int i;
    String string;

    OrderStatus(int i, String string) {
        this.i = i;
        this.string = string;
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

    public int getNum() {
        return this.i;
    }
}
