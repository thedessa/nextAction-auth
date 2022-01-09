package dev.ifrs.dao;

import java.util.Collections;

import javax.enterprise.context.ApplicationScoped;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;

import dev.ifrs.model.User;
import dev.ifrs.utils.AuthUtils;

@ApplicationScoped
public class UserDaoImpl extends AbstractModelDao<User> implements UserDao {

  public static final String TABLE_NAME = "User";

  public UserDaoImpl() {
    super(User.class, TABLE_NAME);
  }

  @Override
  public String authUser(final String email, final String password) throws Exception {
    final Item item = get(new PrimaryKey(User.ATTR_EMAIL, email));
    if (item != null) {
      final User user = new User(item);
      if (AuthUtils.validatePassword(password, user.getHash())) {
        return user.getUserId();
      } else {
        throw new Exception("User not allowed");
      }
    } else {
      throw new Exception("User not exists");
    }
  }

  @Override
  public String register(final String email, final String password) throws Exception {
    final User user = new User(email, AuthUtils.generatePasswordHash(password));
    createModelItemIfNotExists(user, Collections.singletonList(User.ATTR_EMAIL));
    return user.getUserId();
  }
}
