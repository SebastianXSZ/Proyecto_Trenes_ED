package edu.upb.server.business;

import edu.upb.model.Passenger;
import edu.upb.model.Train;
import edu.upb.model.Wagon;
import edu.upb.model.PassengerWagon;
import edu.sebsx.app.array.Array;
import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;
import edu.sebsx.app.stack.list.Stack;
import edu.sebsx.model.iterator.Iterator;

/**
 * Control de abordaje de pasajeros para la publicación en monitores de
 * estación.
 * Implementa el orden de embarque exigido por RF-18: los pasajeros abordan de
 * atrás hacia adelante,
 * respetando el privilegio de tarifa (Premium > Ejecutivo > Estándar).
 * Utiliza un TAD PriorityQueue para ordenar por categoría y un TAD Stack para
 * invertir
 * el orden y simular el abordaje desde los vagones traseros.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class BoardingMonitor {

  public SinglyLinkedList<Passenger> getBoardingOrder(Train train) {
    SinglyLinkedList<Passenger> boardingOrder = new SinglyLinkedList<>();
    SinglyLinkedList<Wagon> wagons = train.getWagons();

    // Convertir a algo que permita recorrer en orden inverso (Pila para invertir
    // vagones)
    Stack<Wagon> wagonStack = new Stack<>();
    Iterator<Wagon> wagonIt = wagons.iterator();
    while (wagonIt.hasNext()) {
      wagonStack.push(wagonIt.next());
    }

    // Recorrer vagones desde el último hasta el primero
    while (!wagonStack.isEmpty()) {
      Wagon w = wagonStack.pop();
      if (w instanceof PassengerWagon pw) {
        // Procesar categorías en orden: primero Premium, luego Ejecutivo, luego
        // Estándar
        addPassengersByCategory(pw, "Premium", boardingOrder);
        addPassengersByCategory(pw, "Ejecutivo", boardingOrder);
        addPassengersByCategory(pw, "Estándar", boardingOrder);
      }
    }
    return boardingOrder;
  }

  private void addPassengersByCategory(PassengerWagon pw, String category, SinglyLinkedList<Passenger> list) {
    Array<Passenger> passengers = pw.getPassengers();
    // Nota: getPassengers devuelve una copia con solo los no nulos
    // Pero necesitamos filtrar por categoría. PassengerWagon debería tener un
    // método para esto o
    // podemos usar getPassengerCategory si lo hace público (lo es).
    for (int i = 0; i < passengers.size(); i++) {
      Passenger p = passengers.get(i);
      if (p != null && category.equalsIgnoreCase(pw.getPassengerCategory(p))) {
        list.add(p);
      }
    }
  }
}