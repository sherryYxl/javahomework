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
        return Double.valueOf(amount).equals(that.getAmount()); 
    }

    @Override public int hashCode() {
        return Double.valueOf(amount).hashCode();
    }
}