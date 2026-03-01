package edu.sebsx.model.array;

import java.util.function.Function;
import java.util.function.Predicate;
import edu.sebsx.model.collection.Collection;
import edu.sebsx.model.iterator.Iterator;

public class Array<E> extends AbstractArray<E> {

    private transient E[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public Array(int capacidad) {
        this.elements = (E[]) new Object[capacidad];
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E get(int indice) {
        if (indice < 0 || indice >= size) {
            return null;
        }
        return elements[indice];
    }

    @Override
    public boolean add(E elemento) {
        if (size >= elements.length) {
            return false;
        }
        elements[size++] = elemento;
        return true;
    }

    @Override
    public boolean set(int indice, E elemento) {
        if (indice < 0 || indice >= size) {
            return false;
        }
        elements[indice] = elemento;
        return true;
    }

    @Override
    public boolean remove(int indice) {
        if (indice < 0 || indice >= size) {
            return false;
        }
        for (int i = indice; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null;
        return true;
    }

    @Override
    public int indexOf(E elementoBuscado) {
        for (int i = 0; i < size; i++) {
            if (elements[i] != null && elements[i].equals(elementoBuscado)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(E elementoBuscado) {
        for (int i = size - 1; i >= 0; i--) {
            if (elements[i] != null && elements[i].equals(elementoBuscado)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
        return true;
    }

    @Override
    public boolean reverse() {
        for (int i = 0; i < size / 2; i++) {
            E temp = elements[i];
            elements[i] = elements[size - 1 - i];
            elements[size - 1 - i] = temp;
        }
        return true;
    }

    @Override
    public void defragment() {
        int index = 0;
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != null) {
                elements[index++] = elements[i];
            }
        }
        for (int i = index; i < elements.length; i++) {
            elements[i] = null;
        }
        size = index;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean dimension(int nuevaCapacidad) {
        if (nuevaCapacidad < size) {
            return false;
        }
        E[] backup = (E[]) new Object[nuevaCapacidad];
        for (int i = 0; i < size; i++) {
            backup[i] = elements[i];
        }
        elements = backup;
        return true;
    }

    @Override
    public boolean remove(Predicate<E> filtro) {
        try {
            for (int i = size - 1; i >= 0; i--) {
                if (filtro.test(elements[i])) {
                    remove(i);
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean remove(int desde, int hasta) {
        if (desde < 0 || hasta >= size || desde > hasta) {
            return false;
        }
        int count = hasta - desde + 1;
        for (int i = 0; i < count; i++) {
            remove(desde);
        }
        return true;
    }

    @Override
    public boolean add(int indice, E[] arregloExterno) {
        for (E e : arregloExterno) {
            add(e);
        }
        return true;
    }

    @Override
    public boolean add(int indice, Collection<E> coleccionExterna) {
        Iterator<E> it = coleccionExterna.iterator();
        while (it.hasNext()) {
            add(it.next());
        }
        return true;
    }

    @Override
    public boolean contains(Collection<E> collection) {
        Iterator<E> it = collection.iterator();
        while (it.hasNext()) {
            if (indexOf(it.next()) == -1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void forEach(Function<E, Void> action) {
        for (int i = 0; i < size; i++) {
            action.apply(elements[i]);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < size;
            }

            @Override
            public E next() {
                return elements[current++];
            }
        };
    }
}