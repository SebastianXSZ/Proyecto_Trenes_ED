package edu.sebsx.model.array;

import java.util.function.Predicate;

import edu.sebsx.model.collection.Collection;

/**
 * Represents an array data structure that allows insertion, retrieval, removal,
 * and manipulation of elements.
 * 
 * @param <E> the type of elements stored in the array.
 * @author Lenin Javier Serrano Gil
 * @version 1.0.20231115
 */
public interface IArray<E> {
  /**
   * Inserts the specified element at the clear position in this collection.
   * 
   * @param element the element to be inserted.
   * @return 'true' if the element was added successfully, otherwise 'false'.
   */
  boolean add(E element);

  /**
   * Inserts all of the elements in the specified array into this collection,
   * starting at the specified position.
   * 
   * @param index the index at which the specified element is to be inserted.
   * @param array the array containing elements to be added to this collection.
   * @return 'true' if the collection was added successfully, otherwise 'false'.
   */
  boolean add(int index, E[] array);

  /**
   * Inserts all of the elements in the specified collection into this collection,
   * starting at the specified position.
   * 
   * @param index      the index at which the specified element is to be inserted.
   * @param collection the collection containing elements to be added to this
   *                   collection.
   * @return 'true' if the collection was added successfully, otherwise 'false'.
   */
  boolean add(int index, Collection<E> collection);

  /**
   * Returns the element at the specified position in this collection.
   * 
   * @param index the index of the element to return.
   * @return the element at the specified position in this collection.
   */
  E get(int index);

  /**
   * Returns the index of the first occurrence of the specified element in this
   * collection.
   * 
   * @param element the element to search for.
   * @return the index of the first occurrence of the specified element in this
   *         collection, or -1 if this list does not contain the element.
   */
  int indexOf(E element);

  /**
   * Returns the index of the last occurrence of the specified element in this
   * collection.
   * 
   * @param element the element to search for.
   * @return the index of the last occurrence of the specified element in this
   *         collection, or -1 if this list does not contain the element.
   */
  int lastIndexOf(E element);

  /**
   * Removes the element at the specified position in this collection.
   * 
   * @param index the index of the element to be removed.
   * @return 'true' if the element was removed successfully, otherwise 'false'.
   */
  boolean remove(int index);

  /**
   * Removes the first occurrence of the specified element from this collection
   * that depends on a predicate
   * 
   * @param filter the filter to apply to each element to determine if it should
   *               be removed.
   * @return 'true' if the element was removed successfully, otherwise 'false'.
   */
  boolean remove(Predicate<E> filter);

  /**
   * Removes from this collection all of the elements whose index is between
   * "from" and "to".
   * 
   * @param from the initial index of the range to be removed, inclusive.
   * @param to   the final index of the range to be removed, exclusive.
   * @return 'true' if the element was removed successfully between the specified
   *         "from", inclusive, and "to", exclusive, otherwise 'false'.
   */
  boolean remove(int from, int to);

  /**
   * Replaces the element at the specified position in this collection with the
   * specified element.
   * 
   * @param index   the index of the element to replace.
   * @param element the element to be stored at the specified position.
   * @return 'true' if the element was replaced successfully, otherwise 'false'.
   */
  boolean set(int index, E element);
}
