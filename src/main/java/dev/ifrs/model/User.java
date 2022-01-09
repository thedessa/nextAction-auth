package dev.ifrs.model;

import java.util.UUID;

import com.amazonaws.services.dynamodbv2.document.Item;

import io.quarkus.runtime.annotations.RegisterForReflection;

import org.apache.commons.lang3.builder.ToStringBuilder;

@RegisterForReflection
public class User extends AbstractDynamoWrapper {

  public static final String ATTR_EMAIL = "email";
  public static final String ATTR_USER_ID = "userId";
  public static final String ATTR_HASH = "hash";

  private String email;
  private String userId;
  private String hash;

  public User() {

  }

  public User(final String email, final String hash) {
    this.email = email;
    this.hash = hash;
    this.userId = UUID.randomUUID().toString();
  }

  public User(final Item item) {
    this.email = item.getString(ATTR_EMAIL);
    this.userId = item.getString(ATTR_USER_ID);
    this.hash = item.getString(ATTR_HASH);
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

  public String getHash() {
    return hash;
  }

  public void setHash(final String hash) {
    this.hash = hash;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("userId", userId)
        .append("email", email)
        .append("password", hash)
        .toString();
  }

  @Override
  public Item toDynamoItem() {
    final Item item = new Item()
        .withPrimaryKey(ATTR_EMAIL, email)
        .withString(ATTR_USER_ID, userId)
        .withString(ATTR_HASH, hash);
    return item;
  }
}
