package com.skwita;

import java.io.*;
import java.net.*;

public class Server extends Thread {
    private Node node;
    private Socket socket;

    Server(Node node, Socket socket) {
        super(node.toString() + System.currentTimeMillis());
        this.node = node;
        this.socket = socket;
    }

    @Override
    public void start() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {

            objectOutputStream.writeObject(new ToSend(node.getPort()));

            Object recievedObject;
            while ((recievedObject = objectInputStream.readObject()) != null) {

                if (recievedObject instanceof ToSend) {
                    ToSend receivedToSend = (ToSend) recievedObject;

                    if (receivedToSend.getToSendType() == ToSendType.Create) {
                        synchronized (node) {
                            if (node.recieveBlock(receivedToSend.getBlocks().get(0))) {
                                System.out.println(node.getPort() + "received:" + recievedObject);
                            }
                        }
                        break;

                    } else if (receivedToSend.getToSendType() == ToSendType.Request){
                        ToSend toSend = new ToSend(node.getPort(), node.getBlockChain(), ToSendType.Response);
                        objectOutputStream.writeObject(toSend);
                        System.out.println(node.getPort() + "sent:" + toSend);
                        break;
                    }
                }
            }
            socket.close();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
