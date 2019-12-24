package CSVparser;

import java.util.ArrayList;
import java.util.List;

public class TransactionParseResult {   // результат CSVparser.TransactionParser .parseData

    private ArrayList<Transaction> transactions;
    private ArrayList<String> notValidLines;
    private ArrayList<String[]> header;

    public TransactionParseResult(ArrayList<Transaction> transactions, ArrayList<String> notValidLines, ArrayList<String[]> header) {
        this.transactions = transactions;
        this.notValidLines = notValidLines;
        this.header = header;
    }

    public List<Transaction> getTransactionList () {
        return transactions;
    }

    public List<String> getNotValidLines () {
        return notValidLines;
    }

    public List<String[]> getHeader () {return header;}


}
