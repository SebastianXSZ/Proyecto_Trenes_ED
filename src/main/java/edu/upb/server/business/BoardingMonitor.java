package edu.upb.server.business;

import edu.upb.model.Passenger;
import edu.upb.model.Train;
import edu.upb.model.Wagon;
import edu.upb.model.PassengerWagon;
import edu.sebsx.app.array.Array;
import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;
import edu.sebsx.app.queue.priorityqueue.PriorityQueue;
import edu.sebsx.app.stack.list.Stack;
import edu.sebsx.model.iterator.Iterator;

/**
 * Control de abordaje de pasajeros para la publicación en monitores de estación.
 * Implementa el orden de embarque exigido por RF-18: los pasajeros abordan de atrás hacia adelante,
 * respetando el privilegio de tarifa (Premium > Ejecutivo > Estándar).
 * Utiliza un TAD PriorityQueue para ordenar por categoría y un TAD Stack para invertir
 * el orden y simular el abordaje desde los vagones traseros.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class BoardingMonitor {

  public SinglyLinkedList<Passenger> getBoardingOrder(Train train) {
    PriorityQueue<Passenger> priorityQueue = new PriorityQueue<>(3);
    SinglyLinkedList<Wagon> wagons = train.getWagons();
    Iterator<Wagon> wagonIt = wagons.iterator();
    while (wagonIt.hasNext()) {
      Wagon w = wagonIt.next();
      PassengerWagon pw = (PassengerWagon) w;
      Array<Passenger> passengers = pw.getPassengers();
      for (int i = 0; i < passengers.size(); i++) {
        Passenger p = passengers.get(i);
        if (p != null) {
          String category = pw.getPassengerCategory(p);
          int priority = getPriority(category);
          priorityQueue.insert(priority, p);
        }
      }
    }
    Stack<Passenger> stack = new Stack<>();
    while (!priorityQueue.isEmpty()) stack.push(priorityQueue.extract());
    SinglyLinkedList<Passenger> boardingOrder = new SinglyLinkedList<>();
    while (!stack.isEmpty()) boardingOrder.add(stack.pop());
    return boardingOrder;
  }
  
  private int getPriority(String category) {
    if (category == null) return 2;
    switch (category) {
      case "Premium": return 0;
      case "Ejecutivo": return 1;
      default: return 2;
    }
  }
}