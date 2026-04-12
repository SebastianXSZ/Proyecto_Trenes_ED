package edu.upb.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Ticket implements Serializable {
  
  private static final long serialVersionUID = 1L;

  private String registrationId;
  private LocalDateTime purchaseDate;
  private LocalDateTime departureTime;
  private LocalDateTime arrivalTime;
  private String passengerName;
  private String passengerLastName;
  private String trainId;
  private String seatNumber;
  private String category;
  private double fareValue;
  private String baggageId;
  private String wagonId;

  public Ticket() {
    this.purchaseDate = LocalDateTime.now();
    this.registrationId = generateId();
  }

  private String generateId() {
    return "TKT-" + System.currentTimeMillis();
  }

  public String getRegistrationId() {
    return registrationId;
  }

  public LocalDateTime getPurchaseDate() {
    return purchaseDate;
  }

  public LocalDateTime getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(LocalDateTime departureTime) {
    this.departureTime = departureTime;
  }

  public LocalDateTime getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(LocalDateTime arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public String getPassengerName() {
    return passengerName;
  }

  public void setPassengerName(String passengerName) {
    this.passengerName = passengerName;
  }

  public String getPassengerLastName() {
    return passengerLastName;
  }

  public void setPassengerLastName(String passengerLastName) {
    this.passengerLastName = passengerLastName;
  }

  public String getTrainId() {
    return trainId;
  }

  public void setTrainId(String trainId) {
    this.trainId = trainId;
  }

  public String getSeatNumber() {
    return seatNumber;
  }

  public void setSeatNumber(String seatNumber) {
    this.seatNumber = seatNumber;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public double getFareValue() {
    return fareValue;
  }

  public void setFareValue(double fareValue) {
    this.fareValue = fareValue;
  }

  public String getBaggageId() {
    return baggageId;
  }

  public void setBaggageId(String baggageId) {
    this.baggageId = baggageId;
  }

  public String getWagonId() {
    return wagonId;
  }

  public void setWagonId(String wagonId) {
    this.wagonId = wagonId;
  }

  public String getCustomerName() {
    return passengerName + " " + passengerLastName;
  }

  public String getId() {
    return registrationId;
  }
}