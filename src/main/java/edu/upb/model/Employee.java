package edu.upb.model;

import java.io.Serializable;

/**
 * Representa un empleado de la empresa ferroviaria.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class Employee implements Serializable {
  private static final long serialVersionUID = 1L;

  private String id;
  private String name;
  private String lastName;
  private String role;
  private String assignedTrainId;

  public Employee(String id, String name, String lastName, String role, String assignedTrainId) {
    this.id = id;
    this.name = name;
    this.lastName = lastName;
    this.role = role;
    this.assignedTrainId = assignedTrainId;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getLastName() {
    return lastName;
  }

  public String getRole() {
    return role;
  }

  public String getAssignedTrainId() {
    return assignedTrainId;
  }
}