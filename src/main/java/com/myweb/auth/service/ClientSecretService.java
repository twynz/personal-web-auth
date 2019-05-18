package com.myweb.auth.service;

import com.myweb.auth.dao.ClientSecretDAO;
import com.myweb.auth.dto.ApiClientDTO;
import com.myweb.auth.entity.ClientSecret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientSecretService {

    @Autowired
    private ClientSecretDAO clientSecretDao;

    public void createClientSecret(ApiClientDTO apiClient) {
        ClientSecret clientSecret = new ClientSecret.ClientSecretBuilder()
                .withClientId(apiClient.getClientId())
                .withClientSecret(apiClient.getClientSecret())
                .withPurpose(apiClient.getPurpose())
                .build();
        clientSecretDao.create(clientSecret);
    }

    public ApiClientDTO getClientSecretByClientId(String clientId) {
        ClientSecret clientSecret = new ClientSecret.ClientSecretBuilder()
                .withClientId(clientId)
                .build();
        List<ClientSecret> results = clientSecretDao.get(clientSecret);
        if (results.size() != 0) {
            return convert(results.get(0));
        }
        return null;
    }

    public int updateClientSecret(ApiClientDTO apiClient){
        ClientSecret clientSecret = new ClientSecret.ClientSecretBuilder()
                .withClientSecret(apiClient.getClientSecret())
                .withPurpose(apiClient.getPurpose())
                .withClientId(apiClient.getClientId())
                .build();
        return clientSecretDao.update(clientSecret);
    }

    private ApiClientDTO convert(ClientSecret clientSecret) {
        ApiClientDTO apiClient = new ApiClientDTO();
        apiClient.setClientId(clientSecret.getClientId());
        apiClient.setClientSecret(clientSecret.getClientSecret());
        return apiClient;
    }

}
