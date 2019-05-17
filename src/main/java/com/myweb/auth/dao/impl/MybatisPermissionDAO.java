package com.myweb.auth.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.myweb.auth.dao.mapper.PermissionMapper;
import com.myweb.auth.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myweb.auth.dao.PermissionDAO;

@Repository
public class MybatisPermissionDAO implements PermissionDAO {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPermissionList(UUID userId){
        return permissionMapper.selectByMap(userId);
    }

    @Override
    public int deleteById(UUID id) {
        return permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Permission record) {
        return permissionMapper.insert(record);
    }

    @Override
    public Permission selectByPrimaryKey(UUID id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKey(Permission record) {
        permissionMapper.updateByPrimaryKey(record);
    }

    @Override
    public void updateName(UUID id, String name) {
        permissionMapper.updateName(id,name);
    }
}
