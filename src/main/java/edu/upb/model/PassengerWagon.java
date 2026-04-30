package edu.upb.model;

import edu.sebsx.app.array.Array;

/**
 * Vagón de pasajeros con capacidad para 40 personas (2 pilotos, 4 personal de abordo y 34 pasajeros).
 * Los asientos se distribuyen en 4 lugares para tarifa premium, 8 para tarifa ejecutiva y 22 para tarifa estándar.
 * Gestiona la asignación de asientos según disponibilidad por categoría.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class PassengerWagon extends Wagon {
  private static final int PREMIUM_SEATS = 4;
  private static final int EXECUTIVE_SEATS = 8;
  private static final int STANDARD_SEATS = 22;

  private transient Array<Passenger> currentPassengers;
  private transient Array<String> passengerCategories;

  public PassengerWagon(String id) {
    super(id, "Passenger", PREMIUM_SEATS + EXECUTIVE_SEATS + STANDARD_SEATS);
    this.currentPassengers = new Array<>(getCapacity());
    this.passengerCategories = new Array<>(getCapacity());
  }

  public String assignSeat(String category) {
    int seatNumber = -1;
    if (category.equalsIgnoreCase("Premium") && getAvailableSeatsByCategory("Premium") > 0) {
      seatNumber = findAvailableSeat(0, PREMIUM_SEATS);
    } else if (category.equalsIgnoreCase("Ejecutivo") && getAvailableSeatsByCategory("Ejecutivo") > 0) {
      seatNumber = findAvailableSeat(PREMIUM_SEATS, PREMIUM_SEATS + EXECUTIVE_SEATS);
    } else if (category.equalsIgnoreCase("Estándar") && getAvailableSeatsByCategory("Estándar") > 0) {
      seatNumber = findAvailableSeat(PREMIUM_SEATS + EXECUTIVE_SEATS, getCapacity());
    }
    if (seatNumber == -1) {
      return null;
    }
    return String.valueOf(seatNumber + 1);
  }

  private int findAvailableSeat(int start, int end) {
    for (int i = start; i < end; i++) {
      if (i >= currentPassengers.size() || currentPassengers.get(i) == null) {
        return i;
      }
    }
    return -1;
  }

  public boolean isFull() {
    return currentPassengers.size() == getCapacity();
  }

  public boolean addPassenger(Passenger passenger, String category) {
    if (isFull()) {
      return false;
    }
    int seat = Integer.parseInt(assignSeat(category)) - 1;
    if (seat >= 0) {
      if (seat >= currentPassengers.size()) {
        for (int i = currentPassengers.size(); i <= seat; i++) {
          currentPassengers.add(null);
          passengerCategories.add(null);
        }
      }
      currentPassengers.set(seat, passenger);
      passengerCategories.set(seat, category);
      return true;
    }
    return false;
  }

  public int getAvailableSeats() {
    return getCapacity() - currentPassengers.size();
  }

  public int getAvailableSeatsByCategory(String category) {
    int available = 0;
    int start = 0;
    int end = 0;
    if (category.equalsIgnoreCase("Premium")) {
      start = 0;
      end = PREMIUM_SEATS;
    } else if (category.equalsIgnoreCase("Ejecutivo")) {
      start = PREMIUM_SEATS;
      end = PREMIUM_SEATS + EXECUTIVE_SEATS;
    } else if (category.equalsIgnoreCase("Estándar")) {
      start = PREMIUM_SEATS + EXECUTIVE_SEATS;
      end = getCapacity();
    }
    for (int i = start; i < end; i++) {
      if (i >= currentPassengers.size() || currentPassengers.get(i) == null) {
        available++;
      }
    }
    return available;
  }

  public Array<Passenger> getPassengers() {
    Array<Passenger> nonNull = new Array<>(currentPassengers.size());
    for (int i = 0; i < currentPassengers.size(); i++) {
      Passenger p = currentPassengers.get(i);
      if (p != null) {
        nonNull.add(p);
      }
    }
    return nonNull;
  }

  public String getPassengerCategory(Passenger passenger) {
    for (int i = 0; i < currentPassengers.size(); i++) {
      if (currentPassengers.get(i) != null && currentPassengers.get(i).equals(passenger)) {
        return passengerCategories.get(i);
      }
    }
    return "Estándar";
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    out.defaultWriteObject();
    int size = currentPassengers.size();
    out.writeInt(size);
    for (int i = 0; i < size; i++) {
      out.writeObject(currentPassengers.get(i));
      out.writeObject(passengerCategories.get(i));
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    in.defaultReadObject();
    int size = in.readInt();
    this.currentPassengers = new Array<>(getCapacity());
    this.passengerCategories = new Array<>(getCapacity());
    for (int i = 0; i < size; i++) {
      currentPassengers.add((Passenger) in.readObject());
      passengerCategories.add((String) in.readObject());
    }
  }
}