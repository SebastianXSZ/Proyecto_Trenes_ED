package edu.upb.model;

public class Train {

  private String id;
  private String name;
  private double loadCapacity;
  private int mileage;

  public Train(String id, String name, double loadCapacity, int mileage) {
    this.id = id;
    this.name = name;
    this.loadCapacity = loadCapacity;
    this.mileage = mileage;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getLoadCapacity() {
    return loadCapacity;
  }

  public void setLoadCapacity(double loadCapacity) {
    if (loadCapacity < 0) throw new IllegalArgumentException("Load capacity cannot be negative.");
    this.loadCapacity = loadCapacity;
  }

  public int getMileage() {
    return mileage;
  }

  public void setMileage(int mileage) {
    if (mileage < 0) throw new IllegalArgumentException("Mileage cannot be negative.");
    this.mileage = mileage;
  }
}