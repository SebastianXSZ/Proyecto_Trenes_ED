package edu.upb.model;

public class MercedesBenzTrain extends Train {
  
  private static final int MAX_WAGONS = 28;

  public MercedesBenzTrain(String id, String name, double loadCapacity, int mileage) {
    super(id, name, loadCapacity, mileage);
  }

  @Override
  public boolean validateWagonLimit() {
    return getWagons().size() <= MAX_WAGONS;
  }
}