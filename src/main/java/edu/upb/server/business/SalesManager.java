package edu.upb.server.business;

import edu.upb.model.*;
import edu.upb.common.SaleDTO;
import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;
import edu.sebsx.app.hashtable.HashTable;
import edu.sebsx.model.iterator.Iterator;

public class SalesManager {
  private SinglyLinkedList<Train> fleet;
  private HashTable<String, Ticket> ticketCache;

  public SalesManager() {
    this.fleet = new SinglyLinkedList<>();
    this.ticketCache = new HashTable<>(32);
  }

  public void addTrain(Train train) {
    fleet.add(train);
  }

  public Ticket processTransaction(SaleDTO dto) {
    Train selectedTrain = findTrainById(dto.getTrainId());
    if (selectedTrain == null) {
      return null;
    }
    String seat = assignSeat(selectedTrain, dto.getCategory());
    if (seat == null) {
      return null;
    }
    Ticket ticket = new Ticket();
    ticket.setTrainId(dto.getTrainId());
    ticket.setPassengerName(dto.getPassengerName());
    ticket.setCategory(dto.getCategory());
    ticket.setSeatNumber(seat);
    ticket.setFareValue(100.0);
    ticketCache.put(ticket.getRegistrationId(), ticket);
    return ticket;
  }

  private Train findTrainById(String trainId) {
    Iterator<Train> it = fleet.iterator();
    while (it.hasNext()) {
      Train t = it.next();
      if (t.getId().equals(trainId)) {
        return t;
      }
    }
    return null;
  }

  public String assignSeat(Train train, String category) {
    SinglyLinkedList<Wagon> wagons = train.getWagons();
    Iterator<Wagon> it = wagons.iterator();
    while (it.hasNext()) {
      Wagon w = it.next();
      PassengerWagon pw = (PassengerWagon) w;
      String seat = pw.assignSeat(category);
      if (seat != null) {
        return w.getId() + "-" + seat;
      }
    }
    return null;
  }

  public boolean checkAvailability(String trainId, String category) {
    Train train = findTrainById(trainId);
    if (train == null) {
      return false;
    }
    SinglyLinkedList<Wagon> wagons = train.getWagons();
    Iterator<Wagon> it = wagons.iterator();
    while (it.hasNext()) {
      Wagon w = it.next();
      PassengerWagon pw = (PassengerWagon) w;
      if (pw.getAvailableSeatsByCategory(category) > 0) {
        return true;
      }
    }
    return false;
  }
}