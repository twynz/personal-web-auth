package com.myweb.auth.dao.mapper;

import com.myweb.auth.entity.ClientSecret;

import java.util.List;
import java.util.Map;

public interface ClientSecretMapper {

    int insert(ClientSecret record);

    List<ClientSecret> selectByParams(Map map);

    int updateByParams(Map map);
}

