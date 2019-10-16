package com.badrul.qnitibox;

public class Inasis {

    //private int userid;
    private int inasisID;
    private String inasisName;
    private String inasisLocation;
    private String inasisLogo;


    public Inasis(int inasisID, String inasisName,String inasisLocation,String inasisLogo) {

        this.inasisID = inasisID;
        this.inasisName = inasisName;
        this.inasisLocation = inasisLocation;
        this.inasisLogo = inasisLogo;

    }

    public int getInasisID() {
        return inasisID;
    }

    public String getInasisName() {
        return inasisName;
    }

    public String getInasisLocation() { return inasisLocation; }

    public String getInasisLogo() { return inasisLogo; }

}
