package edu.upb.model;

import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;

public class CargoWagon extends Wagon {
  private SinglyLinkedList<Baggage> storedBaggage;

  public CargoWagon(String id) {
    super(id, "Cargo", 0);
    this.storedBaggage = new SinglyLinkedList<>();
  }

  public boolean addBaggage(Baggage item) {
    return storedBaggage.add(item);
  }

  public SinglyLinkedList<Baggage> getStoredBaggage() {
    return storedBaggage;
  }
}