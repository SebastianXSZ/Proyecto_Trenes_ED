package edu.upb.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TicketInterface extends Remote {
  Ticket register(Ticket ticket) throws RemoteException;

  boolean validate(Ticket ticket) throws RemoteException;
}