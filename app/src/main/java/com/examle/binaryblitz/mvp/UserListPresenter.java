package com.examle.binaryblitz.mvp;

import com.examle.binaryblitz.application.App;
import com.examle.binaryblitz.data.DbManager;
import com.examle.binaryblitz.model.User;
import com.examle.binaryblitz.utils.NetworkUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class UserListPresenter extends BasePresenter implements Observer<List<User>> {

    private UserListView userListView;
    private DbManager dbManager;

    public UserListPresenter(UserListView userListView) {
        dbManager = new DbManager();
        this.userListView = userListView;
    }

    public void getUserList() {
        if (NetworkUtils.isNetworkAvailable()) {
            userListView.showProgressDialog();
            Observable<List<User>> pointsResponseObservable = App.getUserApiService().getUsers();
            subscribe(pointsResponseObservable, this);
        } else {
            userListView.showToast();
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull List<User> users) {
        userListView.hideProgressDialog();
        dbManager.saveUser(users);
        userListView.onUserLoaded(dbManager.getCacheUser());
    }

    @Override
    public void onError(@NonNull Throwable e) {
        userListView.hideProgressDialog();
    }

    @Override
    public void onComplete() {
        userListView.hideProgressDialog();
    }
}
