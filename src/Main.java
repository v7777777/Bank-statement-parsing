import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public final static String TABLE_PARSING_REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    public final static String COST_DESCRIPTION_REGEX = "\\d{2}\\.\\d{2}\\.\\d{2}\\s+\\d{2}\\.\\d{2}\\.\\d{2}\\s+.*";
    public final static String COST_DESCRIPTION_REGEX2 = "\\d+\\++\\d+\\s+";



    public static void main(String[] args) throws IOException {

        ArrayList<String[]> structuredTable = new ArrayList<String[]>();
        List<String> lines = Files.readAllLines(Paths.get("sources/movementList.csv"));

        for (int i = 0; i < lines.size(); i++ )
        {
            if (lines.get(i).contains("\"")) {String [] columns = lines.get(i).split(TABLE_PARSING_REGEX); structuredTable.add(columns); }
                                        else {String [] columns = lines.get(i).split(","); structuredTable.add(columns);}

            if (structuredTable.get(i).length != 8) { System.out.println("Wrong line: " + lines.get(i)); continue; }}

        // в structuredTable хранятся массивы 1 массив одна строка, элемент массива это ячейка

        for (String [] st : structuredTable)
        {
            st[6] = st[6].replaceAll("\"", "");
            st[6] = st[6].replaceAll(",", "\\.");
            st[7] = st[7].replaceAll("\"", "");
            st[7] = st[7].replaceAll(",", "\\.");
        }

        for (int j = 0; j< structuredTable.size(); j++)
        {
            structuredTable.get(j)[5] = structuredTable.get(j)[5].replaceAll(COST_DESCRIPTION_REGEX, "");
            structuredTable.get(j)[5] = structuredTable.get(j)[5].replaceAll(COST_DESCRIPTION_REGEX2, "");

            if (structuredTable.get(j)[5].contains("/")) { structuredTable.get(j)[5] = structuredTable.get(j)[5].substring((structuredTable.get(j)[5].lastIndexOf("/")+1)).trim().toUpperCase();}
            if (structuredTable.get(j)[5].contains("\\")) {structuredTable.get(j)[5] = structuredTable.get(j)[5].substring((structuredTable.get(j)[5].lastIndexOf("\\")+1)).trim().toUpperCase();}
        }

        for (String [] st : structuredTable)
        { System.out.printf("%11s%25s%11s%17s%20s%31s%11s%11s%n", st[0], st[1], st[2], st[3], st[4], st[5], st[6], st[7]); }

        Collections.sort(structuredTable, new Comparator< String [] >() {
            public int compare(String [] o1, String [] o2) {
                return o1[5].toString().compareTo(o2[5].toString());
            }
        });

        structuredTable.add(0, structuredTable.get(structuredTable.size()-1));
        structuredTable.remove(structuredTable.size()-1);


        System.out.println("потрачено по категориям");

       String temp = "";  // предыдущее название
       double totalAmount = 0; // сумма по расходу

       for (int x = 1; x < structuredTable.size(); x++)
       {
         if (temp.equals(structuredTable.get(x)[5]) || x == 1 ) {
               totalAmount = totalAmount + Double.parseDouble(structuredTable.get(x)[7].trim());
               temp = structuredTable.get(x)[5];
               if (x == (structuredTable.size() - 1) ) { System.out.printf("%-30s%-10.2f%n", temp ,totalAmount);}}

         else {
               System.out.printf("%-30s%-10.2f%n", temp ,totalAmount);
               totalAmount = 0;
               totalAmount = totalAmount + Double.parseDouble(structuredTable.get(x)[7]);
               temp = structuredTable.get(x)[5];}
               if (x == (structuredTable.size() - 1) ) { System.out.printf("%-30s%-10.2f%n", temp ,totalAmount);}}

        System.out.print("всего потрачено ");

        structuredTable.stream().
                filter(t -> (t[7].trim()).
                matches("\\d+\\.*\\d+")).
                map(p -> Double.parseDouble(p[7].trim())).
                reduce((a, b) -> a+b).ifPresent(System.out::println);


           } }





