package com.examle.binaryblitz.mvp;

import com.examle.binaryblitz.api.SaveUserQuery;
import com.examle.binaryblitz.application.App;
import com.examle.binaryblitz.data.DbManager;
import com.examle.binaryblitz.model.Message;
import com.examle.binaryblitz.utils.NetworkUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class UserPresenter extends BasePresenter implements Observer<Message> {

    private UserView userView;
    private DbManager dbManager;

    public UserPresenter(UserView userView) {
        dbManager = new DbManager();
        this.userView = userView;
    }

    public void saveUser(SaveUserQuery saveUserQuery) {
        if (NetworkUtils.isNetworkAvailable()) {
            userView.showProgressDialog();
            Observable<Message> pointsResponseObservable = App.getUserApiService().saveUser(saveUserQuery);
            subscribe(pointsResponseObservable, this);
        } else {
            userView.showToast("Проверьте интернет соеденение");
        }
    }

    public void editUser(SaveUserQuery saveUserQuery) {
        if (NetworkUtils.isNetworkAvailable()) {
            Observable<Message> pointsResponseObservable = App.getUserApiService().editUser(saveUserQuery);
            subscribe(pointsResponseObservable, this);
        } else {
            userView.showToast("Проверьте интернет соеденение");
        }
    }

    public void showUser(String userId) {
        if (userId != null) {
            userView.showUser(dbManager.getUser(userId));
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull Message message) {
        userView.hideProgressDialog();
        userView.showToast(message.getStatus());
    }

    @Override
    public void onError(@NonNull Throwable e) {
        userView.hideProgressDialog();
        userView.showToast(e.getMessage());
    }

    @Override
    public void onComplete() {
        userView.hideProgressDialog();
    }
}
