package CSVparser;

public enum Type {

    COST("cost"),
    INCOME("income");

    private String type;

    Type (String type) {
        this.type = type;
    }

    public void getTitleForSummary () {

        if (this.type == "cost") {System.out.printf("*********%ss summary*************%n", this.type );}
        else if (this.type == "income") {System.out.printf("*********%ss summary**********%n", this.type );}

    }


    




}
