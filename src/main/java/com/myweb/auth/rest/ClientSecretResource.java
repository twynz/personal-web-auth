package com.myweb.auth.rest;

import java.util.List;
import java.util.UUID;

import com.myweb.auth.dao.ClientSecretDAO;
import com.myweb.auth.dao.PermissionDAO;
import com.myweb.auth.dao.UserDAO;
import com.myweb.auth.dao.UserPermissionDAO;
import com.myweb.auth.dto.ApiClientDTO;
import com.myweb.auth.entity.ClientSecret;
import com.myweb.auth.entity.User;
import com.myweb.auth.entity.UserPermission;
import com.myweb.auth.service.ClientSecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ClientSecretDAO clientSecretDAO;

    @Autowired
    private PermissionDAO permissionDAO;

    @Autowired
    private UserPermissionDAO userPermissionDAO;


    @RequestMapping(value = "/test", method = RequestMethod.GET, consumes = "application/json",
            produces = "application/json")
    @Description("test dao.")
    @PreAuthorize("hasAuthority('test')")
    public ResponseEntity<Void> test() {
        userDAO.selectByUsername("twy");

        SecurityContextHolder.getContext();

        User user = new User();
        user.setUserId(UUID.fromString("8e267f87-0807-4e57-96f9-e5a57249bb32"));
        user.setPassword("123");
        user.setUsername("test");
        userDAO.insert(user);

        userDAO.deleteById(UUID.fromString("8e267f87-0807-4e57-96f9-e5a57249bb32"));

        userDAO.getPermissionListByUserId(UUID.fromString("87047969-e2d9-4d61-82fa-8b706c446fcd"));
        userDAO.selectByPrimaryKey(UUID.fromString("8e267f87-0807-4e57-96f9-e5a57249bb32"));
//
//        ClientSecret clientSecret = new ClientSecret();
//        clientSecret.setClientId("test1");
//        clientSecret.setClientSecret("test1");
//        clientSecretDAO.create(clientSecret);

        userPermissionDAO.deletByUserId(UUID.fromString("8e267f87-0807-4e57-96f9-e5a57249bb32"));
        UserPermission userPermission = new UserPermission();
        userPermission.setPermissionId(UUID.fromString("470021ab-fb34-48eb-8f35-908e494c0c5c"));
        userPermission.setUserId(UUID.fromString("8e267f87-0807-4e57-96f9-e5a57249bb32"));
        userPermission.setPermission("test");
        userPermissionDAO.insert(userPermission);

        return null;
    }


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

