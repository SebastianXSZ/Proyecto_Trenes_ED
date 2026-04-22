package edu.upb.server.business;

import edu.upb.model.*;
import edu.upb.common.SaleDTO;
import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;
import edu.sebsx.app.hashtable.HashTable;
import edu.sebsx.model.iterator.Iterator;
import edu.sebsx.model.list.List;

public class SalesManager {
  private SinglyLinkedList<Train> fleet;
  private HashTable<String, Ticket> ticketCache;

  public SalesManager() {
    this.fleet = new SinglyLinkedList<>();
    this.ticketCache = new HashTable<>(32);
    initializeTestData();
  }

  private void initializeTestData() {
    Train train = new MercedesBenzTrain("T1", "Expreso UPB", 1000, 5000);
    PassengerWagon pw1 = new PassengerWagon("W1");
    PassengerWagon pw2 = new PassengerWagon("W2");
    CargoWagon cw1 = new CargoWagon("C1");
    train.addWagon(pw1);
    train.addWagon(pw2);
    train.addWagon(cw1);
    fleet.add(train);
  }

  public Ticket processTransaction(SaleDTO dto) {
    if (fleet.isEmpty()) return null;
    Train selectedTrain = fleet.iterator().next();
    String seat = assignSeat(selectedTrain, dto.getCategory());
    if (seat == null) return null;
    Ticket ticket = new Ticket();
    ticket.setTrainId(selectedTrain.getId());
    ticket.setPassengerName(dto.getPassengerName());
    ticket.setCategory(dto.getCategory());
    ticket.setSeatNumber(seat);
    ticket.setFareValue(100.0);
    ticketCache.put(ticket.getRegistrationId(), ticket);
    return ticket;
  }

  private String assignSeat(Train train, String category) {
    SinglyLinkedList<Wagon> wagons = train.getWagons();
    Iterator<Wagon> it = wagons.iterator();
    while (it.hasNext()) {
      Wagon w = it.next();
      PassengerWagon pw = (PassengerWagon) w;
      String seat = pw.assignSeat(category);
      if (seat != null) return w.getId() + "-" + seat;
    }
    return null;
  }


  public List<Train> getAllTrains() {
    SinglyLinkedList<Train> copy = new SinglyLinkedList<>();
    Iterator<Train> it = fleet.iterator();
    while (it.hasNext()) copy.add(it.next());
    return copy;
  }

  public boolean addTrain(Train train) {
    fleet.add(train);
    return true;
  }

  public boolean updateTrain(Train updatedTrain) {
    Iterator<Train> it = fleet.iterator();
    SinglyLinkedList<Train> newList = new SinglyLinkedList<>();
    boolean found = false;
    while (it.hasNext()) {
      Train t = it.next();
      if (t.getId().equals(updatedTrain.getId())) {
        newList.add(updatedTrain);
        found = true;
      } else {
        newList.add(t);
      }
    }
    if (found) fleet = newList;
    return found;
  }

  public boolean deleteTrain(String trainId) {
    Iterator<Train> it = fleet.iterator();
    SinglyLinkedList<Train> newList = new SinglyLinkedList<>();
    boolean found = false;
    while (it.hasNext()) {
      Train t = it.next();
      if (t.getId().equals(trainId)) {
        found = true;
      } else {
        newList.add(t);
      }
    }
    if (found) fleet = newList;
    return found;
  }
}