package edu.upb.server.model;

/**
 * Representa una acción registrada en el historial del servidor.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class Action {

  private String description;
  private long timestamp;

  public Action(String description) {
    this.description = description;
    this.timestamp = System.currentTimeMillis();
  }

  public String getDescription() {
    return description;
  }

  public String getTimestamp() {
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(new java.util.Date(timestamp));
  }
}