package com.example.queuelibrary;

/**
 * A subscriber contract for the sqlite change bus.
 */
public interface SQLiteBusSubscriber<T> {
    void onAdded(T object);

    void onRemoved(T object);

    void onCleared();
}
