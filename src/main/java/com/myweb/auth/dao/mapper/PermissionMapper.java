package com.myweb.auth.dao.mapper;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.myweb.auth.entity.Permission;
import org.apache.ibatis.annotations.Param;


public interface PermissionMapper {

    int deleteByPrimaryKey(@Param("id") UUID id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(@Param("id") UUID id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> selectByMap(UUID userId);

    void updateName(UUID id, String newName);
}
