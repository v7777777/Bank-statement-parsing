package CSVparser;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;

public class TransactionAnalyze {

    public final static String PRINT_HEADER = "%25s%11s%17s%20s%31s%11s%11s%n";
    public final static String PRINT_PARSED_BANK_STATEMENT_FORMAT = "%25s%11s%17s%20s%31s%11.2f%11.2f%n";
    public final static String PRINT_COST_SUMMARY_FORMAT = "%-30s%-10.2f%n";
    public final static String DATE_FORMAT = "dd.MM.yy";

    public static void printParsedBankStatement(TransactionParseResult result) {

        for (String[] header: result.getHeader()) { System.out.printf(PRINT_HEADER, header[1], header[2], header[3], header[4], header[5],header[6], header[7]);}

        for (int i = 0; i < result.getTransactionList().size(); i++ ) {

            System.out.printf(PRINT_PARSED_BANK_STATEMENT_FORMAT,
                    result.getTransactionList().get(i).getAccountNumber(),
                    result.getTransactionList().get(i).getCurrency(),
                    result.getTransactionList().get(i).getTransactionDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                    result.getTransactionList().get(i).MccCode(),
                    result.getTransactionList().get(i).getCounterparty(),
                    result.getTransactionList().get(i).getIncome(),
                    result.getTransactionList().get(i).getCost());
        }
    }

    public static void printParsedSortedBankStatement(TransactionParseResult result) {

        sort (result);
        printParsedBankStatement(result);


    }

    private static void sort (TransactionParseResult result) { Collections.sort(result.getTransactionList(), new Comparator< Transaction >() {
        public int compare(Transaction o1, Transaction o2) {
            return o1.getCounterparty().compareTo(o2.getCounterparty());
        }
    });}

    public static void printNotValidLines(TransactionParseResult result) {
     if(result.getNotValidLines().isEmpty()) {
         System.out.println("all lines are valid"); return;
     }
     for(String s:result.getNotValidLines())
    { System.out.println(s); } }

    public static void getTotalCost (TransactionParseResult result) {


        double totalCost = result.getTransactionList().stream().map(t -> t.getCost()).reduce((a, b) -> a+b).orElseGet(() -> 0.0);
        System.out.printf("Total costs %7.2f RUR %n", totalCost);
    }

    public static void getTotalIncome (TransactionParseResult result) {

        double totalIncome = result.getTransactionList().stream().map(t -> t.getIncome()).reduce((a, b) -> a+b).orElseGet(() -> 0.0);
        System.out.printf("Total income %7.2f RUR %n ", totalIncome);
    }

    public static void getCostSummary (TransactionParseResult result) {

        System.out.println("*********Costs summary******************");
        sort (result);
        String temp = "";  // предыдущее название
        double totalAmount = 0; // сумма по расходу

        for (int x = 0; x < result.getTransactionList().size(); x++)

        {

            if ((result.getTransactionList().get(x).getCost() == 0) && !(x == (result.getTransactionList().size() - 1))) {continue;}

            if (temp.equals(result.getTransactionList().get(x).getCounterparty()) || x == 0 ) {
                totalAmount = totalAmount + result.getTransactionList().get(x).getCost();
                temp = result.getTransactionList().get(x).getCounterparty();
                if (x == (result.getTransactionList().size() - 1) ) { System.out.printf(PRINT_COST_SUMMARY_FORMAT, temp ,totalAmount);}}

            else {
                if (totalAmount != 0) {System.out.printf(PRINT_COST_SUMMARY_FORMAT, temp ,totalAmount);}
                totalAmount = 0;
                totalAmount = totalAmount + result.getTransactionList().get(x).getCost();
                temp = result.getTransactionList().get(x).getCounterparty();
                if (x == (result.getTransactionList().size() - 1) && totalAmount != 0 ) { System.out.printf(PRINT_COST_SUMMARY_FORMAT, temp ,totalAmount);   }} }

    }

    public static void getIncomeSummary (TransactionParseResult result) {

        System.out.println("*********Incomes summary*********");
        sort (result);
        String temp = "";  // предыдущее название
        double totalAmount = 0; // сумма по расходу

        for (int x = 0; x < result.getTransactionList().size(); x++)

        {

              if ((result.getTransactionList().get(x).getIncome() == 0) && !(x == (result.getTransactionList().size() - 1))) {continue;}

              if (temp.equals(result.getTransactionList().get(x).getCounterparty()) || x == 0 ) {
                totalAmount = totalAmount + result.getTransactionList().get(x).getIncome();
                temp = result.getTransactionList().get(x).getCounterparty();
                if (x == (result.getTransactionList().size() - 1) ) { System.out.printf(PRINT_COST_SUMMARY_FORMAT, temp ,totalAmount);}}

              else {
               if (totalAmount != 0) {System.out.printf(PRINT_COST_SUMMARY_FORMAT, temp ,totalAmount);}
                totalAmount = 0;
                totalAmount = totalAmount + result.getTransactionList().get(x).getIncome();
                temp = result.getTransactionList().get(x).getCounterparty();
                if (x == (result.getTransactionList().size() - 1) && totalAmount != 0 ) { System.out.printf(PRINT_COST_SUMMARY_FORMAT, temp ,totalAmount);   }} }

             }









}






