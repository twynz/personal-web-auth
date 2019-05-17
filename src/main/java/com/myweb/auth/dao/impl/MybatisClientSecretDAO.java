package com.myweb.auth.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myweb.auth.dao.ClientSecretDAO;
import com.myweb.auth.dao.mapper.ClientSecretMapper;
import com.myweb.auth.entity.ClientSecret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisClientSecretDAO implements ClientSecretDAO {

    @Autowired
    private ClientSecretMapper mapper;

    @Override
    public int create(ClientSecret clientSecret) {
        return mapper.insert(clientSecret);
    }

    @Override
    public List<ClientSecret> get(ClientSecret clientSecret) {
        Map<String, Object> params = new HashMap<>();
        params.put("clientId", clientSecret.getClientId());
        params.put("clientSecret", clientSecret.getClientSecret());
        return mapper.selectByParams(params);
    }

    @Override
    public int update(ClientSecret clientSecret) {
        Map<String, Object> params = new HashMap<>();
        params.put("clientId", clientSecret.getClientId());
        params.put("clientSecret", clientSecret.getClientSecret());
        params.put("purpose", clientSecret.getPurpose());
        return mapper.updateByParams(params);
    }
}
