package edu.upb.model;

import edu.sebsx.app.array.Array;

public class PassengerWagon extends Wagon {
  
  private static final int PREMIUM_SEATS = 4;
  private static final int EXECUTIVE_SEATS = 8;
  private static final int STANDARD_SEATS = 22;
  
  private Array<Passenger> currentPassengers;
  
  public PassengerWagon(String id) {
    super(id, "Passenger", PREMIUM_SEATS + EXECUTIVE_SEATS + STANDARD_SEATS);
    this.currentPassengers = new Array<>(getCapacity());
  }
  
  public String assignSeat(String category) {
    return "A1";
  }
  
  public boolean isFull() {
    return currentPassengers.size() == getCapacity();
  }
  
  public boolean addPassenger(Passenger passenger) {
    if (isFull()) {
      return false;
    }
    return currentPassengers.add(passenger);
  }
  
  public int getAvailableSeats() {
    return getCapacity() - currentPassengers.size();
  }
}