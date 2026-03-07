package edu.sebsx.quiz;

import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;
import edu.sebsx.model.list.List;

public class UnoConjunto {
  
  private List<Character> resultado;
  
  public UnoConjunto() {
    this.resultado = new SinglyLinkedList<>();
  }
  
  public List<Character> interseccion(List<Character> conjuntoA, List<Character> conjuntoB) {
    resultado.clear();
    Object[] arregloA = conjuntoA.toArray();
    for (int i = 0; i < arregloA.length; i++) {
      Character actual = (Character) arregloA[i];
      if (conjuntoB.contains(actual) && !resultado.contains(actual)) {
        resultado.add(actual);
      }
    }
    return resultado;
  }
  
  public List<Character> diferencia(List<Character> conjuntoA, List<Character> conjuntoB) {
    resultado.clear();
    Object[] arregloA = conjuntoA.toArray();
    for (int i = 0; i < arregloA.length; i++) {
      Character actual = (Character) arregloA[i];
      if (!conjuntoB.contains(actual) && !resultado.contains(actual)) {
        resultado.add(actual);
      }
    }
    return resultado;
  }
}