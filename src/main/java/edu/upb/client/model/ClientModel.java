package edu.upb.client.model;

import java.rmi.Naming;
import edu.upb.common.TicketInterface;
import edu.upb.common.SaleDTO;
import edu.upb.common.LoginDTO;
import edu.upb.model.Ticket;
import edu.upb.model.Customer;
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

  public String getLogger() {
    return logger;
  }
}