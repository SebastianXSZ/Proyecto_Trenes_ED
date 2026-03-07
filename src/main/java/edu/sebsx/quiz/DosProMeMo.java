package edu.sebsx.quiz;

import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;
import edu.sebsx.model.list.List;
import edu.sebsx.model.iterator.Iterator;

public class DosProMeMo {
  
  private List<Integer> datos;
  
  public DosProMeMo() {
    this.datos = new SinglyLinkedList<>();
  }
  
  public void agregarDatos(List<Integer> entrada) {
    Iterator<Integer> it = entrada.iterator();
    while (it.hasNext()) {
      datos.add(it.next());
    }
  }
  
  public double promedio() {
    double sumaTotal = 0;
    int contador = 0;
    Iterator<Integer> it = datos.iterator();
    while (it.hasNext()) {
      sumaTotal += it.next();
      contador++;
    }
    return contador > 0 ? sumaTotal / contador : 0;
  }
  
  public double mediana() {
    List<Integer> copia = copiarYOrdenar();
    int n = copia.size();
    if (n % 2 != 0) {
      return obtenerElementoEnIndice(copia, n / 2);
    } else {
      return (obtenerElementoEnIndice(copia, n / 2 - 1) + obtenerElementoEnIndice(copia, n / 2)) / 2.0;
    }
  }
  
  public List<Integer> frecuencias() {
    List<Integer> resultados = new SinglyLinkedList<>();
    List<Integer> elementosVistos = new SinglyLinkedList<>();
    
    Iterator<Integer> it = datos.iterator();
    while (it.hasNext()) {
      Integer numero = it.next();
      if (!elementosVistos.contains(numero)) {
        int conteo = contarApariciones(numero);
        resultados.add(numero);
        resultados.add(conteo);
        elementosVistos.add(numero);
      }
    }
    return resultados;
  }
  
  public List<Integer> moda() {
    List<Integer> elementosVistos = new SinglyLinkedList<>();
    List<Integer> modas = new SinglyLinkedList<>();
    int frecuenciaMaxima = 0;
    Iterator<Integer> it = datos.iterator();
    while (it.hasNext()) {
      Integer numero = it.next();
      if (!elementosVistos.contains(numero)) {
        int conteo = contarApariciones(numero);
        if (conteo > frecuenciaMaxima) {
          frecuenciaMaxima = conteo;
        }
        elementosVistos.add(numero);
      }
    }
    elementosVistos.clear();
    it = datos.iterator();
    while (it.hasNext()) {
      Integer numero = it.next();
      if (!elementosVistos.contains(numero)) {
        int conteo = contarApariciones(numero);
        if (conteo == frecuenciaMaxima) {
          modas.add(numero);
        }
        elementosVistos.add(numero);
      }
    }
    return modas;
  }
  
  private int contarApariciones(Integer valorBuscado) {
    int contador = 0;
    Iterator<Integer> it = datos.iterator();
    while (it.hasNext()) {
      if (it.next().equals(valorBuscado)) {
        contador++;
      }
    }
    return contador;
  }

  private List<Integer> copiarYOrdenar() {
    List<Integer> copia = new SinglyLinkedList<>();
    Iterator<Integer> it = datos.iterator();
    while (it.hasNext()) {
      copia.add(it.next());
    }
    copia.sort(n -> n);
    
    return copia;
  }
  
  private int obtenerElementoEnIndice(List<Integer> lista, int indice) {
    int contador = 0;
    Iterator<Integer> it = lista.iterator();
    while (it.hasNext()) {
      int valor = it.next();
      if (contador == indice) {
        return valor;
      }
      contador++;
    }
    return -1;
  }
}