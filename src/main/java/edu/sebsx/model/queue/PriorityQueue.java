package edu.sebsx.model.queue;

public interface PriorityQueue<E> extends Queue<E> {

  public boolean insert(E element);

  public boolean insert(int index, E element);
}