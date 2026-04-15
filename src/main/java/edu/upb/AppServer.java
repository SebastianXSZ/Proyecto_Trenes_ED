package edu.upb;

import edu.upb.server.controller.ServerController;
import edu.upb.server.factory.ServerFactory;

public class AppServer {
  public static void main(String[] args) {
    try {
      ServerController server = ServerFactory.create();
      server.init();
      System.out.println("Servidor RMI iniciado y listo.");
    } catch (Exception e) {
      System.err.println("Error al iniciar el servidor: " + e.getMessage());
      e.printStackTrace();
    }
  }
}