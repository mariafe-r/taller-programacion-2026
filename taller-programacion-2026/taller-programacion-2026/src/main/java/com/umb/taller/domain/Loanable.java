package com.umb.taller.domain;

public interface Loanable {
    void borrow();

    void returnItem();

    boolean isAvailable();

    int getMaxLoanDays();
}

