package dev.ifrs.model;

import java.util.UUID;

import com.amazonaws.services.dynamodbv2.document.Item;

import io.quarkus.runtime.annotations.RegisterForReflection;

import org.apache.commons.lang3.builder.ToStringBuilder;

@RegisterForReflection
public class User extends AbstractDynamoWrapper {

  public static final String ATTR_EMAIL = "email";
  public static final String ATTR_USER_ID = "userId";
  public static final String ATTR_TOKEN = "token";

  private String email;
  private String userId;
  private String token;

  public User() {

  }

  public User(final String email, final String token) {
    this.email = email;
    this.token = token;
    this.userId = UUID.randomUUID().toString();
  }

  public User(final Item item) {
    this.email = item.getString(ATTR_EMAIL);
    this.userId = item.getString(ATTR_USER_ID);
    this.token = item.getString(ATTR_TOKEN);
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(final String userId) {
    this.userId = userId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public String getToken() {
    return token;
  }

  public void setToken(final String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("userId", userId)
        .append("email", email)
        .append("password", token)
        .toString();
  }

  @Override
  public Item toDynamoItem() {
    final Item item = new Item()
        .withPrimaryKey(ATTR_EMAIL, email)
        .withString(ATTR_USER_ID, userId)
        .withString(ATTR_TOKEN, token);
    return item;
  }
}
