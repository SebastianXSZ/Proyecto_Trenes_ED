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