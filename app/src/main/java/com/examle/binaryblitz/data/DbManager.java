package com.examle.binaryblitz.data;

import com.examle.binaryblitz.model.User;

import java.util.List;

import io.realm.Realm;

public class DbManager {

    private Realm realm;

    public DbManager() {
        realm = Realm.getDefaultInstance();
    }

    public List<User> getCacheUser() {
        return realm.where(User.class).findAll();
    }

    public void saveUser(final List<User> users) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(users);
            }
        });
    }

    public User getUser(String userId) {
        return realm.where(User.class).equalTo("mId", userId).findFirst();
    }
}
