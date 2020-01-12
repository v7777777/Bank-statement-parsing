package CSVparser;

public enum Type {

    COST("cost"),
    INCOME("income");

    protected String type;


    Type (String type) {
        this.type = type;
    }

    public void getTitleForSummary () {

        if (this.type == "cost") {System.out.printf("*********%ss summary*************%n", this.type );}
        else if (this.type == "income") {System.out.printf("*********%ss summary**********%n", this.type );}

    }


    public Type setType (String type) {

        if (type.equals("income")) { return INCOME; }
        else  {return COST;}
    }
    




}
