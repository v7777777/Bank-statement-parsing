package CSVparser;

import java.time.LocalDate;
import java.util.Currency;

public class Transaction {

    // Тип счёта,Номер счета,Валюта,Дата операции,Референс проводки,Описание операции,Приход руб и коп ,Расход руб и коп

    private String accountNumber;   // [1]
    private Currency currency;  // [2]
    private LocalDate transactionDate;  // [3]
    private String MccCode; // [4]
    private String counterparty;  // [5]
    private long incomeR; // [6]
    private int incomeC; // [7]
    private long costR; // [8]
    private int costC; // [9]

    public Transaction(String accountNumber, Currency currency,
                       LocalDate transactionDate, String mccCode,
                       String counterparty, long incomeR, int incomeC, long costR, int costC) {

        this.accountNumber = accountNumber;
        this.currency = currency;
        this.transactionDate = transactionDate;
        this.MccCode = mccCode;
        this.counterparty = counterparty;
        this.incomeR = incomeR;
        this.incomeC = incomeC;
        this.costR = costR;
        this.costC = costC;
    }

    public String getAccountNumber () {
        return accountNumber;
    }
    public Currency getCurrency () {
        return currency;
    }
    public LocalDate getTransactionDate () {
        return transactionDate;
    }
    public String getCounterparty () {
        return counterparty;
    }
    public String MccCode () {
        return MccCode;
    }

    public double getCost () {
        double cost = (double) costR + (double) costC/100;
        return cost;
    }
    public double getIncome () {
        double income = (double) incomeR + (double) incomeC/100;
        return income;
    }
}
