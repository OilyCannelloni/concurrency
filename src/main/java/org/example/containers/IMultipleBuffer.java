package org.example.containers;

public interface IMultipleBuffer {
    void put(int[] elements) throws InterruptedException;
    int[] take(int nTake) throws InterruptedException;
    int getItemCount();
}
