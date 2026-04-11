package edu.upb.model;

import java.io.Serializable;

public class Baggage implements Serializable {
  private static final long serialVersionUID = 1L;

  private String id;
  private double weight;
  private String passengerId;

  public Baggage(String id, double weight, String passengerId) {
    this.id = id;
    this.weight = weight;
    this.passengerId = passengerId;
  }

  public String getId() {
    return id;
  }

  public double getWeight() {
    return weight;
  }

  public String getPassengerId() {
    return passengerId;
  }
}