package edu.upb.server.business;

import edu.upb.model.User;
import edu.sebsx.app.hashtable.HashTable;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class SecurityModule {
  private HashTable<String, User> users;
  private HashTable<String, String> sessions;

  public SecurityModule() {
    this.users = new HashTable<>(16);
    this.sessions = new HashTable<>(16);
    User admin = new User("1", "admin", hashPassword("admin"), "ADMIN");
    users.put(admin.getUsername(), admin);
  }

  public String hashPassword(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
      StringBuilder hex = new StringBuilder();
      for (byte b : hash) {
        hex.append(String.format("%02x", b));
      }
      return hex.toString();
    } catch (Exception e) {
      throw new RuntimeException("Error hashing password", e);
    }
  }

  public boolean validateUser(String username, String password) {
    User user = users.get(username);
    if (user == null) return false;
    return user.getPasswordHash().equals(hashPassword(password));
  }

  public String getRole(String username) {
    User user = users.get(username);
    return user != null ? user.getRole() : null;
  }

  public String createSession(String username) {
    String token = username + System.currentTimeMillis();
    sessions.put(token, username);
    return token;
  }

  public boolean validateSession(String token) {
    return sessions.get(token) != null;
  }

  public void addUser(User user) {
    users.put(user.getUsername(), user);
  }
}