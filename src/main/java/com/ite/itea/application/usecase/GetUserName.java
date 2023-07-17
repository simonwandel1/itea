package com.ite.itea.application.usecase;

import com.ite.itea.domain.user.UserId;
import com.ite.itea.domain.user.UserRepository;
import com.ite.itea.presentation.UserInfoPresenter;

import java.util.Optional;

public class GetUserName {

    private final UserRepository userRepository;
    private final UserInfoPresenter userInfoPresenter;

    public GetUserName(UserRepository userRepository, UserInfoPresenter userInfoPresenter) {
        this.userRepository = userRepository;
        this.userInfoPresenter = userInfoPresenter;
    }

    public Optional<String> execute(UserId id) {
        final var user = userRepository.byId(id);
        return user.map(userInfoPresenter::formatUserInfo);
    }
}
