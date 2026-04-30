package edu.upb.server.persistence;

import edu.upb.model.Ticket;
import edu.upb.model.User;
import edu.upb.model.Train;
import edu.upb.model.Route;
import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;
import java.io.*;

/**
 * Módulo de persistencia para guardar y cargar tickets desde archivos.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class PersistenceModule {

  private static final String TICKETS_FILE = "tickets.dat";
  private static final String TRAINS_FILE = "trains.dat";
  private static final String ROUTES_FILE = "routes.dat";
  private static final String USERS_FILE = "users.dat";

  public boolean saveTicket(Ticket ticket) {
    SinglyLinkedList<Ticket> tickets = loadTickets();
    tickets.add(ticket);
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TICKETS_FILE))) {
      oos.writeObject(tickets);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  @SuppressWarnings("unchecked")
  public SinglyLinkedList<Ticket> loadTickets() {
    File file = new File(TICKETS_FILE);
    if (!file.exists()) return new SinglyLinkedList<>();
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
      return (SinglyLinkedList<Ticket>) ois.readObject();
    } catch (Exception e) {
      return new SinglyLinkedList<>();
    }
  }

  public boolean saveTrains(SinglyLinkedList<Train> trains) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TRAINS_FILE))) {
      oos.writeObject(trains);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  @SuppressWarnings("unchecked")
  public SinglyLinkedList<Train> loadTrains() {
    File file = new File(TRAINS_FILE);
    if (!file.exists()) return new SinglyLinkedList<>();
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
      return (SinglyLinkedList<Train>) ois.readObject();
    } catch (Exception e) {
      return new SinglyLinkedList<>();
    }
  }

  public boolean saveRoutes(SinglyLinkedList<Route> routes) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ROUTES_FILE))) {
      oos.writeObject(routes);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  @SuppressWarnings("unchecked")
  public SinglyLinkedList<Route> loadRoutes() {
    File file = new File(ROUTES_FILE);
    if (!file.exists()) return new SinglyLinkedList<>();
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
      return (SinglyLinkedList<Route>) ois.readObject();
    } catch (Exception e) {
      return new SinglyLinkedList<>();
    }
  }

  public boolean saveUsers(SinglyLinkedList<User> users) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
      oos.writeObject(users);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  @SuppressWarnings("unchecked")
  public SinglyLinkedList<User> loadUsers() {
    File file = new File(USERS_FILE);
    if (!file.exists()) return new SinglyLinkedList<>();
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
      return (SinglyLinkedList<User>) ois.readObject();
    } catch (Exception e) {
      return new SinglyLinkedList<>();
    }
  }
}