package edu.sebsx.quiz;

import edu.sebsx.model.array.Array;

public class CincoCalSumDiv {
  
  public String sumar(String num1, String num2) {

    Array<Integer> a1 = new Array<>(num1.length());
    for(int k=0; k<num1.length(); k++) a1.add(num1.charAt(k)-'0');
    Array<Integer> a2 = new Array<>(num2.length());
    for(int k=0; k<num2.length(); k++) a2.add(num2.charAt(k)-'0');
    String resultado = "";
    int i = a1.size() - 1;
    int j = a2.size() - 1;
    int llevo = 0;
    while (i >= 0 || j >= 0 || llevo > 0) {
      int d1 = 0;
      if (i >= 0) {
        d1 = a1.get(i);
        i--;
      }
      int d2 = 0;
      if (j >= 0) {
        d2 = a2.get(j);
        j--;
      }
      int suma = d1 + d2 + llevo;
      llevo = suma / 10;
      resultado = (suma % 10) + resultado;
    }
    return resultado;
  }
  
  public String dividir(String dividendo, String divisor) {
    if (divisor.equals("0")) return "Error: No se puede dividir por 0";
    Array<Integer> arrDiv = new Array<>(dividendo.length());
    for(int k=0; k<dividendo.length(); k++) arrDiv.add(dividendo.charAt(k)-'0');
    String cociente = "";
    String residuoActual = "";
    for (int i = 0; i < arrDiv.size(); i++) {
      residuoActual = residuoActual + arrDiv.get(i);
      residuoActual = quitarCeros(residuoActual);
      int contador = 0;
      while (esMayorOIgual(residuoActual, divisor)) {
        residuoActual = restarCadenas(residuoActual, divisor);
        contador++;
      }
      cociente = cociente + contador;
    }
    return quitarCeros(cociente);
  }
  
  private boolean esMayorOIgual(String a, String b) {
    if (a.length() > b.length()) return true;
    if (a.length() < b.length()) return false;
    return a.compareTo(b) >= 0;
  }
  
  private String restarCadenas(String n1, String n2) {
    String res = "";
    int i = n1.length() - 1;
    int j = n2.length() - 1;
    int prestamo = 0;
    while (i >= 0) {
      int d1 = n1.charAt(i) - '0' - prestamo;
      int d2 = 0;
      if (j >= 0) {
        d2 = n2.charAt(j) - '0';
        j--;
      }
      if (d1 < d2) {
        d1 = d1 + 10;
        prestamo = 1;
      } else {
        prestamo = 0;
      }
      res = (d1 - d2) + res;
      i--;
    }
    return quitarCeros(res);
  }

  private String quitarCeros(String s) {
    int i = 0;
    while (i < s.length() - 1 && s.charAt(i) == '0') i++;
    return s.substring(i);
  }
}