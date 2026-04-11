package edu.upb.common;

import java.io.Serializable;

public class SaleDTO implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  private String trainId;
  private String origin;
  private String destination;
  private String passengerName;
  private String category;
  private String userId;
  private double baggageWeight;
  
  public SaleDTO() {}
  
  public SaleDTO(String trainId, String origin, String destination, String passengerName, String category, String userId, double baggageWeight) {
    this.trainId = trainId;
    this.origin = origin;
    this.destination = destination;
    this.passengerName = passengerName;
    this.category = category;
    this.userId = userId;
    this.baggageWeight = baggageWeight;
  }
  
  public String getTrainId() {
    return trainId;
  }
  
  public void setTrainId(String trainId) {
    this.trainId = trainId;
  }
  
  public String getOrigin() {
    return origin;
  }
  
  public void setOrigin(String origin) {
    this.origin = origin;
  }
  
  public String getDestination() {
    return destination;
  }
  
  public void setDestination(String destination) {
    this.destination = destination;
  }
  
  public String getPassengerName() {
    return passengerName;
  }
  
  public void setPassengerName(String passengerName) {
    this.passengerName = passengerName;
  }
  
  public String getCategory() {
    return category;
  }
  
  public void setCategory(String category) {
    this.category = category;
  }
  
  public String getUserId() {
    return userId;
  }
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public double getBaggageWeight() {
    return baggageWeight;
  }
  
  public void setBaggageWeight(double baggageWeight) {
    this.baggageWeight = baggageWeight;
  }
}