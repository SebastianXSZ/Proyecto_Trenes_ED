package edu.upb.common.observer;

import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;

public abstract class Subject implements Observable {
  protected SinglyLinkedList<Observer> observers;

  protected Subject() {
    this.observers = new SinglyLinkedList<>();
  }

  @Override
  public void attach(Observer observer) {
    this.observers.add(observer);
  }

  @Override
  public void detach(Observer observer) {
    this.observers.remove(observer);
  }

  @Override
  public void notifyObservers() {
    notifyObservers("default");
  }

  public void notifyObservers(String event) {
    this.observers.forEach(observer -> {
      observer.update(event);
      return null;
    });
  }
}
