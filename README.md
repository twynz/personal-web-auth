# personal-web-auth

This project is used to control login and url access security. It is also a micro service integrated with spring cloud and 
totally meet OAUTH2 flow by using spring security.

When it starts, this service will register to consul. To get a access token, must register your client details in database,
should also add your user details in database.

The format to get a token can use this command:

     curl test:test@localhost:18100/oauthant_type=password -d username=twy -d password=twy

You will get a token like this:
     
    {
      "access_token": "161bb76e-dc5c-4703-bfeb-b0a82c4cf275",
      "token_type": "bearer",
      "refresh_token": "853c660d-6776-4587-834c-0625ff0605ec",
      "expires_in": 43010,
      "scope": "test"
    }

To run this project:

1. mvn clean install.

2. mvn spring-boot:run
