package edu.upb.model;

import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;

/**
 * Vagón de equipaje que transporta el equipaje de los pasajeros.
 * Solo se permite dos maletas de 80 kilogramos cada una por pasajero.
 * Por cada dos vagones de pasajeros se utiliza al menos un vagón de carga.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
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