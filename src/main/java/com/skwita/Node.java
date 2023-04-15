package com.skwita;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Node {
    private BlockChain blockChain = new BlockChain();
    private ServerSocket serverSocket;
    private int port;
    private Set<Integer> ports;
    private ObjectOutputStream[] outStreams;
    private ObjectInputStream[] inStreams;
    private int[] clientPorts;
    private boolean shouldGenerateFirst;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private boolean listening = true;

    public Node(int port) {
        try {
            this.port = port;
            this.serverSocket = new ServerSocket(port);
            this.ports  = Set.of(2100, 2200, 2300);
            this.blockChain.createGenesis();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    // Create a new block, calculate its hash and share it
    public void mine() {
        String lastHash = blockChain.get(blockChain.size() - 1).getHash();
        Block block = new Block();
        block.setPrevHash(lastHash);
        block.calculateHash();
        this.blockChain.add(block);
        shareBlock(List.of(block), ToSendType.Create);
    }

    // Share or receive blocks
    public void shareBlock(List<Block> blocks, ToSendType toSendType) {
        for (Integer clientPort : ports) {

            try (Socket socket = new Socket("localhost", clientPort);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {

                Object objectToGet;
                while ((objectToGet = objectInputStream.readObject()) != null) {

                    if (objectToGet instanceof ToSend) {
                        ToSend toSend = (ToSend) objectToGet;

                        if (ToSendType.Ready == toSend.getToSendType()) {
                            objectOutputStream.writeObject(new ToSend(this.port, clientPort, blocks, toSendType));

                        } else if (ToSendType.Response == toSend.getToSendType()) {
                            if (!toSend.getBlocks().isEmpty() && this.blockChain.size() == 1) {
                                this.blockChain = new BlockChain(toSend.getBlocks());
                            }
                            break;
                        }
                    }
                }
            } catch (UnknownHostException | ClassNotFoundException unknownHostException) {
                unknownHostException.printStackTrace();
            } catch (IOException ioException) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }
    }

    public boolean recieveBlock(Block block) {
        return this.blockChain.add(block);
    }

    public void shareBlockChain() {
        for (ObjectOutputStream outputStream : outStreams) {
            try {
                outputStream.writeObject(blockChain);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void recieveBlockChain(BlockChain blockChain) {
        if (blockChain.isValid())
            this.blockChain = blockChain;
        System.out.println(this.blockChain);
    }

    public void run() {
        executorService.execute(() -> {
            try {
                serverSocket = new ServerSocket(port);
                listening = true;
                while (listening) {
                    new Server(Node.this, serverSocket.accept()).start();
                }
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        shareBlock(null, ToSendType.Request);
    }
}
