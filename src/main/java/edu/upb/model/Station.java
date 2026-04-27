package edu.upb.model;

import java.io.Serializable;

/**
 * Representa una estación del sistema ferroviario.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class Station implements Serializable {
  private static final long serialVersionUID = 1L;

  private String id;
  private String name;

  public Station(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Station station = (Station) obj;
    return id.equals(station.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return name;
  }
}