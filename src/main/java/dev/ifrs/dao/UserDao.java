package dev.ifrs.dao;

import java.util.List;

import dev.ifrs.model.NextAction;

public interface UserDao {

  String authUser(final String email, final String password) throws Exception;
}
