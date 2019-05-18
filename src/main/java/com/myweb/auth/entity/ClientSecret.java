package com.myweb.auth.entity;

public class ClientSecret extends BaseEntity {
    private String clientId;
    private String clientSecret;
    private String purpose;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }


    public static class ClientSecretBuilder {

        private ClientSecret client = new ClientSecret();

        public ClientSecretBuilder withClientId(String clientId) {
            client.setClientId(clientId);
            return this;
        }

        public ClientSecretBuilder withClientSecret(String clientSecret) {
            client.setClientSecret(clientSecret);
            return this;
        }

        public ClientSecretBuilder withPurpose(String purpose) {
            client.setPurpose(purpose);
            return this;
        }

        public ClientSecret build() {
            return client;
        }
    }
}

