package edu.upb.server.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import edu.upb.common.TicketInterface;
import edu.upb.common.LoginDTO;
import edu.upb.common.SaleDTO;
import edu.upb.model.Train;
import edu.upb.model.Ticket;
import edu.upb.server.business.SalesManager;
import edu.upb.server.business.SecurityModule;
import edu.upb.server.persistence.PersistenceModule;
import edu.sebsx.model.list.List;

public class TicketService extends UnicastRemoteObject implements TicketInterface {

  private transient SalesManager salesManager;
  private transient SecurityModule securityModule;
  private transient PersistenceModule persistenceModule;

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
    if (ticket != null) persistenceModule.saveTicket(ticket);
    return ticket;
  }

  @Override
  public boolean validateUser(LoginDTO login) throws RemoteException {
    return securityModule.validateUser(login.getUsername(), login.getPassword());
  }

  @Override
  public String[] getStationNames() throws RemoteException {
    return new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};
  }

  @Override
  public List<Train> getAllTrains() throws RemoteException {
    return salesManager.getAllTrains();
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
}