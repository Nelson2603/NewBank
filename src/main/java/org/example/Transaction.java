package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class Transaction {
    private final User sender;
    private final User recipient;
    private final  double amount;
    private final LocalDateTime transactionTime;


    @Override
    public String toString() {
        return "Отправитель : " + sender + ", Получатель " + recipient + ", Сумма : " + amount + '}';
    }
}
