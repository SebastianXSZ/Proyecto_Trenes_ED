package edu.upb.server.business;

import edu.upb.model.*;
import edu.upb.common.SaleDTO;
import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;
import edu.sebsx.app.hashtable.HashTable;
import edu.sebsx.model.iterator.Iterator;
import edu.sebsx.model.list.List;
import edu.sebsx.app.graph.GraphMatrix;
import edu.upb.server.persistence.PersistenceModule;

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
  private SinglyLinkedList<Employee> employees;
  private PersistenceModule persistenceModule;

  public SalesManager() {
    this.persistenceModule = new PersistenceModule();
    this.ticketCache = new HashTable<>(32);
    this.graph = new GraphMatrix<>(11);
    this.stations = new SinglyLinkedList<>();
    this.routes = new SinglyLinkedList<>();
    this.employees = new SinglyLinkedList<>();
    this.fleet = new SinglyLinkedList<>();
    initializeTestData();
    initializeStationsAndGraph();
    persistenceModule.saveTrains(this.fleet);
    persistenceModule.saveRoutes(this.routes);
    persistenceModule.saveEmployees(this.employees);
  }

  private void initializeStationsAndGraph() {
    this.stations = new SinglyLinkedList<>();
    Iterator<Route> routeIt = routes.iterator();
    while (routeIt.hasNext()) {
      Route route = routeIt.next();
      Iterator<Station> stationIt = route.getStations().iterator();
      Station prev = null;
      while (stationIt.hasNext()) {
        Station curr = stationIt.next();
        if (findStationById(curr.getId()) == null) {
          stations.add(curr);
          graph.addVertex(curr);
        } else {
          curr = findStationById(curr.getId());
        }
        if (prev != null) {
          double segmentDistance = route.getDistance() > 0 ? route.getDistance() : 50.0;
          graph.addEdge(prev, curr, segmentDistance);
        }
        prev = curr;
      }
    }
  }

  private Station findStationById(String id) {
    Iterator<Station> it = stations.iterator();
    while (it.hasNext()) {
      Station station = it.next();
      if (station.getId().equals(id))
        return station;
    }
    return null;
  }

  private void initializeTestData() {
    // Tren de prueba
    Train train = new MercedesBenzTrain("T1", "Expreso UPB", 1000, 5000);
    PassengerWagon pw1 = new PassengerWagon("W1");
    PassengerWagon pw2 = new PassengerWagon("W2");
    CargoWagon cw1 = new CargoWagon("C1");
    train.addWagon(pw1);
    train.addWagon(pw2);
    train.addWagon(cw1);
    fleet.add(train);

    // Mapeo de letras a nombres de estaciones
    String[] stationNames = {
        "Altea Park", // A
        "Belmont Square", // B
        "Cambridge Hills", // C
        "Davenport Gate", // D
        "East Hampton", // E
        "Fairmont Boulevard", // F
        "Grand Avenue", // G
        "Highbury Station", // H
        "Ivy District", // I
        "Jade Gardens", // J
        "Kensington Way" // K
    };

    // Matriz de distancias (solo celdas con valor)
    // Formato: origen, destino, distancia
    String[][] connections = {
        { "A", "B", "30" }, { "A", "C", "40" }, { "A", "D", "50" }, { "A", "E", "50" },
        { "B", "D", "40" }, { "B", "E", "80" }, { "B", "F", "120" }, { "B", "G", "110" },
        { "C", "E", "80" }, { "C", "F", "120" }, { "C", "G", "110" },
        { "D", "E", "20" }, { "D", "H", "65" },
        { "E", "F", "50" }, { "E", "H", "80" },
        { "F", "G", "30" }, { "F", "I", "145" },
        { "G", "I", "145" },
        { "H", "I", "30" }
    };

    for (String[] conn : connections) {
      int fromIdx = conn[0].charAt(0) - 'A';
      int toIdx = conn[1].charAt(0) - 'A';
      String fromName = stationNames[fromIdx];
      String toName = stationNames[toIdx];
      double dist = Double.parseDouble(conn[2]);
      Route r = new Route(fromName + "-" + toName);
      r.addStation(new Station(fromName, fromName));
      r.addStation(new Station(toName, toName));
      r.setDistance(dist);
      r.setDepartureTime("08:00");
      r.setArrivalTime("09:00");
      routes.add(r);
    }

    initializeStationsAndGraph();
    persistenceModule.saveRoutes(this.routes);
  }

  public Ticket processTransaction(SaleDTO dto) {
    if (fleet.isEmpty())
      return null;
    // Validar equipaje (RF-17: límite 80kg por maleta)
    if (dto.getBaggageWeight() > 80) {
      return null;
    }

    Train selectedTrain = findTrainById(dto.getTrainId());
    if (selectedTrain == null)
      selectedTrain = fleet.iterator().next(); // Fallback

    // Crear pasajero
    String passengerId = dto.getPassengerId() != null ? dto.getPassengerId() : "P-" + System.currentTimeMillis();
    Passenger passenger = new Passenger(passengerId, dto.getPassengerName(), 0);

    // Asignar asiento y agregar al vagón
    String seatInfo = assignSeatAndAddPassenger(selectedTrain, dto.getCategory(), passenger);
    if (seatInfo == null)
      return null;

    // Manejar equipaje
    String cargoWagonId = "N/A";
    String baggageId = "N/A";
    if (dto.getBaggageWeight() > 0) {
      Baggage baggage = new Baggage("B-" + System.currentTimeMillis(), dto.getBaggageWeight(), passengerId);
      CargoWagon cw = findAvailableCargoWagon(selectedTrain);
      if (cw != null) {
        cw.addBaggage(baggage);
        cargoWagonId = cw.getId();
        baggageId = baggage.getId();
      }
    }

    double fare = calculateFare(dto.getOrigin(), dto.getDestination());
    if (fare < 0)
      return null;

    // Aplicar multiplicadores de categoría
    if ("Premium".equalsIgnoreCase(dto.getCategory()))
      fare *= 2.0;
    else if ("Ejecutivo".equalsIgnoreCase(dto.getCategory()))
      fare *= 1.5;

    Ticket ticket = new Ticket();
    ticket.setTrainId(selectedTrain.getId());
    ticket.setPassengerName(dto.getPassengerName());
    ticket.setPassengerLastName(dto.getPassengerLastName());
    ticket.setPassengerId(passengerId);
    ticket.setIdType(dto.getIdType());
    ticket.setAddress(dto.getAddress());
    ticket.setPhoneNumbers(dto.getPhoneNumbers());
    ticket.setContactPersonName(dto.getContactPersonName());
    ticket.setContactPersonLastName(dto.getContactPersonLastName());
    ticket.setContactPersonPhone(dto.getContactPersonPhone());
    ticket.setCategory(dto.getCategory());
    ticket.setSeatNumber(seatInfo);
    ticket.setFareValue(fare);
    ticket.setBaggageId(baggageId);
    ticket.setBaggageWeight(dto.getBaggageWeight());
    ticket.setCargoWagonId(cargoWagonId);

    // Buscar horarios en la ruta (si coincide origen/destino)
    Route route = findRouteByStations(dto.getOrigin(), dto.getDestination());
    if (route != null) {
      // Aquí se deberían parsear las horas, pero por ahora las fijamos como String si
      // el modelo lo permitiera
      // Como el modelo usa LocalDateTime, pondremos valores actuales + offset
      ticket.setDepartureTime(java.time.LocalDateTime.now().plusHours(1));
      ticket.setArrivalTime(java.time.LocalDateTime.now().plusHours(3));
    }

    ticketCache.put(ticket.getRegistrationId(), ticket);
    return ticket;
  }

  private CargoWagon findAvailableCargoWagon(Train train) {
    Iterator<Wagon> it = train.getWagons().iterator();
    while (it.hasNext()) {
      Wagon w = it.next();
      if (w instanceof CargoWagon cw)
        return cw;
    }
    return null;
  }

  private Route findRouteByStations(String originId, String destinationId) {
    Iterator<Route> it = routes.iterator();
    while (it.hasNext()) {
      Route r = it.next();
      // Verificación simplificada: si contiene ambas estaciones
      boolean hasOrigin = false;
      boolean hasDest = false;
      Iterator<Station> sit = r.getStations().iterator();
      while (sit.hasNext()) {
        Station s = sit.next();
        if (s.getId().equals(originId))
          hasOrigin = true;
        if (s.getId().equals(destinationId))
          hasDest = true;
      }
      if (hasOrigin && hasDest)
        return r;
    }
    return null;
  }

  private double calculateFare(String originId, String destinationId) {
    if (originId.equals(destinationId))
      return -1.0;
    Station origin = findStationById(originId);
    Station destination = findStationById(destinationId);
    if (origin == null || destination == null)
      return -1.0;
    SinglyLinkedList<Station> path = graph.getShortestPath(origin, destination);
    if (path.isEmpty())
      return -1.0;
    double totalDistance = 0.0;
    Iterator<Station> it = path.iterator();
    Station prev = null;
    while (it.hasNext()) {
      Station curr = it.next();
      if (prev != null)
        totalDistance += graph.getEdgeWeight(prev, curr);
      prev = curr;
    }
    return totalDistance * 100.0;
  }

  private String assignSeatAndAddPassenger(Train train, String category, Passenger passenger) {
    SinglyLinkedList<Wagon> wagons = train.getWagons();
    Iterator<Wagon> it = wagons.iterator();
    while (it.hasNext()) {
      Wagon wagon = it.next();
      if (wagon instanceof PassengerWagon pw) {
        if (pw.getAvailableSeatsByCategory(category) > 0) {
          String seat = pw.assignSeat(category);
          if (seat != null) {
            pw.addPassenger(passenger, category);
            return wagon.getId() + "-" + seat;
          }
        }
      }
    }
    return null;
  }

  public List<Train> getAllTrains() {
    SinglyLinkedList<Train> copy = new SinglyLinkedList<>();
    Iterator<Train> it = fleet.iterator();
    while (it.hasNext())
      copy.add(it.next());
    System.out.println("DEBUG SalesManager.getAllTrains: fleet size=" + fleet.size() + ", copy size=" + copy.size());
    return copy;
  }

  public boolean addTrain(Train train) {
    System.out.println("DEBUG SalesManager.addTrain: recibiendo tren " + train.getId());
    fleet.add(train);
    persistenceModule.saveTrains(fleet);
    System.out.println("DEBUG SalesManager.addTrain: flota guardada, tamaño actual: " + fleet.size());
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
    if (found) {
      fleet = newList;
      persistenceModule.saveTrains(fleet);
    }
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
    if (found) {
      fleet = newList;
      persistenceModule.saveTrains(fleet);
    }
    return found;
  }

  public Train findTrainById(String trainId) {
    Iterator<Train> it = fleet.iterator();
    while (it.hasNext()) {
      Train train = it.next();
      if (train.getId().equals(trainId))
        return train;
    }
    return null;
  }

  public List<Route> getAllRoutes() {
    SinglyLinkedList<Route> copy = new SinglyLinkedList<>();
    Iterator<Route> it = routes.iterator();
    while (it.hasNext())
      copy.add(it.next());
    return copy;
  }

  public boolean addRoute(Route route) {
    routes.add(route);
    persistenceModule.saveRoutes(routes);
    initializeStationsAndGraph(); // Reconstruir grafo
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
    if (found) {
      routes = newList;
      persistenceModule.saveRoutes(routes);
      initializeStationsAndGraph();
    }
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
    if (found) {
      routes = newList;
      persistenceModule.saveRoutes(routes);
      initializeStationsAndGraph(); // Reconstruir grafo
    }
    return found;
  }

  public List<Employee> getAllEmployees() {
    SinglyLinkedList<Employee> copy = new SinglyLinkedList<>();
    Iterator<Employee> it = employees.iterator();
    while (it.hasNext())
      copy.add(it.next());
    return copy;
  }

  public boolean addEmployee(Employee employee) {
    employees.add(employee);
    persistenceModule.saveEmployees(employees);
    return true;
  }

  public boolean updateEmployee(Employee updatedEmployee) {
    Iterator<Employee> it = employees.iterator();
    SinglyLinkedList<Employee> newList = new SinglyLinkedList<>();
    boolean found = false;
    while (it.hasNext()) {
      Employee emp = it.next();
      if (emp.getId().equals(updatedEmployee.getId())) {
        newList.add(updatedEmployee);
        found = true;
      } else {
        newList.add(emp);
      }
    }
    if (found) {
      employees = newList;
      persistenceModule.saveEmployees(employees);
    }
    return found;
  }

  public boolean deleteEmployee(String employeeId) {
    Iterator<Employee> it = employees.iterator();
    SinglyLinkedList<Employee> newList = new SinglyLinkedList<>();
    boolean found = false;
    while (it.hasNext()) {
      Employee emp = it.next();
      if (emp.getId().equals(employeeId)) {
        found = true;
      } else {
        newList.add(emp);
      }
    }
    if (found) {
      employees = newList;
      persistenceModule.saveEmployees(employees);
    }
    return found;
  }

  public double getShortestDistance(String originId, String destinationId) {
    if (originId.equals(destinationId))
      return -1;
    Station origin = findStationById(originId);
    Station destination = findStationById(destinationId);
    if (origin == null || destination == null)
      return -1;
    SinglyLinkedList<Station> path = graph.getShortestPath(origin, destination);
    if (path.isEmpty())
      return -1;
    double totalDistance = 0.0;
    Iterator<Station> it = path.iterator();
    Station prev = null;
    while (it.hasNext()) {
      Station curr = it.next();
      if (prev != null)
        totalDistance += graph.getEdgeWeight(prev, curr);
      prev = curr;
    }
    return totalDistance;
  }

  public Train[] getAllTrainsArray() {
    int size = fleet.size();
    Train[] array = new Train[size];
    final int[] idx = { 0 };
    fleet.forEach(t -> {
      array[idx[0]++] = t;
      return null;
    });
    return array;
  }

  public Route[] getAllRoutesArray() {
    int size = routes.size();
    Route[] array = new Route[size];
    final int[] idx = { 0 };
    routes.forEach(r -> {
      array[idx[0]++] = r;
      return null;
    });
    return array;
  }

  public Employee[] getAllEmployeesArray() {
    int size = employees.size();
    Employee[] array = new Employee[size];
    final int[] idx = { 0 };
    employees.forEach(e -> {
      array[idx[0]++] = e;
      return null;
    });
    return array;
  }
}