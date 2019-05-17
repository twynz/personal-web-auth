package com.myweb.auth.dao.mapper;

import com.myweb.auth.entity.UserPermission;

import java.util.List;
import java.util.UUID;

public interface UserPermissionMapper {

    List<UserPermission> selectByUserId(UUID userId);

    int deleteByUserId(UUID uuid);

    int deleteByPermissionId(UUID uuid);

    int insert(UserPermission userPermission);

    void updateByPrimaryKeySelective(UserPermission userPermission);
}
