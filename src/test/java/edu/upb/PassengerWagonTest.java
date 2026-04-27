package edu.upb;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.upb.model.PassengerWagon;
import edu.upb.model.Passenger;

class PassengerWagonTest {

  private PassengerWagon wagon;

  @BeforeEach
  void setUp() {
    wagon = new PassengerWagon("W1");
  }

  @Test
  void testAssignSeatPremium() {
    String seat = wagon.assignSeat("Premium");
    assertNotNull(seat);
    assertEquals("1", seat);
  }

  @Test
  void testIsFullInitiallyFalse() {
    assertFalse(wagon.isFull());
  }

  @Test
  void testAddPassenger() {
    Passenger p = new Passenger("P1", "Juan", 25);
    assertTrue(wagon.addPassenger(p, "Premium"));
  }
}