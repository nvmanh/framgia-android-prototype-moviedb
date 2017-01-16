package com.nvmanh.themoviedb.utils;

import rx.Subscriber;

/**
 * Created by FRAMGIA\nguyen.viet.manh on 16/01/2017.
 */

public abstract class LocalSubscribe<T> extends Subscriber<T> {
    private T mT;

    @Override
    public void onCompleted() {
        onSuccess(mT);
    }

    @Override
    public void onNext(T t) {
        mT = t;
        onCompleted();
    }

    public abstract void onSuccess(T mt);
}
