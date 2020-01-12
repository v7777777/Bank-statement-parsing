package CSVparser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class TransactionParser {

    public final static String TABLE_PARSING_REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    public final static String COST_DESCRIPTION_REGEX = "\\d{2}\\.\\d{2}\\.\\d{2}\\s+\\d{2}\\.\\d{2}\\.\\d{2}\\s+.*";
    public final static String COST_DESCRIPTION_REGEX2 = "\\d+\\++\\d+\\s+";
    public final static String DATE_FORMAT = "dd.MM.yy";
    public final static DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);



    public TransactionParseResult parseData (String path) throws IOException {

        List<String> lines = Files.readAllLines(Paths.get(path));
        ArrayList<Transaction> structuredTable = new ArrayList<Transaction>();
        ArrayList<String> notValidLines = new ArrayList<String>();
        ArrayList<String[]> header = new ArrayList<String[]>();

        for (int i = 1; i < lines.size(); i++) {

            if (lines.get(i).contains("\"")) {

            String [] columns = lines.get(i).split(TABLE_PARSING_REGEX);

            if (columns.length != 8) {notValidLines.add(lines.get(i)); continue;}

            addTransaction(structuredTable, columns);

            structuredTable.get(i-1).type = structuredTable.get(i-1).setType(getOperationType(columns [6]));

            }

            else {

            String [] columns = lines.get(i).split(",");

             if (columns.length != 8) {notValidLines.add(lines.get(i)); continue;}

              addTransaction(structuredTable, columns);

             structuredTable.get(i-1).type = structuredTable.get(i-1).setType(getOperationType(columns [6]));

            }

        }

        parseHeader(path, header);

        TransactionParseResult parseResult = new TransactionParseResult(structuredTable, notValidLines, header);

        return parseResult;
    }

    private ArrayList<String[]> parseHeader (String path, ArrayList<String[]> header) throws IOException {

        List<String> lines = Files.readAllLines(Paths.get(path));

                String [] columns = lines.get(0).split(",");

                if (columns.length != 8) { System.out.println("wrong header format"); return header; } // пустые наименования столбцов

                header.add(columns);
                return header; }

    private String parseCounterparty (String counterparty) {

        if (counterparty.contains("/")) { counterparty = counterparty.substring((counterparty.lastIndexOf("/")+1)).trim().toUpperCase();}
        if (counterparty.contains("\\")) {counterparty = counterparty.substring((counterparty.lastIndexOf("\\")+1)).trim().toUpperCase();}
        counterparty = counterparty.replaceAll(COST_DESCRIPTION_REGEX, "");
        counterparty = counterparty.replaceAll(COST_DESCRIPTION_REGEX2, "").trim();

        return counterparty;
    }

    private ArrayList<Transaction> addTransaction (ArrayList<Transaction> parsedLines, String [] columns) {



            parsedLines.add(new Transaction(columns [1], //  String accountNumber
                       Currency.getInstance(columns [2]),  //  Currency currency
                            LocalDate.parse(columns [3], format), // LocalDate transactionDate
                                            columns [4], // String mccCode
                          parseCounterparty(columns [5]), // String counterparty
                                parseAmount(columns [6], columns [7]) // long amount income [6] or cost [7] rubs and cents
                                  ));



        return parsedLines;
    }

    private long parseAmount (String income, String cost) {

        long rubly;
        long kopeyki;

        String amount = getNotNullAmount (income, cost);

        amount = amount.replaceAll("\"", "").trim();

        if (amount.contains(","))
        {
            rubly = Long.parseLong(amount.substring(0,amount.indexOf(",")));
            String cents = amount.substring(amount.indexOf(",") + 1);
            if (cents.length() == 1) {cents = cents + "0";}
            kopeyki = Long.parseLong(cents);

            return rubly*100+kopeyki;

        }
        else {rubly =  Long.parseLong(amount);

            return rubly*100;

        }

    }

    private String getNotNullAmount (String income, String cost) {

        income = income.trim();
        cost = cost.trim();

        if (cost.equals("0")  ) {return income; }
        else {return cost; }


    }

    private String getOperationType (String income) {

        income = income.trim();
        income = income.replaceAll("\"", "");

        String type = "";

        if (income.equals("0")  )
        { type ="cost";
        return type; }
        else {type ="income"; return type ; }
    }



}