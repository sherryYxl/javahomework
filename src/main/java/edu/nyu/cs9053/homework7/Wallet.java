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
            return false;
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
                currentArray[indexOfValue] = null;
                if (currentSize > (int) Math.floor((1 - EFFICIENT_CAPACITY) * currentArray.length) || currentArray.length <= DEFAULT_INITIAL_SIZE ) {
                    currentArray = rearrange(currentArray, indexOfValue);
                } else {
                    currentArray = halfWalletCapacity(currentArray);
                }
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
        
        if (oldLength < (int) Math.round(EFFICIENT_CAPACITY * DEFAULT_INITIAL_SIZE)) {
            doubleLengthArray = arrayCreator.create(DEFAULT_INITIAL_SIZE);
        } else {
            doubleLengthArray = arrayCreator.create(2 * oldLength);
        }

        int newIndex = 0;
        for (int i = 0; i < oldLength; i++) {
            if (oldArray[i] != null) {
                doubleLengthArray[newIndex] = oldArray[i];
                newIndex++;
            }
        }
        atomicWallet.set(doubleLengthArray);
    }

    private T[] halfWalletCapacity(T[] array) {
        int oldLength = array.length;
        int newLength = oldLength / 2;
        T[] halfArray = arrayCreator.create(newLength);

        int indexOld = 0;
        int indexNew = 0;
        while (indexNew < newLength && indexOld < oldLength) {
            if (array[indexOld] == null) {
                indexOld++;
            }
            else {
                halfArray[indexNew] = array[indexOld];
                indexOld++;
                indexNew++;
            }
        }
        return halfArray;
    }

    private T[] rearrange(T[] array, int index) {
        int currLength = array.length;
        int indexNext = index + 1;

        while (index < currLength && indexNext < currLength) {                  
            if (array[index] == null) {
                if (array[indexNext] != null) {
                    array[index] = array[indexNext];
                    array[indexNext] = null;
                    index++;
                    indexNext++; 
                } else {
                    indexNext++;
                }
            } else {
                index++;
            }
        }
        return array;
    }
}





