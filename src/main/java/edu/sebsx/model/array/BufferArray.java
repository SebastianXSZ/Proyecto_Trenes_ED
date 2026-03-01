package edu.sebsx.model.array;

/**
 * Represents array methods to defragment and resize the array.
 * 
 * @author Lenin Javier Serrano Gil
 * @version 1.0.20240219
 */
public interface BufferArray {
  /**
   * Moves all the elements to the left.
   */
  void defragment();

  /**
   * Resizes the array to the specified dimension. If the specified dimension is
   * less than the current dimension, the array is truncated.
   * 
   * @param newDimension the new dimension of the array.
   * @return 'true' if the array was re dimensioned successfully, otherwise
   *         'false'.
   */
  boolean dimension(int newDimension);
}
