package edu.upb;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.upb.server.business.SalesManager;
import edu.upb.common.SaleDTO;
import edu.upb.model.Ticket;

class SalesManagerTest {

  private SalesManager salesManager;

  @BeforeEach
  void setUp() {
    salesManager = new SalesManager();
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
    assertEquals(7000.0, ticket.getFareValue(), 0.01);
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
    assertEquals(5000.0, ticket.getFareValue(), 0.01);
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
