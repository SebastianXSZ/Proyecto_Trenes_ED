package edu.upb.common;

import java.io.Serializable;

/**
 * Objeto de transferencia de datos para la compra de boletos.
 * Encapsula todos los datos necesarios para procesar una venta.
 * Implementa Serializable para ser transmitido por RMI.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class SaleDTO implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  private String trainId;
  private String origin;
  private String destination;
  private String passengerName;
  private String passengerLastName;
  private String passengerId;
  private String idType;
  private String address;
  private String phoneNumbers;
  private String contactPersonName;
  private String contactPersonLastName;
  private String contactPersonPhone;
  private String category;
  private String userId;
  private double baggageWeight;
  
  public SaleDTO() {}
  
  public SaleDTO(String trainId, String origin, String destination, String passengerName, String passengerLastName, String passengerId, String idType, String address, String phoneNumbers, String contactPersonName, String contactPersonLastName, String contactPersonPhone, String category, String userId, double baggageWeight) {
    this.trainId = trainId;
    this.origin = origin;
    this.destination = destination;
    this.passengerName = passengerName;
    this.passengerLastName = passengerLastName;
    this.passengerId = passengerId;
    this.idType = idType;
    this.address = address;
    this.phoneNumbers = phoneNumbers;
    this.contactPersonName = contactPersonName;
    this.contactPersonLastName = contactPersonLastName;
    this.contactPersonPhone = contactPersonPhone;
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