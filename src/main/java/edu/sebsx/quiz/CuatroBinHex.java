package edu.sebsx.quiz;

import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;
import edu.sebsx.model.iterator.Iterator;
import java.util.Scanner;

public class CuatroBinHex {
  
  public String binarioAHexadecimal(String binario) {

    SinglyLinkedList<Character> arregloBin = new SinglyLinkedList<>();
    for (int i = 0; i < binario.length(); i++) arregloBin.add(binario.charAt(i));
    while (arregloBin.size() % 4 != 0){
      arregloBin.addFirst('0');
    }
    String resultado = "";
    Iterator<Character> it = arregloBin.iterator();
    int contador = 0;
    int valor = 0;
    while (it.hasNext()) {
      char bit = it.next();
      if (contador == 0 && bit == '1') valor = valor + 8;
      if (contador == 1 && bit == '1') valor = valor + 4;
      if (contador == 2 && bit == '1') valor = valor + 2;
      if (contador == 3 && bit == '1') valor = valor + 1;
      contador++;
      if (contador == 4) {
        String tablaHex = "0123456789ABCDEF";
        resultado = resultado + tablaHex.charAt(valor);
        contador = 0;
        valor = 0;
      }
    }
    return resultado;
  }

  public String hexadecimalABinario(String hex) {
    String resultado = "";
    for (int i = 0; i < hex.length(); i++) {
      char caracterHex = hex.charAt(i);
      resultado = resultado + convertirCaracterHexABinario(caracterHex);
    }
    return resultado;
  }

  private String convertirCaracterHexABinario(char c) {
    int v = 0;
    if (c >= '0' && c <= '9'){
      v = c - '0';
    } else{
      v = c - 'A' + 10;
    }
    String bloque = "";
    if (v >= 8){
      bloque = bloque + "1"; v = v - 8;
    } else{
      bloque = bloque + "0";
    }
    if (v >= 4){
      bloque = bloque + "1"; v = v - 4;
    } else{
      bloque = bloque + "0";
    }
    if (v >= 2){
      bloque = bloque + "1"; v = v - 2;
    } else{
      bloque = bloque + "0";
    }
    if (v >= 1){
      bloque = bloque + "1";
    } else{
      bloque = bloque + "0";
    }
    return bloque;
  }

  public static void main(String[] args) {
    CuatroBinHex conv = new CuatroBinHex();
    Scanner sc = new Scanner(System.in);
    System.out.println("Opciones: 1) binario->hexadecimal  2) hexadecimal->binario  (ingrese 'q' para salir)");
    while (true) {
      System.out.print("Elija opción (1, 2 o q): ");
      String opt = sc.nextLine().trim();
      if (opt.equalsIgnoreCase("q")) {
        break;
      }
      if (opt.equals("1")) {
        System.out.print("Ingrese número binario: ");
        String bin = sc.nextLine().trim();
        System.out.println("Hexadecimal: " + conv.binarioAHexadecimal(bin));
      } else if (opt.equals("2")) {
        System.out.print("Ingrese número hexadecimal: ");
        String hex = sc.nextLine().trim().toUpperCase();
        System.out.println("Binario: " + conv.hexadecimalABinario(hex));
      } else {
        System.out.println("Opción inválida. Use 1, 2 o q.");
      }
    }
    sc.close();
    System.out.println("Saliendo.");
  }
}