package edu.upb.server.business;

import edu.upb.model.User;
import edu.sebsx.app.hashtable.HashTable;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

/**
 * Módulo de seguridad encargado de la autenticación de usuarios y gestión de sesiones.
 * Almacena las credenciales de los usuarios con hash SHA-256 para proteger las contraseñas.
 * Proporciona métodos para validar credenciales, obtener roles de usuario,
 * crear y validar tokens de sesión.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class SecurityModule {
  private HashTable<String, User> users;
  private HashTable<String, String> sessions;

  public SecurityModule() {
    this.users = new HashTable<>(16);
    this.sessions = new HashTable<>(16);
    User admin = new User("1", "admin", hashPassword("1234"), "ADMIN");
    User operador = new User("2", "operador", hashPassword("123"), "OPERATOR");
    users.put(admin.getUsername(), admin);
    users.put(operador.getUsername(), operador);
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
    String token = java.util.UUID.randomUUID().toString();
    sessions.put(token, username);
    return token;
  }

  public boolean validateSession(String token) {
    return sessions.get(token) != null;
  }

  public void addUser(User user) {
    users.put(user.getUsername(), user);
  }

  public boolean registerUser(String id, String username, String password, String role) {
    if (users.get(username) != null) return false;
    User newUser = new User(id, username, hashPassword(password), role);
    users.put(username, newUser);
    return true;
  }

  public boolean changePassword(String username, String oldPassword, String newPassword) {
    User user = users.get(username);
    if (user == null) return false;
    if (!user.getPasswordHash().equals(hashPassword(oldPassword))) return false;
    user.setPasswordHash(hashPassword(newPassword));
    return true;
  }
}