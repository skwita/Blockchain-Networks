package com.skwita;

import java.util.List;

import lombok.Getter;

@Getter
public class ToSend {
    private List<Block> blocks;
    private int senderPort;
    private int receiverPort;
    private ToSendType toSendType;

    public ToSend(int senderPort, int receiverPort, List<Block> blocks, ToSendType toSendType) {
        this.blocks = blocks;
        this.receiverPort = receiverPort;
        this.senderPort = senderPort;
        this.toSendType = toSendType;
    }

    public ToSend(int senderPort, List<Block> blocks, ToSendType toSendType) {
        this.blocks = blocks;
        this.senderPort = senderPort;
        this.toSendType = toSendType;
    }

    public ToSend(int senderPort) {
        this.senderPort = senderPort;
    }

    @Override
    public String toString() {
        return "blocks:" + blocks + "\n"
        + "senderPort:" + senderPort + "\n"
        + "recieverPort:" + receiverPort + "\n"
        + "type:" + toSendType + "\n";
    }
}


