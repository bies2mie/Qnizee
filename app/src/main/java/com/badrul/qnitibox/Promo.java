package com.badrul.qnitibox;

public class Promo {

    //private int userid;
    private int promoID;
    private String promoQTT;
    private String promoName;


    public Promo(int promoID, String promoQTT,String promoName) {

        this.promoID = promoID;
        this.promoQTT = promoQTT;
        this.promoName = promoName;

    }

    public int getPromoID() {
        return promoID;
    }

    public String getPromoQTT() {
        return promoQTT;
    }

    public String getPromoName() { return promoName; }

}
