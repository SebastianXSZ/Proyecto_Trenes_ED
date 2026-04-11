package edu.upb.server.business;

import edu.upb.model.Station;
import edu.sebsx.app.graph.GraphMatrix;
import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;
import edu.sebsx.model.iterator.Iterator;

public class SalesManager {
  private GraphMatrix<Station> graph;
  private SinglyLinkedList<Station> stations;

  public SalesManager() {
    this.graph = new GraphMatrix<>(11);
    this.stations = new SinglyLinkedList<>();
    initializeGraph();
  }

  private void initializeGraph() {
    String[] names = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};
    for (String name : names) {
      Station s = new Station(name, name);
      stations.add(s);
      graph.addVertex(s);
    }
    double[][] distances = {
      {0, 30, 40, 50, -1, -1, 50, -1, -1, -1, -1},
      {30, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1},
      {40, -1, 0, -1, -1, -1, 80, 120, 110, -1, -1},
      {50, -1, -1, 0, 20, -1, -1, -1, -1, -1, -1},
      {-1, -1, -1, 20, 0, 65, -1, -1, -1, -1, -1},
      {-1, -1, -1, -1, 65, 0, 50, 65, 80, -1, -1},
      {50, -1, 80, -1, -1, 50, 0, 30, -1, -1, 145},
      {-1, -1, 120, -1, -1, 65, 30, 0, -1, 80, -1},
      {-1, -1, 110, -1, -1, 80, -1, -1, 0, -1, 145},
      {-1, -1, -1, -1, -1, -1, -1, 80, -1, 0, 120},
      {-1, -1, -1, -1, -1, -1, 145, -1, 145, 120, 0}
    };
    for (int i = 0; i < 11; i++) {
      for (int j = 0; j < 11; j++) {
        if (distances[i][j] > 0) {
          graph.addEdge(getStation(i), getStation(j), distances[i][j]);
        }
      }
    }
  }

  private Station getStation(int index) {
    Iterator<Station> it = stations.iterator();
    int i = 0;
    while (it.hasNext()) {
      Station s = it.next();
      if (i == index) {
        return s;
      }
      i++;
    }
    return null;
  }

  public SinglyLinkedList<Station> calculateShortestRoute(Station origin, Station destination) {
    return graph.getShortestPath(origin, destination);
  }

  public GraphMatrix<Station> getGraph() {
    return graph;
  }
}