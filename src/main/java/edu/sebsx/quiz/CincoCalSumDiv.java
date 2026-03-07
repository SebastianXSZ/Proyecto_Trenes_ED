package edu.sebsx.quiz;

import edu.sebsx.app.linkedlist.doubly.DoublyLinkedList;
import edu.sebsx.model.iterator.Iterator;
import java.util.Scanner;

public class CincoCalSumDiv {
  
  public String sumar(String num1, String num2) {

    DoublyLinkedList<Integer> a1 = new DoublyLinkedList<>();
    for(int k=0; k<num1.length(); k++) a1.add(num1.charAt(k)-'0');
    DoublyLinkedList<Integer> a2 = new DoublyLinkedList<>();
    for(int k=0; k<num2.length(); k++) a2.add(num2.charAt(k)-'0');
    
    Integer[] arr1 = new Integer[a1.size()];
    Iterator<Integer> it1 = a1.iterator();
    int idx1 = 0;
    while (it1.hasNext()) arr1[idx1++] = it1.next();
    
    Integer[] arr2 = new Integer[a2.size()];
    Iterator<Integer> it2 = a2.iterator();
    int idx2 = 0;
    while (it2.hasNext()) arr2[idx2++] = it2.next();
    
    String resultado = "";
    int i = arr1.length - 1;
    int j = arr2.length - 1;
    int llevo = 0;
    while (i >= 0 || j >= 0 || llevo > 0) {
      int d1 = 0;
      if (i >= 0) {
        d1 = arr1[i];
        i--;
      }
      int d2 = 0;
      if (j >= 0) {
        d2 = arr2[j];
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
    DoublyLinkedList<Integer> arrDiv = new DoublyLinkedList<>();
    for(int k=0; k<dividendo.length(); k++) arrDiv.add(dividendo.charAt(k)-'0');
    
    String cociente = "";
    String residuoActual = "";
    Iterator<Integer> it = arrDiv.iterator();
    while (it.hasNext()) {
      int digito = it.next();
      residuoActual = residuoActual + digito;
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

  public static void main(String[] args) {
    CincoCalSumDiv calc = new CincoCalSumDiv();
    Scanner sc = new Scanner(System.in);
    System.out.println("Opciones: 1) sumar  2) dividir  (ingrese 'q' para salir)");
    while (true) {
      System.out.print("Elija opción (1, 2 o q): ");
      String opt = sc.nextLine().trim();
      if (opt.equalsIgnoreCase("q")) break;
      if (opt.equals("1")) {
        System.out.print("Ingrese primer número: ");
        String a = sc.nextLine().trim();
        System.out.print("Ingrese segundo número: ");
        String b = sc.nextLine().trim();
        System.out.println("Resultado: " + calc.sumar(a, b));
      } else if (opt.equals("2")) {
        System.out.print("Ingrese dividendo: ");
        String d = sc.nextLine().trim();
        System.out.print("Ingrese divisor: ");
        String v = sc.nextLine().trim();
        System.out.println("Cociente: " + calc.dividir(d, v));
      } else {
        System.out.println("Opción inválida. Use 1, 2 o q.");
      }
    }
    sc.close();
    System.out.println("Saliendo.");
  }
}