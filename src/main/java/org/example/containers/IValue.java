package org.example.containers;

public interface IValue {
    int get() throws InterruptedException;
    void set(int v) throws InterruptedException;
}
