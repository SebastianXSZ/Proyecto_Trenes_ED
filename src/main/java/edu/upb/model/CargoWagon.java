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
  private transient SinglyLinkedList<Baggage> storedBaggage;

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

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    out.defaultWriteObject();
    int size = 0;
    if (storedBaggage != null) {
      edu.sebsx.model.iterator.Iterator<Baggage> it = storedBaggage.iterator();
      while (it.hasNext()) {
        it.next();
        size++;
      }
    }
    out.writeInt(size);
    if (storedBaggage != null) {
      edu.sebsx.model.iterator.Iterator<Baggage> it = storedBaggage.iterator();
      while (it.hasNext()) {
        out.writeObject(it.next());
      }
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    in.defaultReadObject();
    int size = in.readInt();
    this.storedBaggage = new SinglyLinkedList<>();
    for (int i = 0; i < size; i++) {
      storedBaggage.add((Baggage) in.readObject());
    }
  }
}