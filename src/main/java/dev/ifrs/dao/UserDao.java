package dev.ifrs.dao;

public interface UserDao {

  String authUser(final String email, final String password) throws Exception;

  String register(final String email, final String password) throws Exception;
}
