package com.example.artem.newsapp.observer_and_subject;


public interface Subject {

    public void register(Observer observer);
    public void unregister(Observer observer);
    public void notifyObservers();
}
