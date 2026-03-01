package edu.sebsx.quiz;

import edu.sebsx.model.array.Array;

public class TresOctDec {
  
  public String octalADecimal(String octal) {
    Array<Integer> digitos = new Array<>(octal.length());
    for (int i = 0; i < octal.length(); i++) {
      char c = octal.charAt(i);
      digitos.add(c - '0');
    }
    String resultado = "0";
    for (int i = 0; i < digitos.size(); i++) {
      String temporal = "0";
      for (int k = 0; k < 8; k++) {
        temporal = sumarCadenas(temporal, resultado);
      }
      int digitoActual = digitos.get(i);
      resultado = sumarCadenas(temporal, String.valueOf(digitoActual));
    }
    return resultado;
  }
  
  public String decimalAOctal(String decimal) {
    if (decimal.equals("0")) {
      return "0";
    }
    String resultado = "";
    String numeroActual = decimal;
    while (!numeroActual.equals("0")) {
      String nuevoCociente = "";
      int residuo = 0;
      Array<Integer> arregloNum = new Array<>(numeroActual.length());
      for (int i = 0; i < numeroActual.length(); i++) {
        arregloNum.add(numeroActual.charAt(i) - '0');
      }
      for (int i = 0; i < arregloNum.size(); i++) {
        int valorTemporal = (residuo * 10) + arregloNum.get(i);
        int division = valorTemporal / 8;
        residuo = valorTemporal % 8;
        if (!nuevoCociente.isEmpty() || division > 0) {
          nuevoCociente = nuevoCociente + division;
        }
      }
      if (nuevoCociente.isEmpty()) {
        nuevoCociente = "0";
      }
      numeroActual = nuevoCociente;
      resultado = residuo + resultado;
    }
    return resultado;
  }
  
  private String sumarCadenas(String a, String b) {
    String res = "";
    int i = a.length() - 1;
    int j = b.length() - 1;
    int llevo = 0;
    while (i >= 0 || j >= 0 || llevo > 0) {
      int d1 = 0;
      if (i >= 0) {
        d1 = a.charAt(i) - '0';
        i--;
      }
      int d2 = 0;
      if (j >= 0) {
        d2 = b.charAt(j) - '0';
        j--;
      }
      int suma = d1 + d2 + llevo;
      llevo = suma / 10;
      res = (suma % 10) + res;
    }
    return res;
  }
}