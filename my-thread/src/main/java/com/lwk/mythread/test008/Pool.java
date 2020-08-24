package com.lwk.mythread.test008;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.concurrent.Semaphore;

/**
 * @author kai
 * @date 2020-08-24 23:06
 */
class Pool {
    public static final int MAX_AVAILABLE = 10;
    private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);
    private final String[] items;
    private final boolean[] used = new boolean[MAX_AVAILABLE];

    Pool() {
        items = new String[MAX_AVAILABLE];
        for (int i = 0; i < items.length; i++) {
            items[i] = "I-" + i;
        }
    }

    String getItem() throws InterruptedException {
        available.acquire();
        return getNextAvailableItem();
    }

    void putItem(String item) {
        if (markAsUnused(item)) {
            available.release();
        }
    }

    private synchronized String getNextAvailableItem() {
        for (int i = 0; i < MAX_AVAILABLE; i++) {
            if (!used[i]) {
                used[i] = true;
                return items[i];
            }
        }
        return null;
    }

    private synchronized boolean markAsUnused(String item) {
        for (int i = 0; i < MAX_AVAILABLE; i++) {
            if (item.equals(items[i])) {
                if (used[i]) {
                    used[i] = false;
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
