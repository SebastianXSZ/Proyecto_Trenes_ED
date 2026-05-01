package edu.upb.model;

import java.io.Serializable;

/**
 * Representa un usuario del sistema con sus credenciales y rol.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class User implements Serializable {
  private static final long serialVersionUID = 1L;

  private String id;
  private String username;
  private String passwordHash;
  private String role;
  private String name;
  private String lastName;

  public User(String id, String username, String passwordHash, String role, String name, String lastName) {
    this.id = id;
    this.username = username;
    this.passwordHash = passwordHash;
    this.role = role;
    this.name = name;
    this.lastName = lastName;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }
}