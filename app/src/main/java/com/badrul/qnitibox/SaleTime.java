package com.badrul.qnitibox;

public class SaleTime {

        //private int userid;
        private int saletimeID;
        private int saleStart;
        private int saleEnd;


        public SaleTime(int saletimeID, int saleStart,int saleEnd) {

            this.saletimeID = saletimeID;
            this.saleStart = saleStart;
            this.saleEnd = saleEnd;

        }

        public int getSaletimeID() {
            return saletimeID;
        }

        public int getSaleStart() {
            return saleStart;
        }

        public int getSaleEnd() { return saleEnd; }

    }
