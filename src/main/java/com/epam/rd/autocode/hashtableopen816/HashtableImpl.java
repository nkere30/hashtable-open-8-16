package com.epam.rd.autocode.hashtableopen816;

public class HashtableImpl implements HashtableOpen8to16{
    private static final int INIT_CAPACITY = 8;
    private static final int MAX_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.25;

    private int[] keys;
    private Object[] values;
    private int size;
    private int capacity;
    private boolean[] isFilled;

    public HashtableImpl() {
        this.keys = new int[INIT_CAPACITY];
        this.values = new Object[INIT_CAPACITY];
        this.isFilled = new boolean[INIT_CAPACITY];
        this.size = 0;
        this.capacity = INIT_CAPACITY;
    }
    @Override
    public void insert(int key, Object value) {

        if (size == capacity && ! containsKey(keys, key)) {
            if(size == MAX_CAPACITY ) throw new IllegalStateException();
            resizeAndRehash(2 * capacity);
        }
        if (!containsKey(keys, key)) {
            size++;
            int index = findIndex(key, capacity, keys, isFilled);
            keys[index] = key;
            values[index] = value;
            isFilled[index] = true;
        } else {
            int index = findIndex(key, capacity, keys, isFilled);
            values[index] = value;
        }
    }

    private boolean containsKey(int[] keys, int key) {
        for (int i = 0; i < keys.length; i++) {
            if(keys[i] == key && values[i] != null && values[i].equals(search(key))) return true;
        }
        return false;
    }

    private void resizeAndRehash(int resizeFactor) {
        int newCapacity = Math.min(resizeFactor, MAX_CAPACITY);
        int[] newKeys = new int[newCapacity];
        boolean[] newIsFilled = new boolean[newCapacity];
        Object[] newValues = new Object[newCapacity];

        for (int i = 0; i < capacity; i++) {
            if (values[i] != null) {
                int newIndex = findIndex(keys[i], newCapacity, newKeys, newIsFilled);
                newKeys[newIndex] = keys[i];
                newValues[newIndex] = values[i];
                newIsFilled[newIndex] = true;
            }
        }

        keys = newKeys;
        values = newValues;
        capacity = newCapacity;
        isFilled = newIsFilled;
    }
    private int findIndex(int key, int capacity, int[] array, boolean[] isFilled) {
        int index = Math.abs(key) % capacity;
        while (array[index] != key && isFilled[index]) {
            index = (index + 1) % capacity;
        }
        return index;
    }
    @Override
    public Object search(int key) {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == key && values[i] != null) {
                return values[i];
            }
        }
        return null;
    }

    @Override
    public void remove(int key) {
        int index = findIndex(key, capacity, keys, isFilled);
        if (keys[index] == key) {
            keys[index] = 0;
            values[index] = null;
            isFilled[index] = false;
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
