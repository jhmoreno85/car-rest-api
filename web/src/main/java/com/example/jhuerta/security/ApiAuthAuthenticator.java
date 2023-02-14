package com.example.jhuerta.security;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.util.CommonHelper;

public class ApiAuthAuthenticator implements Authenticator {

    public static final String ISSUER = "https://dev-le-hz9mr.us.auth0.com/";

    @Override
    public void validate(Credentials cred, WebContext webContext) {
        if (null == cred) {
            throw new CredentialsException("credentials must not be null");
        }
        final var credentials = (TokenCredentials) cred;
        if (CommonHelper.isBlank(credentials.getToken())) {
            throw new CredentialsException("token must not be blank");
        }
    }
}
