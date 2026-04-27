package edu.upb.server.business;

import edu.upb.model.*;
import edu.upb.common.SaleDTO;
import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;
import edu.sebsx.app.hashtable.HashTable;
import edu.sebsx.model.iterator.Iterator;
import edu.sebsx.model.list.List;
import edu.sebsx.app.graph.GraphMatrix;

/**
 * Núcleo de la lógica de negocio del sistema de trenes.
 * Gestiona la flota de trenes, el procesamiento de compras,
 * la asignación de asientos y el cálculo de tarifas basado
 * en el algoritmo de Dijkstra sobre el grafo de estaciones.
 * 
 * @author Sebastian Pinto
 * @version 1.0
 */
public class SalesManager {
  private SinglyLinkedList<Train> fleet;
  private HashTable<String, Ticket> ticketCache;
  private GraphMatrix<Station, Double> graph;
  private SinglyLinkedList<Station> stations;
  private SinglyLinkedList<Route> routes;

  public SalesManager() {
    this.fleet = new SinglyLinkedList<>();
    this.ticketCache = new HashTable<>(32);
    this.graph = new GraphMatrix<>(11);
    this.stations = new SinglyLinkedList<>();
    this.routes = new SinglyLinkedList<>();
    initializeStationsAndGraph();
    initializeTestData();
  }

  private void initializeStationsAndGraph() {
    String[] names = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};
    for (String name : names) {
      Station station = new Station(name, name);
      stations.add(station);
      graph.addVertex(station);
    }
    double[][] distances = {
      {0, 30, 40, 50, -1, -1, 50, -1, -1, -1, -1},
      {30, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1},
      {40, -1, 0, -1, -1, -1, 80, 120, 110, -1, -1},
      {50, -1, -1, 0, 20, -1, -1, -1, -1, -1, -1},
      {-1, -1, -1, 20, 0, 65, -1, -1, -1, -1, -1},
      {-1, -1, -1, -1, 65, 0, 50, 65, 80, -1, -1},
      {50, -1, 80, -1, -1, 50, 0, 30, -1, -1, 145},
      {-1, -1, 120, -1, -1, 65, 30, 0, -1, 80, -1},
      {-1, -1, 110, -1, -1, 80, -1, -1, 0, -1, 145},
      {-1, -1, -1, -1, -1, -1, -1, 80, -1, 0, 120},
      {-1, -1, -1, -1, -1, -1, 145, -1, 145, 120, 0}
    };
    for (int i = 0; i < 11; i++) {
      for (int j = 0; j < 11; j++) {
        if (distances[i][j] > 0) graph.addEdge(getStation(i), getStation(j), distances[i][j]);
      }
    }
  }

  private Station getStation(int index) {
    Iterator<Station> it = stations.iterator();
    int i = 0;
    while (it.hasNext()) {
      Station station = it.next();
      if (i == index) return station;
      i++;
    }
    return null;
  }

  private Station findStationById(String id) {
    Iterator<Station> it = stations.iterator();
    while (it.hasNext()) {
      Station station = it.next();
      if (station.getId().equals(id)) return station;
    }
    return null;
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
    double fare = calculateFare(dto.getOrigin(), dto.getDestination());
    Ticket ticket = new Ticket();
    ticket.setTrainId(selectedTrain.getId());
    ticket.setPassengerName(dto.getPassengerName());
    ticket.setCategory(dto.getCategory());
    ticket.setSeatNumber(seat);
    ticket.setFareValue(fare);
    ticketCache.put(ticket.getRegistrationId(), ticket);
    return ticket;
  }

  private double calculateFare(String originId, String destinationId) {
    Station origin = findStationById(originId);
    Station destination = findStationById(destinationId);
    if (origin == null || destination == null) return 100.0;
    SinglyLinkedList<Station> path = graph.getShortestPath(origin, destination);
    if (path.isEmpty()) return 100.0;
    double totalDistance = 0.0;
    Iterator<Station> it = path.iterator();
    Station prev = null;
    while (it.hasNext()) {
      Station curr = it.next();
      if (prev != null) totalDistance += graph.getEdgeWeight(prev, curr);
      prev = curr;
    }
    return totalDistance * 100.0;
  }

  private String assignSeat(Train train, String category) {
    SinglyLinkedList<Wagon> wagons = train.getWagons();
    Iterator<Wagon> it = wagons.iterator();
    while (it.hasNext()) {
      Wagon wagon = it.next();
      PassengerWagon pw = (PassengerWagon) wagon;
      String seat = pw.assignSeat(category);
      if (seat != null) return wagon.getId() + "-" + seat;
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
      Train train = it.next();
      if (train.getId().equals(updatedTrain.getId())) {
        newList.add(updatedTrain);
        found = true;
      } else {
        newList.add(train);
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
      Train train = it.next();
      if (train.getId().equals(trainId)) {
        found = true;
      } else {
        newList.add(train);
      }
    }
    if (found) fleet = newList;
    return found;
  }

  public Train findTrainById(String trainId) {
    Iterator<Train> it = fleet.iterator();
    while (it.hasNext()) {
      Train train = it.next();
      if (train.getId().equals(trainId)) return train;
    }
    return null;
  }

  public List<Route> getAllRoutes() {
    SinglyLinkedList<Route> copy = new SinglyLinkedList<>();
    Iterator<Route> it = routes.iterator();
    while (it.hasNext()) copy.add(it.next());
    return copy;
  }

  public boolean addRoute(Route route) {
    routes.add(route);
    return true;
  }

  public boolean updateRoute(Route updatedRoute) {
    Iterator<Route> it = routes.iterator();
    SinglyLinkedList<Route> newList = new SinglyLinkedList<>();
    boolean found = false;
    while (it.hasNext()) {
      Route route = it.next();
      if (route.getId().equals(updatedRoute.getId())) {
        newList.add(updatedRoute);
        found = true;
      } else {
        newList.add(route);
      }
    }
    if (found) routes = newList;
    return found;
  }

  public boolean deleteRoute(String routeId) {
    Iterator<Route> it = routes.iterator();
    SinglyLinkedList<Route> newList = new SinglyLinkedList<>();
    boolean found = false;
    while (it.hasNext()) {
      Route route = it.next();
      if (route.getId().equals(routeId)) {
        found = true;
      } else {
        newList.add(route);
      }
    }
    if (found) routes = newList;
    return found;
  }
}