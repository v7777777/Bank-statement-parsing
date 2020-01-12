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
    private long amount; // [6]   6 и 7 в копейках

    protected Type type;

    public Transaction(String accountNumber, Currency currency,
                       LocalDate transactionDate, String mccCode,
                       String counterparty, long amount) {

        this.accountNumber = accountNumber;
        this.currency = currency;
        this.transactionDate = transactionDate;
        this.MccCode = mccCode;
        this.counterparty = counterparty;
        this.amount = amount;

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

    public double getAmount () {
        return (double) amount/100;
    }

    public double getCost () {
        if (this.type == Type.COST ) { return getAmount (); }
        else {return 0;}
    }

    public double getIncome () {
        if (this.type == Type.INCOME ) { return getAmount (); }
        else {return 0;}
    }

    public Type setType (String type) {

        if (type.equals("income")) { return Type.INCOME; }
        else  {return Type.COST;}
    }













}
