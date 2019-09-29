package com.badrul.qnitibox;

public class Food {
    //private int userid;
    private int foodID;
    private String foodTitle;
    private String foodPrice;
    private String foodDesc;
    private String foodImage;


    public Food(int foodID, String foodTitle, String foodPrice, String foodDesc, String foodImage) {

        this.foodID = foodID;
        this.foodTitle = foodTitle;
        this.foodPrice = foodPrice;
        this.foodDesc = foodDesc;
        this.foodImage = foodImage;
    }

    public int getFoodID() {
        return foodID;
    }

    public String getFoodTitle() {
        return foodTitle;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public String getFoodImage() {
        return foodImage;
    }

}
