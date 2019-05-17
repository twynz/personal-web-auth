package com.myweb.auth.dao;

import com.myweb.auth.entity.ClientSecret;

import java.util.List;

public interface ClientSecretDAO {
    int create(ClientSecret clientSecret);

    List<ClientSecret> get(ClientSecret clientSecret);

    int update(ClientSecret clientSecret);
}
