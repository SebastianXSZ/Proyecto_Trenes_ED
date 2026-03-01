package edu.sebsx.quiz;

import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;
import edu.sebsx.model.list.List;
import edu.sebsx.model.array.Array;
import java.util.Arrays;

public class DosProMeMo {
  
  private List<Integer> datos;
  
  public DosProMeMo() {
    this.datos = new SinglyLinkedList<>();
  }
  
  public void agregarDatos(Array<Integer> entrada) {
    for (int i = 0; i < entrada.size(); i++) {
      datos.add(entrada.get(i));
    }
  }
  
  public double promedio() {
    Object[] arreglo = datos.toArray();
    double sumaTotal = 0;
    for (int i = 0; i < arreglo.length; i++) {
      sumaTotal += (Integer) arreglo[i];
    }
    return sumaTotal / arreglo.length;
  }
  
  public double mediana() {
    Integer[] arreglo = datos.toArray();
    Arrays.sort(arreglo);
    int n = arreglo.length;
    if (n % 2 != 0) {
      return arreglo[n / 2];
    } else {
      return (arreglo[n / 2 - 1] + arreglo[n / 2]) / 2.0;
    }
  }
  
  public List<Integer> frecuencias() {
    Object[] arreglo = datos.toArray();
    List<Integer> resultados = new SinglyLinkedList<>();
    List<Integer> elementosVistos = new SinglyLinkedList<>();
    for (int i = 0; i < arreglo.length; i++) {
      Integer numero = (Integer) arreglo[i];
      if (!elementosVistos.contains(numero)) {
        int conteo = contarApariciones(arreglo, numero);
        resultados.add(numero);
        resultados.add(conteo);
        elementosVistos.add(numero);
      }
    }
    return resultados;
  }
  
  public List<Integer> moda() {
    Object[] arreglo = datos.toArray();
    List<Integer> elementosVistos = new SinglyLinkedList<>();
    List<Integer> modas = new SinglyLinkedList<>();
    int frecuenciaMaxima = 0;
    for (int i = 0; i < arreglo.length; i++) {
      Integer numero = (Integer) arreglo[i];
      if (!elementosVistos.contains(numero)) {
        int conteo = contarApariciones(arreglo, numero);
        if (conteo > frecuenciaMaxima) {
          frecuenciaMaxima = conteo;
        }
        elementosVistos.add(numero);
      }
    }
    elementosVistos.clear();
    for (int i = 0; i < arreglo.length; i++) {
      Integer numero = (Integer) arreglo[i];
      if (!elementosVistos.contains(numero)) {
        int conteo = contarApariciones(arreglo, numero);
        if (conteo == frecuenciaMaxima) {
          modas.add(numero);
        }
        elementosVistos.add(numero);
      }
    }
    return modas;
  }
  
  private int contarApariciones(Object[] arreglo, Integer valorBuscado) {
    int contador = 0;
    for (int i = 0; i < arreglo.length; i++) {
      if (arreglo[i].equals(valorBuscado)) {
        contador++;
      }
    }
    return contador;
  }
}