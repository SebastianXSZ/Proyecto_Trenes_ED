package edu.upb.model;

import java.io.Serializable;

/**
 * Representa un cliente del sistema de venta de boletos.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class Customer implements Serializable {

  private static final long serialVersionUID = 2L;

  private String id;
  private String names;

  public Customer(String id, String names) {
    this.id = id;
    this.names = names;
  }

  public String getId() {
    return id;
  }

  public String getNames() {
    return names;
  }

  public void setNames(String names) {
    this.names = names;
  }
}