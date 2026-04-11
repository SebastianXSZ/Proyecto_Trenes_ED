package edu.upb.client.observer;

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
    this.observers.forEach(observer -> {
      observer.update();
      return null;
    });
  }
}