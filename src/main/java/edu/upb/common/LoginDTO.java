package edu.upb.common;

import java.io.Serializable;

/**
 * Objeto de transferencia de datos para la autenticación de usuarios.
 * Contiene el nombre de usuario y la contraseña.
 * Implementa Serializable para ser transmitido por RMI.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class LoginDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String username;
  private String password;

  public LoginDTO() {
  }

  public LoginDTO(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}