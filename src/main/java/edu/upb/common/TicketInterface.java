package edu.upb.common;

import edu.upb.model.Train;
import edu.upb.model.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import edu.upb.model.Employee;
import edu.upb.model.Passenger;
import edu.upb.model.Route;
import edu.upb.model.Ticket;

public interface TicketInterface extends Remote {
  Ticket register(Ticket ticket) throws RemoteException;

  boolean validate(Ticket ticket) throws RemoteException;

  Ticket purchaseTicket(SaleDTO dto) throws RemoteException;

  boolean validateUser(LoginDTO login) throws RemoteException;

  String getUserRole(String username) throws RemoteException;

  User getUser(String username) throws RemoteException;

  boolean updateUser(edu.upb.model.User user) throws RemoteException;

  boolean registerUser(String id, String username, String password, String role, String name, String lastName)
      throws RemoteException;

  boolean changePassword(String username, String oldPassword, String newPassword) throws RemoteException;

  String[] getStationNames() throws RemoteException;

  Train[] getAllTrains() throws RemoteException;

  boolean addTrain(Train train) throws RemoteException;

  boolean updateTrain(Train train) throws RemoteException;

  boolean deleteTrain(String trainId) throws RemoteException;

  Passenger[] getBoardingOrder(String trainId) throws RemoteException;

  Route[] getAllRoutes() throws RemoteException;

  boolean addRoute(Route route) throws RemoteException;

  boolean updateRoute(Route route) throws RemoteException;

  boolean deleteRoute(String routeId) throws RemoteException;

  Employee[] getAllEmployees() throws RemoteException;

  boolean addEmployee(Employee employee) throws RemoteException;

  boolean updateEmployee(Employee employee) throws RemoteException;

  boolean deleteEmployee(String employeeId) throws RemoteException;

  double getShortestDistance(String origin, String destination) throws RemoteException;
}