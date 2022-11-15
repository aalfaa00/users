package com.zeromax.users.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeromax.users.utils.Constants;
import com.zeromax.users.entity.user.UserEntity;
import com.zeromax.users.exeptions.CustomErrorModel;
import com.zeromax.users.exeptions.InvalidRequestException;
import com.zeromax.users.models.LoginRequestModel;
import com.zeromax.users.models.LoginResponseModel;
import com.zeromax.users.service.RefreshTokenService;
import com.zeromax.users.service.UserService;
import com.zeromax.users.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final RefreshTokenService refreshTokenService;

  @Autowired
  public CustomAuthFilter(AuthenticationManager authenticationManager,
                          UserService userService,
                          RefreshTokenService refreshTokenService) {
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    this.refreshTokenService = refreshTokenService;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException, InvalidRequestException {
    try {
      LoginRequestModel login =
          new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
      UsernamePasswordAuthenticationToken token =
          new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
      return authenticationManager.authenticate(token);
    } catch (IOException e) {
      throw new InvalidRequestException("Please enter email and password for login");
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException {
    User login = (User) authResult.getPrincipal();
    UserEntity user = userService.getUserByEmail(login.getUsername());

    if (user != null) {
      if(!user.getIsActive()){
        unsuccessfulAuthentication(request, response,
            new AuthenticationException("Your account is not active") {
              @Override
              public String getMessage() {
                return super.getMessage();
              }
            });
      }else {
        refreshTokenService.checkAndDeleteRefreshToken(user.getId());
        String refreshToken = JWTUtils.createToken(user, Constants.COMPANY_NAME, Constants.REFRESH_KEY, Constants.REFRESH_TTL);
        refreshTokenService.saveToken(user.getId(), refreshToken);

        String accessToken = JWTUtils.createToken(user, Constants.COMPANY_NAME, Constants.ACCESS_KEY, Constants.ACCESS_TTL);

        LoginResponseModel body = new LoginResponseModel(accessToken, refreshToken, user.getIsFinished());
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter()
            .write(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
      }
    } else {
      throw new IOException("Could not read and pass user info");
    }
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException {
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    CustomErrorModel error =
        new CustomErrorModel(failed.getMessage(), "1004", HttpStatus.BAD_REQUEST.value());
    ObjectMapper objectMapper = new ObjectMapper();
    String body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(error);
    response.getWriter().write(body);
  }
}
