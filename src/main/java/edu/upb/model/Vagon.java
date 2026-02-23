package edu.upb.model;

public class Vagon {

  private String id;
  private String type;
  private int capacity;

  public Vagon(String id, String type, int capacity) {
    this.id = id;
    this.type = type;
    this.capacity = capacity;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    if (capacity < 0) throw new IllegalArgumentException("Capacity cannot be negative.");
    this.capacity = capacity;
  }
}