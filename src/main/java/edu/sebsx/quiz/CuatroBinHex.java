package edu.sebsx.quiz;

import edu.sebsx.model.array.Array;

public class CuatroBinHex {
  
  public String binarioAHexadecimal(String binario) {

    Array<Character> arregloBin = new Array<>(binario.length() + 4);
    for (int i = 0; i < binario.length(); i++) {
      arregloBin.add(binario.charAt(i));
    }
    while (arregloBin.size() % 4 != 0){
      arregloBin.add(0, new Character[]{'0'}); 
    }
    String resultado = "";
    for (int i = 0; i < arregloBin.size(); i = i + 4){
      int valor = 0;
      if (arregloBin.get(i) == '1') valor = valor + 8;
      if (arregloBin.get(i + 1) == '1') valor = valor + 4;
      if (arregloBin.get(i + 2) == '1') valor = valor + 2;
      if (arregloBin.get(i + 3) == '1') valor = valor + 1;
      String tablaHex = "0123456789ABCDEF";
      resultado = resultado + tablaHex.charAt(valor);
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
}