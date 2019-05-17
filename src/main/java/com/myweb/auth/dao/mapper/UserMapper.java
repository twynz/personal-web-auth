package com.myweb.auth.dao.mapper;

import com.myweb.auth.entity.Permission;
import com.myweb.auth.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

public interface UserMapper {

    int insert(User user);

    User selectByPrimaryKey(@Param("id") UUID id);

    int deleteByPrimaryKey(@Param("id") UUID id);

    int updateByPrimaryKey(User user);

}
