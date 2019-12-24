import CSVparser.TransactionAnalyze;
import CSVparser.TransactionParser;

import java.io.IOException;

public class Main {

    public final static String TABLE_PARSING_REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    public final static String COST_DESCRIPTION_REGEX = "\\d{2}\\.\\d{2}\\.\\d{2}\\s+\\d{2}\\.\\d{2}\\.\\d{2}\\s+.*";
    public final static String COST_DESCRIPTION_REGEX2 = "\\d+\\++\\d+\\s+";



    public static void main(String[] args) throws IOException {

        TransactionParser parser = new TransactionParser ();

        TransactionAnalyze.printParsedBankStatement(parser.parseData("sources/movementList.csv"));
        TransactionAnalyze.printNotValidLines(parser.parseData("sources/movementList.csv"));
        TransactionAnalyze.printParsedSortedBankStatement(parser.parseData("sources/movementList.csv"));
        TransactionAnalyze.getTotalCost(parser.parseData("sources/movementList.csv"));
        TransactionAnalyze.getTotalIncome(parser.parseData("sources/movementList.csv"));
        TransactionAnalyze.getIncomeSummary (parser.parseData("sources/movementList.csv"));
        TransactionAnalyze.getCostSummary(parser.parseData("sources/movementList.csv"));


           } }





