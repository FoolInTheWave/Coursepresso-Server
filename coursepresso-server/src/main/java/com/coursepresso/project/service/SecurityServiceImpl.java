package com.coursepresso.project.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

  private static final Logger log = LoggerFactory.getLogger(
      SecurityServiceImpl.class
  );

  @Inject
  private AuthenticationManager authenticationManager;
  @Inject
  private HttpServletRequest request;

  @Override
  public void login(String username, String password) {
    // Never log password information!
    log.info("Logging in user '{}'", username);

    UsernamePasswordAuthenticationToken authToken
        = new UsernamePasswordAuthenticationToken(username, password);

    Authentication authentication = authenticationManager.authenticate(
        authToken
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    log.debug("User '{}' successfully logged in with authorities: '{}'",
        username,
        authentication.getAuthorities()
    );
  }

  @Override
  public void logout() {
    Authentication authentication = SecurityContextHolder.getContext()
        .getAuthentication();

    log.info("Logging out user '{}'", authentication.getName());

    SecurityContextHolder.clearContext();

    // Clear HTTP session
    request.getSession().invalidate();

    log.debug("User '{}' successfully logged out",
        authentication.getName()
    );
  }
}
