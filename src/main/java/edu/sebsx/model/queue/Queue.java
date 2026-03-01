package edu.sebsx.model.queue;

public interface Queue<E> {

    public E peek();

    public E extract();

    public boolean insert(E element);
}