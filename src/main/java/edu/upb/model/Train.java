package edu.upb.model;

import java.io.Serializable;
import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;

/**
 * Representa un tren de la flota ferroviaria.
 * Contiene vagones de pasajeros y de carga.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class Train implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  private String id;
  private String name;
  private double loadCapacity;
  private int mileage;
  private SinglyLinkedList<Wagon> wagons;
  
  public Train(String id, String name, double loadCapacity, int mileage) {
    this.id = id;
    this.name = name;
    this.loadCapacity = loadCapacity;
    this.mileage = mileage;
    this.wagons = new SinglyLinkedList<>();
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public double getLoadCapacity() {
    return loadCapacity;
  }
  
  public void setLoadCapacity(double loadCapacity) {
    if (loadCapacity < 0) throw new IllegalArgumentException("Load capacity cannot be negative.");
    this.loadCapacity = loadCapacity;
  }
  
  public int getMileage() {
    return mileage;
  }
  
  public void setMileage(int mileage) {
    if (mileage < 0) throw new IllegalArgumentException("Mileage cannot be negative.");
    this.mileage = mileage;
  }
  
  public SinglyLinkedList<Wagon> getWagons() {
    return wagons;
  }
  
  public void addWagon(Wagon wagon) {
    wagons.add(wagon);
  }
  
  public boolean validateWagonLimit() {
    return true;
  }
}