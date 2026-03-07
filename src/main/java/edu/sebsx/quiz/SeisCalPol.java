package edu.sebsx.quiz;

import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;
import edu.sebsx.model.list.List;


public class SeisCalPol {
  
  private List<Integer> coeficientes;
  
  public SeisCalPol() {
    this.coeficientes = new SinglyLinkedList<>();
  }
  public void cargarPolinomio(List<Integer> entrada) {
    Object[] arr = entrada.toArray();
    for (int i = 0; i < arr.length; i++) {
      coeficientes.add((Integer) arr[i]);
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

  public static void main(String[] args) {
    SeisCalPol p = new SeisCalPol();
    if (args.length == 0) {
      System.out.println("Uso: evaluar <x> | integrar | sumar <c0,c1,...>");
      return;
    }
    switch (args[0]) {
      case "evaluar":
        if (args.length < 2) { System.out.println("Falta x"); return; }
        System.out.println(p.evaluar(Double.parseDouble(args[1])));
        break;
      case "integrar":
        System.out.println(p.integrar());
        break;
      case "sumar":
        if (args.length < 2) { System.out.println("Faltan coeficientes"); return; }
        SeisCalPol q = new SeisCalPol();
        for (String s : args[1].split(",")) q.coeficientes.add(Integer.parseInt(s));
        System.out.println(p.sumar(q));
        break;
      default:
        System.out.println("Comando desconocido");
    }
  }
}