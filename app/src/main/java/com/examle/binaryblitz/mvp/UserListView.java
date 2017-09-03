package com.examle.binaryblitz.mvp;

import com.examle.binaryblitz.model.User;

import java.util.List;

public interface UserListView extends BaseView{
    void onUserLoaded(List<User> users);
    void showToast();
    void showProgressDialog();
    void hideProgressDialog();
}
