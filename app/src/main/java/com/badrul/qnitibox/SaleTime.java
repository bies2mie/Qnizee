package com.badrul.qnitibox;

public class SaleTime {

        //private int userid;
        private int saletimeID;
        private String saleStart;
        private String saleEnd;


        public SaleTime(int saletimeID, String saleStart,String saleEnd) {

            this.saletimeID = saletimeID;
            this.saleStart = saleStart;
            this.saleEnd = saleEnd;

        }

        public int getSaletimeID() {
            return saletimeID;
        }

        public String getSaleStart() {
            return saleStart;
        }

        public String getSaleEnd() { return saleEnd; }

    }
