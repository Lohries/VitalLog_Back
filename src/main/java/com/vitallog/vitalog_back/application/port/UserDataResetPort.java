package com.vitallog.vitalog_back.application.port;

import com.vitallog.vitalog_back.domain.entity.User;

public interface UserDataResetPort {
    void resetAllData(User user);
}
