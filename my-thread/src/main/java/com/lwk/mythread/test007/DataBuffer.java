package com.lwk.mythread.test007;

import java.util.ArrayList;
import java.util.List;

/**
 * 缓冲区
 * @author kai
 * @date 2020-08-24 21:27
 */
class DataBuffer {

    private final  static int MAX_ITEMS=10;
    private final List<String> items = new ArrayList<>();

    DataBuffer() {

    }

    DataBuffer(String prefix) {
        for (int i = 0; i < MAX_ITEMS; i++) {
            String item = prefix + i;
            System.out.printf("Init %s%n", item);
            items.add(item);
        }
    }

    synchronized void add(String s) {
        if (!isFull()) {
            items.add(s);
        }
    }

    synchronized String remove() {
        if (!isEmpty()) {
            return items.remove(0);
        }
        return null;
    }

    synchronized boolean isFull() {
        return items.size() == MAX_ITEMS;
    }

    synchronized boolean isEmpty() {
        return items.size() == 0;
    }
}
