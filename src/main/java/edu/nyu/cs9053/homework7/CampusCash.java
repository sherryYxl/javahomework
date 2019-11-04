package edu.nyu.cs9053.homework7;

public class CampusCash implements Cryptocurrency {
    
    private double amount;

    public CampusCash(double amount) {
        this.amount = amount;
    }

    @Override public double getAmount() {
        return amount;
    }

    @Override public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        CampusCash that = (CampusCash) obj;
        return (((long) Math.round(that.getAmount() * 100) - (long) Math.round(amount * 100)) == 0L); 
        //compare only two decimals since it's amount of money
    }

    @Override public int hashCode() {
        return Long.valueOf((long) Math.round(amount * 100)).hashCode();
        //keep two decimals. 
        //ex. both 56.348... and 56.354... will be converted to 5635L and have the same hashCode
    }
}