package edu.sebsx.quiz;

import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;
import edu.sebsx.model.list.List;
import edu.sebsx.model.array.Array;

public class SeisCalPol {
  
  private List<Integer> coeficientes;
  
  public SeisCalPol() {
    this.coeficientes = new SinglyLinkedList<>();
  }
  public void cargarPolinomio(Array<Integer> entrada) {
    for (int i = 0; i < entrada.size(); i++) {
      coeficientes.add(entrada.get(i));
    }
  }
  
  public double evaluar(double x) {
    Object[] arrCoef = coeficientes.toArray();
    double resultado = 0;
    for (int i = arrCoef.length - 1; i >= 0; i--) {
      resultado = (resultado * x) + (Integer) arrCoef[i];
    }
    return resultado;
  }
  
  public String integrar() {
    Object[] arrCoef = coeficientes.toArray();
    String resultado = "<";
    for (int i = 0; i < arrCoef.length; i++) {
      int coef = (Integer) arrCoef[i];
      resultado += coef + "/" + (i + 1);
      if (i < arrCoef.length - 1) {
        resultado += ", ";
      }
    }
    resultado += ", C>";
    return resultado;
  }
  
  public String sumar(SeisCalPol otroPolinomio) {
    Object[] coef1 = this.coeficientes.toArray();
    Object[] coef2 = otroPolinomio.coeficientes.toArray();
    int tamanoMax = Math.max(coef1.length, coef2.length);
    String resultado = "<";
    for (int i = 0; i < tamanoMax; i++) {
      int valor1 = (i < coef1.length) ? (Integer) coef1[i] : 0;
      int valor2 = (i < coef2.length) ? (Integer) coef2[i] : 0;
      resultado += (valor1 + valor2);
      if (i < tamanoMax - 1) {
        resultado += ", ";
      }
    }
    resultado += ">";
    return resultado;
  }
}