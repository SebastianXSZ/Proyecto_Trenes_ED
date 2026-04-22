package edu.upb.common;

import edu.upb.model.Train;
import java.rmi.Remote;
import java.rmi.RemoteException;
import edu.upb.model.Ticket;

public interface TicketInterface extends Remote {
  Ticket register(Ticket ticket) throws RemoteException;
  boolean validate(Ticket ticket) throws RemoteException;
  Ticket purchaseTicket(SaleDTO dto) throws RemoteException;
  boolean validateUser(LoginDTO login) throws RemoteException;
  String[] getStationNames() throws RemoteException;
  edu.sebsx.model.list.List<Train> getAllTrains() throws RemoteException;
  boolean addTrain(Train train) throws RemoteException;
  boolean updateTrain(Train train) throws RemoteException;
  boolean deleteTrain(String trainId) throws RemoteException;
}