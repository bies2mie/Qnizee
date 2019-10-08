package com.badrul.qnitibox;

public class Promo {

    //private int userid;
    private int promoID;
    private String promoQTT;


    public Promo(int promoID, String promoQTT) {

        this.promoID = promoID;
        this.promoQTT = promoQTT;

    }

    public int getPromoID() {
        return promoID;
    }

    public String getPromoQTT() {
        return promoQTT;
    }

}
