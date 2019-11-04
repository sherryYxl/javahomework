package edu.nyu.cs9053.homework7;

public class CryptoWalletTransfer {

    public static <T extends Cryptocurrency> void transfer(CryptoWallet<? super T> toWallet, CryptoWallet<? extends T> fromWallet) {
        for (int i = 0; i < fromWallet.size(); i++) {
            toWallet.add(fromWallet.get(i));
        }
    }
}