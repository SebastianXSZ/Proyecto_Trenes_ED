package edu.upb.server.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import edu.upb.common.TicketInterface;
import edu.upb.common.LoginDTO;
import edu.upb.common.SaleDTO;
import edu.upb.model.Ticket;
import edu.upb.model.Customer;

public class TicketService extends UnicastRemoteObject implements TicketInterface {

  private int index = 0;
  private Ticket[] tickets = new Ticket[100];

  public TicketService() throws RemoteException {
    super();
  }

  @Override
  public Ticket register(Ticket ticket) throws RemoteException {
    Ticket newTicket = new Ticket(String.valueOf(index + 1), new Customer("1", ticket.getCustomerName()));
    tickets[index] = newTicket;
    index++;
    return newTicket;
  }

  @Override
  public boolean validate(Ticket ticket) throws RemoteException {
    for (int i = 0; i < index; i++) {
      if (tickets[i].getId().equals(ticket.getId())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Ticket purchaseTicket(SaleDTO dto) throws RemoteException {
    return new Ticket("PUR-" + System.currentTimeMillis(), new Customer("1", dto.getPassengerName()));
  }

  @Override
  public boolean validateUser(LoginDTO login) throws RemoteException {
    return "admin".equals(login.getUsername()) && "admin".equals(login.getPassword());
  }
}