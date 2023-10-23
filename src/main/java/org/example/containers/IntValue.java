package org.example.containers;

public class IntValue implements IValue {
    private int value = 0;

    public IntValue(){}

    public IntValue(int v) {
        value = v;
    }

    public void set(int v) {
        value = v;
    }

    public int get() {
        return value;
    }

    public void increment() {
        value++;
    }

    public void decrement() {
        value--;
    }
}
