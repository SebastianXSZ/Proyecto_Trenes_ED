package edu.upb.model;

import java.io.Serializable;
import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;

/**
 * Representa una ruta entre estaciones del sistema ferroviario.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
public class Route implements Serializable {
  private static final long serialVersionUID = 1L;

  private String id;
  private transient SinglyLinkedList<Station> stations;
  private double distance;
  private String departureTime;
  private String arrivalTime;

  public Route(String id) {
    this.id = id;
    this.stations = new SinglyLinkedList<>();
    this.distance = 0.0;
    this.departureTime = "";
    this.arrivalTime = "";
  }

  public String getId() {
    return id;
  }

  public SinglyLinkedList<Station> getStations() {
    return stations;
  }

  public void addStation(Station station) {
    stations.add(station);
  }

  public double getDistance() {
    return distance;
  }

  public void setDistance(double distance) {
    this.distance = distance;
  }

  public String getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(String departureTime) {
    this.departureTime = departureTime;
  }

  public String getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(String arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    out.defaultWriteObject();
    int size = 0;
    if (stations != null) {
      edu.sebsx.model.iterator.Iterator<Station> it = stations.iterator();
      while (it.hasNext()) { it.next(); size++; }
    }
    out.writeInt(size);
    if (stations != null) {
      edu.sebsx.model.iterator.Iterator<Station> it = stations.iterator();
      while (it.hasNext()) { out.writeObject(it.next()); }
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    in.defaultReadObject();
    int size = in.readInt();
    this.stations = new SinglyLinkedList<>();
    for (int i = 0; i < size; i++) {
      stations.add((Station) in.readObject());
    }
  }
}