package edu.upb.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa un boleto de tren emitido por el sistema.
 * Contiene toda la información requerida por RF-16:
 * ID de registro, fechas, datos del pasajero, tren, asiento,
 * categoría, valor del pasaje y equipaje.
 * 
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
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
  private String passengerId;
  private String idType;
  private String address;
  private String phoneNumbers;
  private String contactPersonName;
  private String contactPersonLastName;
  private String contactPersonPhone;
  private String baggageId;
  private double baggageWeight;
  private String cargoWagonId;
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

  public String getPassengerId() {
    return passengerId;
  }

  public void setPassengerId(String passengerId) {
    this.passengerId = passengerId;
  }

  public String getIdType() {
    return idType;
  }

  public void setIdType(String idType) {
    this.idType = idType;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhoneNumbers() {
    return phoneNumbers;
  }

  public void setPhoneNumbers(String phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
  }

  public String getContactPersonName() {
    return contactPersonName;
  }

  public void setContactPersonName(String contactPersonName) {
    this.contactPersonName = contactPersonName;
  }

  public String getContactPersonLastName() {
    return contactPersonLastName;
  }

  public void setContactPersonLastName(String contactPersonLastName) {
    this.contactPersonLastName = contactPersonLastName;
  }

  public String getContactPersonPhone() {
    return contactPersonPhone;
  }

  public void setContactPersonPhone(String contactPersonPhone) {
    this.contactPersonPhone = contactPersonPhone;
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

  public double getBaggageWeight() {
    return baggageWeight;
  }

  public void setBaggageWeight(double baggageWeight) {
    this.baggageWeight = baggageWeight;
  }

  public String getCargoWagonId() {
    return cargoWagonId;
  }

  public void setCargoWagonId(String cargoWagonId) {
    this.cargoWagonId = cargoWagonId;
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
    return getRegistrationId();
  }
}