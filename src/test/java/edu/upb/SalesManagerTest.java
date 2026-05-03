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
        salesManager = new SalesManager(); // Ya carga todas las rutas de la matriz
    }

    @Test
    void testProcessTransactionValidRoute() {
        SaleDTO dto = new SaleDTO();
        dto.setOrigin("Altea Park");
        dto.setDestination("Belmont Square"); // A-B 30 km
        dto.setCategory("Premium");
        dto.setPassengerName("John Doe");

        Ticket ticket = salesManager.processTransaction(dto);
        assertNotNull(ticket);
        assertEquals("John Doe", ticket.getPassengerName());
        assertEquals("Premium", ticket.getCategory());
        assertNotNull(ticket.getSeatNumber());
        assertEquals(6000.0, ticket.getFareValue(), 0.01); // 30*100*2
    }

    @Test
    void testProcessTransactionIndirectRoute() {
        SaleDTO dto = new SaleDTO();
        dto.setOrigin("Altea Park");
        dto.setDestination("Fairmont Boulevard"); // A-F sin conexión directa, debería haber ruta vía B u otra
        dto.setCategory("Ejecutivo");
        dto.setPassengerName("Jane Doe");

        Ticket ticket = salesManager.processTransaction(dto);
        assertNotNull(ticket);
        // El precio será el camino más corto, no podemos predecir exactamente, 
        // pero sí podemos verificar que no sea null.
    }

    @Test
    void testProcessTransactionInvalidStation() {
        SaleDTO dto = new SaleDTO();
        dto.setOrigin("Altea Park");
        dto.setDestination("Nowhere Station");
        dto.setCategory("Estándar");
        dto.setPassengerName("Lost Passenger");

        Ticket ticket = salesManager.processTransaction(dto);
        assertNull(ticket);
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
