package com.myweb.auth.dao.impl;

import com.myweb.auth.dao.UserPermissionDAO;
import com.myweb.auth.dao.mapper.UserPermissionMapper;
import com.myweb.auth.entity.UserPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class MybatisUserPermissionDAO implements UserPermissionDAO {

    @Autowired
    private UserPermissionMapper userPermissionMapper;

    @Override
    public int insert(UserPermission userPermission) {
        return userPermissionMapper.insert(userPermission);
    }

    @Override
    public List<UserPermission> selectByUserId(UUID id) {
        return userPermissionMapper.selectByUserId(id);
    }

    @Override
    public void updateUserPermission(UserPermission userPermission) {
        userPermissionMapper.updateByPrimaryKeySelective(userPermission);
    }

    @Override
    public int deletByUserId(UUID userId) {
        return userPermissionMapper.deleteByUserId(userId);
    }
}
