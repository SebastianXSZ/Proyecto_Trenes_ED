package edu.upb;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.upb.server.business.SecurityModule;

class SecurityModuleTest {

  private SecurityModule securityModule;

  @BeforeEach
  void setUp() {
    securityModule = new SecurityModule();
  }

  @Test
  void testValidateUserCorrect() {
    assertTrue(securityModule.validateUser("admin", "1234"));
    assertTrue(securityModule.validateUser("operador", "123"));
  }

  @Test
  void testValidateUserIncorrect() {
    assertFalse(securityModule.validateUser("admin", "mal"));
    assertFalse(securityModule.validateUser("x", "x"));
  }
}