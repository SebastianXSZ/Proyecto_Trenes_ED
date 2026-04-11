package edu.upb.server.model;

import edu.sebsx.app.stack.list.Stack;
import edu.upb.server.observer.Subject;

public class History extends Subject {

  private Stack<Action> actions;

  public History() {
    this.actions = new Stack<>();
  }

  public void addAction(String description) {
    this.actions.push(new Action(description));
    this.notifyObservers();
  }

  public String getLastAction() {
    if (actions.isEmpty()) {
      return "No actions yet.";
    }
    Action lastAction = actions.peek();
    return lastAction.getTimestamp() + ": " + lastAction.getDescription();
  }
}