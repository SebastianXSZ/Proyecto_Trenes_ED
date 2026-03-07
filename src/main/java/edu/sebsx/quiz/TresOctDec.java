package edu.sebsx.quiz;

import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;
import edu.sebsx.model.iterator.Iterator;
import java.util.Scanner;

public class TresOctDec {
  
  public String octalADecimal(String octal) {
    SinglyLinkedList<Integer> digitos = new SinglyLinkedList<>();
    for (int i = 0; i < octal.length(); i++) {
      char c = octal.charAt(i);
      digitos.add(c - '0');
    }
    String resultado = "0";
    Iterator<Integer> it = digitos.iterator();
    while (it.hasNext()) {
      String temporal = "0";
      for (int k = 0; k < 8; k++) {
        temporal = sumarCadenas(temporal, resultado);
      }
      int digitoActual = it.next();
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
      SinglyLinkedList<Integer> arregloNum = new SinglyLinkedList<>();
      for (int i = 0; i < numeroActual.length(); i++) {
        arregloNum.add(numeroActual.charAt(i) - '0');
      }
      Iterator<Integer> it = arregloNum.iterator();
      while (it.hasNext()) {
        int valorTemporal = (residuo * 10) + it.next();
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

  public static void main(String[] args) {
    TresOctDec conv = new TresOctDec();
    Scanner sc = new Scanner(System.in);
    System.out.println("Opciones: 1) octal->decimal  2) decimal->octal  (ingrese 'q' para salir)");
    while (true) {
      System.out.print("Elija opción (1, 2 o q): ");
      String opt = sc.nextLine().trim();
      if (opt.equalsIgnoreCase("q")) {
        break;
      }
      if (opt.equals("1")) {
        System.out.print("Ingrese número octal: ");
        String oct = sc.nextLine().trim();
        System.out.println("Decimal: " + conv.octalADecimal(oct));
      } else if (opt.equals("2")) {
        System.out.print("Ingrese número decimal: ");
        String dec = sc.nextLine().trim();
        System.out.println("Octal: " + conv.decimalAOctal(dec));
      } else {
        System.out.println("Opción inválida. Use 1, 2 o q.");
      }
    }
    sc.close();
    System.out.println("Saliendo.");
  }
}