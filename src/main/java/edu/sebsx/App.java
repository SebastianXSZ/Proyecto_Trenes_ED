package edu.sebsx;

import edu.sebsx.app.linkedlist.singly.SinglyLinkedList;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello Lists");

        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        list.add(10);
        list.add(2);
        list.add(130);
        list.add(1044444);

        list.remove(1);

    }
}
