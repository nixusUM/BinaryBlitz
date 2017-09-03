package com.examle.binaryblitz.mvp;

import com.examle.binaryblitz.model.User;

public interface UserView {
    void showUser(User user);
    void showToast(String message);
    void showProgressDialog();
    void hideProgressDialog();
}
