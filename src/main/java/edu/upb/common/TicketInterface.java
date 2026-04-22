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
  void addTrain(Train train) throws RemoteException;
  Train[] getAllTrains() throws RemoteException;
  void updateTrain(Train train) throws RemoteException;
  void deleteTrain(String trainId) throws RemoteException;
}