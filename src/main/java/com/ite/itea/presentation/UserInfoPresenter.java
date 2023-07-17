package com.ite.itea.presentation;

import com.ite.itea.domain.user.User;

public class UserInfoPresenter {
    public String formatUserInfo(User user) {
        return "Full name: " + user.firstname() + " " + user.lastname();
    }
}
