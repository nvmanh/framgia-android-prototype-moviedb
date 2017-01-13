package com.nvmanh.themoviedb.utils;

import rx.Subscriber;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 12/01/2017.
 */

public abstract class SimpleSubscribe<T> extends Subscriber<T> {
    private T mT;

    @Override
    public void onCompleted() {
        onSuccess(mT);
    }

    @Override
    public void onNext(T t) {
        this.mT = t;
    }

    public abstract void onSuccess(T t);
}
