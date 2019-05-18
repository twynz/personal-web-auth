package com.myweb.auth.rest;

import java.util.List;
import java.util.UUID;

import com.myweb.auth.dto.ApiClientDTO;
import com.myweb.auth.service.ClientSecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClientSecretResource {

    @Autowired
    private ClientSecretService clientSecretService;

    @RequestMapping(value = "/clients", method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    @Description("create client.")
    @PreAuthorize("hasPermission('client_secret_change')")
    public ResponseEntity<Void> createClient(@RequestBody ApiClientDTO apiClientDTO) {
        clientSecretService.createClientSecret(apiClientDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/clients/{clientId}", method = RequestMethod.GET, produces = "application/json",
            consumes = "application/json")
    @Description("get client by clientId.")
    @PreAuthorize("hasPermission('client_secret_change')")
    public ResponseEntity<ApiClientDTO> getClient(@PathVariable("clientId") String clientId) {
        ApiClientDTO apiClientDTO = clientSecretService.getClientSecretByClientId(clientId);
        return new ResponseEntity<>(apiClientDTO, HttpStatus.OK);
    }
}

