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

  public void addTrain(Train train) {
    fleet.add(train);
  }

  public Train[] getAllTrains() {
    Train[] trains = new Train[fleet.size()];
    Iterator<Train> it = fleet.iterator();
    int i = 0;
    while (it.hasNext()) trains[i++] = it.next();
    return trains;
  }

  public void updateTrain(Train updatedTrain) {
    Iterator<Train> it = fleet.iterator();
    while (it.hasNext()) {
      Train t = it.next();
      if (t.getId().equals(updatedTrain.getId())) {
        fleet.remove(t);
        fleet.add(updatedTrain);
        return;
      }
    }
  }

  public void deleteTrain(String trainId) {
    Iterator<Train> it = fleet.iterator();
    while (it.hasNext()) {
      Train t = it.next();
      if (t.getId().equals(trainId)) {
        fleet.remove(t);
        return;
      }
    }
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

  public String assignSeat(Train train, String category) {
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

  public boolean checkAvailability(String trainId, String category) {
    Train train = findTrainById(trainId);
    if (train == null) return false;
    SinglyLinkedList<Wagon> wagons = train.getWagons();
    Iterator<Wagon> it = wagons.iterator();
    while (it.hasNext()) {
      Wagon w = it.next();
      PassengerWagon pw = (PassengerWagon) w;
      if (pw.getAvailableSeatsByCategory(category) > 0) return true;
    }
    return false;
  }

  private Train findTrainById(String trainId) {
    Iterator<Train> it = fleet.iterator();
    while (it.hasNext()) {
      Train t = it.next();
      if (t.getId().equals(trainId)) return t;
    }
    return null;
  }
}