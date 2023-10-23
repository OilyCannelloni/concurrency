package org.example;

public interface IValue {
    int get() throws InterruptedException;
    void set(int v) throws InterruptedException;
}
