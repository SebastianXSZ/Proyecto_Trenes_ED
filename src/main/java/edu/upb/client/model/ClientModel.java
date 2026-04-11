package edu.upb.client.model;

import java.rmi.Naming;

import edu.upb.client.model.observer.Subject;
import edu.upb.server.model.ticket.Customer;
import edu.upb.server.model.ticket.Ticket;
import edu.upb.server.model.ticket.TicketInterface;

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
      this.logger = ("Connecting to server at: " + uri);
      this.notifyObservers();
      return true;
    } catch (Exception e) {
      this.logger = ("Failed to connect to server at: " + uri);
      this.notifyObservers();
      e.printStackTrace();
      return false;
    }
  }

  public TicketInterface getTicketService() {
    return ticketService;
  }

  public void register(String names) {
    try {
      Ticket ticket = new Ticket("", new Customer("1", names));
      Ticket ticketRegistered = this.getTicketService().register(ticket);
      this.logger = ("Registered with ticket: " + ticketRegistered.getId() + " for customer: "
          + ticketRegistered.getCustomerName());
      this.notifyObservers();
    } catch (Exception e) {
      e.printStackTrace();
      this.logger = "Registration failed: " + e.getMessage();
      this.notifyObservers();
    }
  }

  public String getLogger() {
    return logger;
  }
}
