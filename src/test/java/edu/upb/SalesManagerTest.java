package edu.upb;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.upb.server.business.SalesManager;
import edu.upb.common.SaleDTO;
import edu.upb.model.Ticket;

class SalesManagerTest {

  @BeforeAll
  static void cleanPersistence() {
    new File("routes.dat").delete();
    new File("trains.dat").delete();
    new File("users.dat").delete();
    new File("employees.dat").delete();
  }

  private SalesManager salesManager;

  @BeforeEach
  void setUp() {
    salesManager = new SalesManager();
    // Añadir rutas de prueba
    edu.upb.model.Route r1 = new edu.upb.model.Route("R1");
    r1.addStation(new edu.upb.model.Station("Altea Park", "Altea Park"));
    r1.addStation(new edu.upb.model.Station("Davenport Gate", "Davenport Gate"));
    r1.addStation(new edu.upb.model.Station("East Hampton", "East Hampton"));
    r1.setDistance(50.0); // 50km entre estaciones adyacentes
    salesManager.addRoute(r1);
  }

  @Test
  void testProcessTransactionValidRoute() {
    SaleDTO dto = new SaleDTO();
    dto.setOrigin("Altea Park");
    dto.setDestination("East Hampton");
    dto.setCategory("Premium");
    dto.setPassengerName("John Doe");

    Ticket ticket = salesManager.processTransaction(dto);

    assertNotNull(ticket);
    assertEquals("John Doe", ticket.getPassengerName());
    assertEquals("Premium", ticket.getCategory());
    assertNotNull(ticket.getSeatNumber());
    assertEquals(20000.0, ticket.getFareValue(), 0.01);
  }

  @Test
  void testProcessTransactionDirectRoute() {
    SaleDTO dto = new SaleDTO();
    dto.setOrigin("Altea Park");
    dto.setDestination("Davenport Gate");
    dto.setCategory("Ejecutivo");
    dto.setPassengerName("Jane Doe");

    Ticket ticket = salesManager.processTransaction(dto);

    assertNotNull(ticket);
    assertEquals("Jane Doe", ticket.getPassengerName());
    assertEquals(7500.0, ticket.getFareValue(), 0.01);
  }

  @Test
  void testProcessTransactionInvalidStation() {
    SaleDTO dto = new SaleDTO();
    dto.setOrigin("Altea Park");
    dto.setDestination("Nowhere Station");
    dto.setCategory("Estándar");
    dto.setPassengerName("Lost Passenger");

    Ticket invalidTicket = salesManager.processTransaction(dto);
    assertNull(invalidTicket);
  }

  @Test
  void testProcessTransactionSameStation() {
    SaleDTO dto = new SaleDTO();
    dto.setOrigin("Altea Park");
    dto.setDestination("Altea Park");
    dto.setCategory("Premium");
    dto.setPassengerName("Lazy Passenger");

    Ticket ticket = salesManager.processTransaction(dto);
    assertNull(ticket);
  }
}
