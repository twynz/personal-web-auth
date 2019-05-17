package com.myweb.auth.dao.impl;

import com.myweb.auth.dao.UserDAO;
import com.myweb.auth.dao.mapper.PermissionMapper;
import com.myweb.auth.dao.mapper.UserMapper;
import com.myweb.auth.dao.mapper.UserPermissionMapper;
import com.myweb.auth.entity.Permission;
import com.myweb.auth.entity.User;
import com.myweb.auth.entity.UserPermission;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MybatisUserDAO implements UserDAO {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserPermissionMapper userPermissionMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPermissionListByUserId(UUID userId) {

        List<UserPermission> list = userPermissionMapper.selectByUserId(userId);
        List<Permission> permissionList = new ArrayList<>();
        for(UserPermission userPermission: list){
            Permission currentPermission = new Permission();
            currentPermission.setPermission(userPermission.getPermission());
            currentPermission.setId(userPermission.getPermissionId());
//            currentPermission.setDescription(permissionMapper.selectByPrimaryKey(userPermission.getPermissionId()).getDescription());
            permissionList.add(currentPermission);
        }
        return permissionList;
    }

    @Override
    public int deleteById(UUID id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User selectByPrimaryKey(UUID id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKey(User user) {
        userMapper.updateByPrimaryKey(user);
    }
}
