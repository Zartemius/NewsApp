package com.example.artem.newsapp;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxBus {

    public RxBus(){

    }

    private PublishSubject<Boolean> bus = PublishSubject.create();

    public void send(Boolean isBookMarked){
        bus.onNext(isBookMarked);
    }

    public Observable<Boolean> toObservable(){
        return bus;
    }

}
