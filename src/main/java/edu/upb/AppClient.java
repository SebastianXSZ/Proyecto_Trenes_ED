package edu.upb;

import edu.upb.client.controller.ClientController;
import edu.upb.client.factory.ClientFactory;

public class AppClient {
  public static void main(String[] args) {
    try {
      ClientController client = ClientFactory.create();
      client.init();
    } catch (Exception e) {
      System.err.println("Error al iniciar el cliente: " + e.getMessage());
      e.printStackTrace();
    }
  }
}