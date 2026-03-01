package edu.sebsx.model.array;

import edu.sebsx.model.collection.AbstractCollection;
import edu.sebsx.model.iterator.Iterator;

public abstract class AbstractArray<E> extends AbstractCollection<E> implements IArray<E>, BufferArray {

  @Override
  public boolean contains(E element) {
    for (Iterator<E> iterator = iterator(); iterator.hasNext();) {
      if (iterator.next().equals(element)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean contains(E[] array) {
    for (E element : array) {
      if (!contains(element)) {
        return false;
      }
    }
    return true;
  }
}