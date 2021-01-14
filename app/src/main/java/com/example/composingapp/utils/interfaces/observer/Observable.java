package com.example.composingapp.utils.interfaces.observer;

public interface Observable {
    /**
     * Adds an Observer to this Observable
     *
     * @param observer Observer to add
     */
    void addObserver(Observer observer);

    /**
     * Removes an Observer from this Observable
     *
     * @param observer Observer to remove
     */
    void removeObserver(Observer observer);

    /**
     * Calls update() on Observers of this Observable
     *
     */
    void updateObservers();
}
