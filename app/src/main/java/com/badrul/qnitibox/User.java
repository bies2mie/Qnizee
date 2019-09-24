package com.badrul.qnitibox;

public class User {

    //private int userid;
    private int userID;
    private String matrixID;
    private String nameID;
    private String phoneID;
    private String emailID;


    public User(int userID, String matrixID, String nameID, String phoneID, String emailID) {

        this.userID = userID;
        this.nameID = nameID;
        this.matrixID = matrixID;
        this.phoneID = phoneID;
        this.emailID = emailID;

    }

    public int getUserID() {
        return userID;
    }

    public String getNameID() {
        return nameID;
    }

    public String getMatrixID() {
        return matrixID;
    }

    public String getPhoneID() {
        return phoneID;
    }

    public String getEmailID() {
        return emailID;
    }

}
