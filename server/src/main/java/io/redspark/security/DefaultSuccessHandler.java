package io.redspark.security;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DefaultSuccessHandler implements AuthenticationSuccessHandler, LogoutSuccessHandler {

  private ConvertPrincipal convertPrincipal;

  public DefaultSuccessHandler(ConvertPrincipal convertPrincipal) {
    super();
    this.convertPrincipal = convertPrincipal;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    success(response, authentication);
  }

  private void success(HttpServletResponse response, Authentication authentication) throws IOException {
    JsonFactory jsonFactory = new JsonFactory(new ObjectMapper());
    StringWriter writer = new StringWriter();
    JsonGenerator jsonGenerator = jsonFactory.createGenerator(writer);

    if (authentication != null) {
      Object principal = authentication.getPrincipal();
      jsonGenerator.writeObject(convertPrincipal.convertPrincipal(principal));

      this.updateHeaders(response);

      try {
        response.getWriter().print(writer.toString());
        response.getWriter().flush();
      } finally {
        writer.close();
      }
    }
  }

  protected void updateHeaders(HttpServletResponse response) {
    response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
  }

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    success(response, authentication);
  }

}
