/*
 * Click nbfs://nbhost/SystemFileSystem/tmplates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/tmplates/Classes/Class.java to edit this tmplate
 */
package project;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Tuna-Miray
 */
public class Problem1 {

    public static void main(String[] args) throws IOException {
        CircularLinkedList list = new CircularLinkedList();
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        System.out.print("Enter the number of players: ");
        int N = scanner.nextInt();

        File f = new File("names.txt");
        Scanner input = new Scanner(f);
        int counter = 0;
        while (input.hasNextLine()) {
            input.nextLine();
            counter++;
        }

        String[] names = new String[counter];
        int i = 0;
        input = new Scanner(f);
        while (input.hasNextLine()) {
            names[i] = input.nextLine();
            i++;
        }

        if (N > names.length) {
            System.out.println("Out of list.");
            return;
        }

        String[] selectedPlayers = new String[N];

        boolean[] kontrol = new boolean[names.length];
        int count = 0;
        while (count < N) {
            int r = random.nextInt(names.length);
            if (kontrol[r] != true) {
                selectedPlayers[count] = names[r];
                kontrol[r] = true;
                count++;
            }
        }

        for (int j = 0; j < selectedPlayers.length; j++) {
            list.addPlayer(selectedPlayers[j]);
        }

        list.game();
    }
}

class NodeChar {

    char ch;
    NodeChar next;

    public NodeChar(char ch) {
        this.ch = ch;
        this.next = null;
    }
}

class LinkedList {

    NodeChar first;

    public void addChar(char ch) {
        NodeChar newNode = new NodeChar(ch);
        if (first == null) {
            first = newNode;
        } else {
            NodeChar tmp = first;
            while (tmp.next != null) {
                tmp = tmp.next;
            }
            tmp.next = newNode;
        }
    }

    public void removeFirst() {
        if (first == null) {
            System.out.println("This list is already empty");
            return;
        }
        first = first.next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public String getName() {
        if (first == null) {
            return "";
        }
        NodeChar tmp = first;
        String totalisimchar = "";
        while (tmp != null) {
            totalisimchar += tmp.ch;
            tmp = tmp.next;
        }
        return totalisimchar;
    }
}

class Node {

    String fullName;
    LinkedList name;
    Node next;

    public Node(String name) {
        this.fullName = name;
        this.name = new LinkedList();
        for (int i = 0; i < name.length(); i++) {
            this.name.addChar(name.charAt(i));
        }
        this.next = null;
    }

    public boolean isEliminated() {
        return name.isEmpty();
    }
}

class CircularLinkedList {

    Node first;
    int size = 0;

    public void addPlayer(String name) {
        Node newNode = new Node(name);
        if (first == null) {
            first = newNode;
            first.next = first;
        } else {
            Node tmp = first;
            while (tmp.next != first) {
                tmp = tmp.next;
            }
            tmp.next = newNode;
            newNode.next = first;
        }
        size++;
    }

    public void removePlayer(Node prev, Node remove) {
        if (size == 1) {
            first = null;
        } else if (remove == first) {
            prev.next = first.next;
            first = first.next;
        } else {
            prev.next = remove.next;
        }
        size--;
    }

    public void game() {
        Random random = new Random();
        Node current = first;
        Node dataprev = null;

        while (size > 1) {

            int k = random.nextInt(size) + 1; 
            System.out.println("kth player: " + k);
            for (int i = 1; i < k; i++) {
                dataprev = current;
                current = current.next;
            }

            System.out.println("Selected player: " + current.fullName);
            current.name.removeFirst();
            System.out.println(current.fullName + " lose letter.");

            if (current.isEliminated()) {
                System.out.println(current.fullName + " is eliminated.");
                removePlayer(dataprev, current);
                current = dataprev.next;
            } else {
                dataprev = current;
                current = current.next;
            }

            toString();
        }
        System.out.println("The winner is: " + first.fullName);
    }

    @Override
    public String toString() {
        Node tmp = first;
        System.out.print(tmp.fullName + " (" + tmp.name.getName() + ") -> ");
        tmp = tmp.next;

        while (tmp != null && tmp != first) {
            System.out.print(tmp.fullName + " (" + tmp.name.getName() + ") -> ");
            tmp = tmp.next;
        }
        System.out.println();
        return "";
    }
}
