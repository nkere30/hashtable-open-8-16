package com.epam.rd.autocode.hashtableopen816;

public class HashtableImpl implements HashtableOpen8to16{
    private static final int INIT_CAPACITY = 8;
    private static final int MAX_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.25;

    private int[] keys;
    private Object[] values;
    private int size;
    private int capacity;


    public HashtableImpl() {
        this.keys = new int[INIT_CAPACITY];
        this.values = new Object[INIT_CAPACITY];
        this.size = 0;
        this.capacity = INIT_CAPACITY;
    }
    @Override
    public void insert(int key, Object value) {

        if (size == capacity && ! containsKey(keys, key)) {
            if(capacity == MAX_CAPACITY ) throw new IllegalStateException();
            resizeAndRehash(2 * capacity);
        }
        int index = findIndex(key, capacity, keys);
        keys[index] = key;
        values[index] = value;
        size++;
    }

    private boolean containsKey(int[] keys, int key) {
        for (int tempKey: keys) {
            if(tempKey == key) return true;
        }
        return false;
    }

    private void resizeAndRehash(int resizeFactor) {
        int newCapacity = Math.min(resizeFactor, MAX_CAPACITY);
        int[] newKeys = new int[newCapacity];
        Object[] newValues = new Object[newCapacity];

        for (int i = 0; i < capacity; i++) {
            if (keys[i] != 0) {
                int newIndex = findIndex(keys[i], newCapacity, newKeys);
                newKeys[newIndex] = keys[i];
                newValues[newIndex] = values[i];
            }
        }

        keys = newKeys;
        values = newValues;
        capacity = newCapacity;
    }
    private int findIndex(int key, int capacity, int[] array) {
        int index = Math.abs(key) % capacity;
        while (array[index] != key && array[index] != 0) {
            index = (index + 1) % capacity;
        }
        return index;
    }
    @Override
    public Object search(int key) {
        int index = findIndex(key, capacity, keys);
        return values[index];
    }

    @Override
    public void remove(int key) {
        int index = findIndex(key, capacity, keys);
        if (keys[index] == key) {
            keys[index] = 0;
            values[index] = null;
            size--;
            if (size > 0 && size <= capacity * LOAD_FACTOR) {
                resizeAndRehash(capacity / 2);
            }
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int[] keys() {
        return keys;
    }
}
