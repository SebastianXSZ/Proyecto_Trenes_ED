package edu.upb.server.model;

import edu.sebsx.app.stack.list.Stack;
import edu.upb.server.observer.Subject;

/**
 * Historial de acciones del servidor para auditoría.
 * Almacena las acciones realizadas en una pila (TAD Stack) y notifica
 * a los observadores cuando se registra una nueva acción.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
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
    if (actions.isEmpty()) return "No actions yet.";
    Action lastAction = actions.peek();
    return lastAction.getTimestamp() + ": " + lastAction.getDescription();
  }
}