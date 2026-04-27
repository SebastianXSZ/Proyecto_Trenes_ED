package edu.upb;

import edu.upb.client.controller.ClientController;
import edu.upb.client.factory.ClientFactory;

/**
 * Punto de entrada del cliente RMI.
 * Inicia la interfaz gráfica y conecta con el servidor.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class AppClient {
  public static void main() {
    try {
      ClientController client = ClientFactory.create();
      client.init();
    } catch (Exception e) {
      System.err.println("Error al iniciar el cliente: " + e.getMessage());
      e.printStackTrace();
    }
  }
}