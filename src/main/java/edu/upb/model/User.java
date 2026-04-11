package edu.upb.model;

import java.io.Serializable;

public class User implements Serializable {
  private static final long serialVersionUID = 1L;

  private String id;
  private String username;
  private String passwordHash;
  private String role;

  public User(String id, String username, String passwordHash, String role) {
    this.id = id;
    this.username = username;
    this.passwordHash = passwordHash;
    this.role = role;
  }

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public String getRole() {
    return role;
  }
}