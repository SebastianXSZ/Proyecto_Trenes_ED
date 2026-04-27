package edu.upb.server.persistence;

import edu.upb.model.Ticket;
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
}