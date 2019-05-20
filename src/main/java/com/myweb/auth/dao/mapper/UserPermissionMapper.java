package com.myweb.auth.dao.mapper;

import com.myweb.auth.entity.UserPermission;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.UUID;

public interface UserPermissionMapper {

    List<UserPermission> selectByUserId(@Param("userId")UUID userId);

    int deleteByUserId(@Param("userId")UUID uuid);

    int deleteByPermissionId(@Param("permissionId")UUID uuid);

    int insert(UserPermission userPermission);

    void updateByPrimaryKeySelective(UserPermission userPermission);
}
