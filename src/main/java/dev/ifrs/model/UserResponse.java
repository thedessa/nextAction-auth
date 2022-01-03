package dev.ifrs.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class UserResponse {

  private String jwt;
  private String userId;

  public UserResponse() {

  }

  public UserResponse(final String jwt, final String userId) {
    this.jwt = jwt;
    this.userId = userId;
  }

  public String getJwt() {
    return jwt;
  }

  public void setJwt(final String jwt) {
    this.jwt = jwt;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(final String userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("jwt", jwt)
        .append("userId", userId)
        .toString();
  }
}
