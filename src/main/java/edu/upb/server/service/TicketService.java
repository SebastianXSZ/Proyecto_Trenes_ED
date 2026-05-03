package edu.upb.server.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import edu.upb.common.TicketInterface;
import edu.upb.common.LoginDTO;
import edu.upb.common.SaleDTO;
import edu.upb.model.Train;
import edu.upb.model.Employee;
import edu.upb.model.Passenger;
import edu.upb.model.Route;
import edu.upb.model.Ticket;
import edu.upb.server.business.BoardingMonitor;
import edu.upb.server.business.SalesManager;
import edu.upb.server.business.SecurityModule;
import edu.upb.server.persistence.PersistenceModule;
import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;

/**
 * Implementación del objeto remoto RMI que expone los servicios del sistema de
 * venta de boletos.
 * Actúa como fachada del servidor, delegando las operaciones de negocio al
 * SalesManager,
 * SecurityModule y PersistenceModule. Los métodos aquí definidos pueden ser
 * invocados
 * remotamente desde el cliente a través de la interfaz TicketInterface.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class TicketService extends UnicastRemoteObject implements TicketInterface {

  private SalesManager salesManager;
  private SecurityModule securityModule;
  private PersistenceModule persistenceModule;

  public TicketService() throws RemoteException {
    super();
    this.salesManager = new SalesManager();
    this.securityModule = new SecurityModule();
    this.persistenceModule = new PersistenceModule();
  }

  @Override
  public Ticket register(Ticket ticket) throws RemoteException {
    Ticket newTicket = new Ticket();
    newTicket.setPassengerName(ticket.getPassengerName());
    persistenceModule.saveTicket(newTicket);
    return newTicket;
  }

  @Override
  public boolean validate(Ticket ticket) throws RemoteException {
    return persistenceModule.loadTickets().contains(ticket);
  }

  @Override
  public Ticket purchaseTicket(SaleDTO dto) throws RemoteException {
    Ticket ticket = salesManager.processTransaction(dto);
    if (ticket != null)
      persistenceModule.saveTicket(ticket);
    return ticket;
  }

  @Override
  public boolean validateUser(LoginDTO login) throws RemoteException {
    return securityModule.validateUser(login.getUsername(), login.getPassword());
  }

  @Override
  public String getUserRole(String username) throws RemoteException {
    return securityModule.getRole(username);
  }

  @Override
  public edu.upb.model.User getUser(String username) throws RemoteException {
    return securityModule.getUser(username);
  }

  @Override
  public boolean updateUser(edu.upb.model.User user) throws RemoteException {
    return securityModule.updateUser(user);
  }

  @Override
  public boolean registerUser(String id, String username, String password, String role, String name, String lastName)
      throws RemoteException {
    return securityModule.registerUser(id, username, password, role, name, lastName);
  }

  @Override
  public boolean changePassword(String username, String oldPassword, String newPassword) throws RemoteException {
    return securityModule.changePassword(username, oldPassword, newPassword);
  }

  @Override
  public String[] getStationNames() throws RemoteException {
    return new String[] { "Altea Park", "Belmont Square", "Cambridge Hills", "Davenport Gate",
        "East Hampton", "Fairmont Boulevard", "Grand Avenue", "Highbury Station",
        "Ivy District", "Jade Gardens", "Kensington Way" };
  }

  @Override
  public Train[] getAllTrains() throws RemoteException {
    return salesManager.getAllTrainsArray();
  }

  @Override
  public boolean addTrain(Train train) throws RemoteException {
    return salesManager.addTrain(train);
  }

  @Override
  public boolean updateTrain(Train train) throws RemoteException {
    return salesManager.updateTrain(train);
  }

  @Override
  public boolean deleteTrain(String trainId) throws RemoteException {
    return salesManager.deleteTrain(trainId);
  }

  @Override
  public SinglyLinkedList<Passenger> getBoardingOrder(String trainId) throws RemoteException {
    Train train = salesManager.findTrainById(trainId);
    if (train == null)
      return new SinglyLinkedList<>();
    BoardingMonitor monitor = new BoardingMonitor();
    return monitor.getBoardingOrder(train);
  }

  @Override
  public Route[] getAllRoutes() throws RemoteException {
    return salesManager.getAllRoutesArray();
  }

  @Override
  public boolean addRoute(Route route) throws RemoteException {
    return salesManager.addRoute(route);
  }

  @Override
  public boolean updateRoute(Route route) throws RemoteException {
    return salesManager.updateRoute(route);
  }

  @Override
  public boolean deleteRoute(String routeId) throws RemoteException {
    return salesManager.deleteRoute(routeId);
  }

  @Override
  public Employee[] getAllEmployees() throws RemoteException {
    return salesManager.getAllEmployeesArray();
  }

  @Override
  public boolean addEmployee(Employee employee) throws RemoteException {
    return salesManager.addEmployee(employee);
  }

  @Override
  public boolean updateEmployee(Employee employee) throws RemoteException {
    return salesManager.updateEmployee(employee);
  }

  @Override
  public boolean deleteEmployee(String employeeId) throws RemoteException {
    return salesManager.deleteEmployee(employeeId);
  }

  @Override
  public double getShortestDistance(String origin, String destination) throws RemoteException {
    return salesManager.getShortestDistance(origin, destination);
  }
}