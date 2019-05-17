package com.myweb.auth.dao;

import com.myweb.auth.entity.Permission;
import com.myweb.auth.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserDAO {

    List<Permission> getPermissionListByUserId(UUID userId);

    int deleteById(UUID id);

    int insert(User user);

    User selectByPrimaryKey(UUID id);

    void updateByPrimaryKey(User user);
}
