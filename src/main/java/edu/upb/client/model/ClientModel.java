package edu.upb.client.model;

import java.rmi.Naming;
import edu.upb.common.TicketInterface;
import edu.upb.common.SaleDTO;
import edu.upb.common.LoginDTO;
import edu.upb.model.Passenger;
import edu.upb.model.Route;
import edu.upb.model.Ticket;
import edu.upb.model.Train;
import edu.upb.client.observer.Subject;
import edu.sebsx.model.list.List;
import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;

/**
 * Modelo del cliente en la arquitectura MVC.
 * Establece la conexión RMI con el servidor y expone métodos para que el controlador
 * pueda realizar operaciones como iniciar sesión, comprar boletos, gestionar trenes
 * y consultar el orden de abordaje. Hereda de Subject para notificar cambios a las vistas.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class ClientModel extends Subject {
  private String logger;
  private String uri;
  private TicketInterface ticketService;

  public ClientModel(String ip, int port, String serviceName) {
    this.uri = "rmi://" + ip + ":" + port + "/" + serviceName;
    this.ticketService = null;
  }

  public boolean connect() {
    try {
      this.ticketService = (TicketInterface) Naming.lookup(uri);
      this.logger = "Connected to server at: " + uri;
      this.notifyObservers();
      return true;
    } catch (Exception e) {
      this.logger = "Failed to connect: " + e.getMessage();
      this.notifyObservers();
      return false;
    }
  }

  public boolean login(String username, String password) {
    try {
      LoginDTO dto = new LoginDTO(username, password);
      return ticketService.validateUser(dto);
    } catch (Exception e) {
      this.logger = "Login error: " + e.getMessage();
      this.notifyObservers();
      return false;
    }
  }

  public Ticket purchaseTicket(SaleDTO dto) {
    try {
      return ticketService.purchaseTicket(dto);
    } catch (Exception e) {
      this.logger = "Purchase error: " + e.getMessage();
      this.notifyObservers();
      return null;
    }
  }

  public String[] getStationNames() {
    try {
      return ticketService.getStationNames();
    } catch (Exception e) {
      this.logger = "Error fetching stations: " + e.getMessage();
      this.notifyObservers();
      return new String[0];
    }
  }

  public void register(String names) {
    try {
      Ticket ticket = new Ticket();
      ticket.setPassengerName(names);
      Ticket registered = ticketService.register(ticket);
      this.logger = "Registered with ticket: " + registered.getRegistrationId() + " for: " + names;
      this.notifyObservers();
    } catch (Exception e) {
      this.logger = "Registration failed: " + e.getMessage();
      this.notifyObservers();
    }
  }

  public List<Train> getAllTrains() {
    try {
      return ticketService.getAllTrains();
    } catch (Exception e) {
      logger = "Error fetching trains: " + e.getMessage();
      notifyObservers();
      return new SinglyLinkedList<>();
    }
  }

  public boolean addTrain(Train train) {
    try {
      return ticketService.addTrain(train);
    } catch (Exception e) {
      logger = "Error adding train: " + e.getMessage();
      notifyObservers();
      return false;
    }
  }

  public boolean updateTrain(Train train) {
    try {
      return ticketService.updateTrain(train);
    } catch (Exception e) {
      logger = "Error updating train: " + e.getMessage();
      notifyObservers();
      return false;
    }
  }

  public boolean deleteTrain(String trainId) {
    try {
      return ticketService.deleteTrain(trainId);
    } catch (Exception e) {
      logger = "Error deleting train: " + e.getMessage();
      notifyObservers();
      return false;
    }
  }

  public String getUserRole(String username) {
    try {
      return ticketService.getUserRole(username);
    } catch (Exception e) {
      logger = "Error fetching user role: " + e.getMessage();
      notifyObservers();
      return "OPERATOR";
    }
  }

  public boolean registerUser(String id, String username, String password, String role) {
    try {
      return ticketService.registerUser(id, username, password, role);
    } catch (Exception e) {
      logger = "Error registering user: " + e.getMessage();
      notifyObservers();
      return false;
    }
  }

  public boolean changePassword(String username, String oldPassword, String newPassword) {
    try {
      return ticketService.changePassword(username, oldPassword, newPassword);
    } catch (Exception e) {
      logger = "Error changing password: " + e.getMessage();
      notifyObservers();
      return false;
    }
  }

  public String getLogger() {
    return logger;
  }

  public SinglyLinkedList<Passenger> getBoardingOrder(String trainId) {
    try {
      return ticketService.getBoardingOrder(trainId);
    } catch (Exception e) {
      logger = "Error fetching boarding order: " + e.getMessage();
      notifyObservers();
      return new SinglyLinkedList<>();
    }
  }

  public List<Route> getAllRoutes() {
    try {
      return ticketService.getAllRoutes();
    } catch (Exception e) {
      logger = "Error fetching routes: " + e.getMessage();
      notifyObservers();
      return new SinglyLinkedList<>();
    }
  }

  public boolean addRoute(Route route) {
    try {
      return ticketService.addRoute(route);
    } catch (Exception e) {
      logger = "Error adding route: " + e.getMessage();
      notifyObservers();
      return false;
    }
  }

  public boolean updateRoute(Route route) {
    try {
      return ticketService.updateRoute(route);
    } catch (Exception e) {
      logger = "Error updating route: " + e.getMessage();
      notifyObservers();
      return false;
    }
  }

  public boolean deleteRoute(String routeId) {
    try {
      return ticketService.deleteRoute(routeId);
    } catch (Exception e) {
      logger = "Error deleting route: " + e.getMessage();
      notifyObservers();
      return false;
    }
  }
}