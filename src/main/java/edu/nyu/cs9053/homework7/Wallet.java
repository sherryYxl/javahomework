package edu.nyu.cs9053.homework7;

import java.util.concurrent.atomic.AtomicReference;

public class Wallet<T> {
    
    private static final int DEFAULT_INITIAL_SIZE = 16;
    private static final double EFFICIENT_CAPACITY = 0.75;
    private final ArrayCreator<T> arrayCreator;
    private final AtomicReference<T[]> atomicWallet;

    public Wallet(ArrayCreator<T> arrayCreator) {
        this(arrayCreator, DEFAULT_INITIAL_SIZE);
    }

    public Wallet(ArrayCreator<T> arrayCreator, int size) {
        this.arrayCreator = arrayCreator;
        this.atomicWallet = new AtomicReference(arrayCreator.create(size));
    }

    public boolean add(T value) {
        if (value == null) {
            throw new IllegalArgumentException("invalid argument");
        }

        if (!contains(value)) {
            int lastIndex = size();
            if (lastIndex > (int) Math.floor(EFFICIENT_CAPACITY * atomicWallet.get().length)) {
                doubleWalletCapacity();
            }
            T[] currentArray = atomicWallet.get();
            currentArray[lastIndex] = value;
            atomicWallet.set(currentArray);
            return true;
        }
        return false;
        
    }

    public boolean contains(T value) {
        if (value == null) {
            return false;
        }

        for (T  item : atomicWallet.get()) {
            if (item == null) {
                continue;
            }
            if (item.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean remove(T value) {
        if (value == null) {
            return false;
        }

        T[] currentArray = atomicWallet.get();
        int currentSize = size();
        int indexOfValue = 0;

        for (T item : currentArray) {
            if (item == null) {
                continue;
            }
            if (item.equals(value)) {
                
                if (currentSize > (int) Math.floor((1 - EFFICIENT_CAPACITY) * currentArray.length) || currentArray.length <= DEFAULT_INITIAL_SIZE ) {
                    System.arraycopy(currentArray, (indexOfValue + 1), currentArray, indexOfValue, (currentSize - (indexOfValue + 1)));
                    
                } else {
                    //currentArray = halfWalletCapacity(currentArray, indexOfValue);
                    T[] halfArray = arrayCreator.create(currentArray.length / 2);
                    System.arraycopy(currentArray, 0, halfArray, 0, indexOfValue);
                    System.arraycopy(currentArray, (indexOfValue + 1), halfArray, indexOfValue, (currentSize - (indexOfValue + 1)));
                    currentArray = halfArray;
                }
                currentArray[currentSize - 1] = null;
                atomicWallet.set(currentArray);
                return true;
            }
            indexOfValue++;
        }
        return false;
    }

    public T get(int index) {
        if (index < atomicWallet.get().length) {
            return atomicWallet.get()[index];
        }
        return null;      
    } 

    public int size() {
        int size = 0;
        for (T item : atomicWallet.get()) {
            if (item != null) {
                size++;
            }
        }
        return size;
    }

    private final void doubleWalletCapacity() {
        T[] oldArray = atomicWallet.get();
        int oldLength = oldArray.length;

        T[] doubleLengthArray;
        
        if (oldLength * 2 < DEFAULT_INITIAL_SIZE) {
            doubleLengthArray = arrayCreator.create(DEFAULT_INITIAL_SIZE);
        } else {
            doubleLengthArray = arrayCreator.create(2 * oldLength);
        }

        System.arraycopy(oldArray, 0, doubleLengthArray, 0, oldLength);
        atomicWallet.set(doubleLengthArray);
    }

    public int length() {
        T[] array = atomicWallet.get();
        return array.length;
    }
}





