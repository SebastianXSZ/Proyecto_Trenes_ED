package edu.upb.client.model;

import java.rmi.Naming;
import edu.upb.common.TicketInterface;
import edu.upb.common.SaleDTO;
import edu.upb.model.Ticket;
import edu.upb.client.observer.Subject;

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

  public String[] getStationNames() {
    try {
      return ticketService.getStationNames();
    } catch (Exception e) {
      this.logger = "Error fetching stations: " + e.getMessage();
      this.notifyObservers();
      return new String[0];
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

  public String getLogger() {
    return logger;
  }
}