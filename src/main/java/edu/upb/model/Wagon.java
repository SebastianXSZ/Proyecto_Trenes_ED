package edu.upb.model;

import java.io.Serializable;

public abstract class Wagon implements Serializable {

  private static final long serialVersionUID = 1L;
  
  protected String id;
  protected String type;
  protected int capacity;
  
  protected Wagon(String id, String type, int capacity) {
    this.id = id;
    this.type = type;
    this.capacity = capacity;
  }
  
  public String getId() {
    return id;
  }
  
  public String getType() {
    return type;
  }
  
  public int getCapacity() {
    return capacity;
  }
}