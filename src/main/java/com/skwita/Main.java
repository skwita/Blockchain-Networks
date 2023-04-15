package com.skwita;

public class Main {

    public static void main(String[] args) {
        Node node = new Node(Integer.parseInt(args[1]));
        node.run();
        while (true) {
            node.mine();
        }
    }
}
