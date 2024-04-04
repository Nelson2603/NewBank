package org.example;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;


@Data
@AllArgsConstructor


public class User {
    private String name;
    private String numberFone;
    Double balance;

    public User(Double balance) {
        this.balance = 100.0;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(numberFone, user.numberFone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberFone);
    }

    public void deposit(double amount) {
        this.balance += amount;
    }
}
