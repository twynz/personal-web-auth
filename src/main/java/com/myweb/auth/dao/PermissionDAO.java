package com.myweb.auth.dao;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.myweb.auth.entity.Permission;

public interface PermissionDAO {

    List<Permission> getPermissionList(UUID userId);

    int deleteById(UUID id);

    int insert(Permission record);

    Permission selectByPrimaryKey(UUID id);

    void updateByPrimaryKey(Permission record);

    void updateName(UUID id, String newName);

}
