package com.myweb.auth.dao;

import com.myweb.auth.entity.UserPermission;

import java.util.List;
import java.util.UUID;

public interface UserPermissionDAO {

    int insert(UserPermission userPermission);

    List<UserPermission> selectByUserId(UUID id);

    void updateUserPermission(UserPermission userPermission);

    int deletByUserId(UUID userId);
}
