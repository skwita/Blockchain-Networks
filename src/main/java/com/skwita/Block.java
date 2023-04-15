package com.skwita;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Block implements Serializable {
    private static int indexCounter = 0;
    private int index;
    private String prevHash;
    private String hash;
    private String data;
    private int nonce; 
    private Timestamp timestamp;
    
    public Block() {
        this.index = indexCounter++;
        this.data = RandomStringUtils.randomAlphanumeric(256);
        this.nonce = RandomUtils.nextInt();
    }

    public void calculateHash() {
        String temp;
        String resultHash;
        while (true) {
            temp = prevHash + data + nonce;
            resultHash = DigestUtils.sha256Hex(temp);
            if (resultHash.endsWith("0000")) {
                this.hash = resultHash;
                timestamp = new Timestamp(System.currentTimeMillis());
                return;
            } else {
                nonce += 1;
            }
        }
    }

    @Override
    public String toString() {
        return "ind:" + index + "\n" + 
                "hash:" + hash + "\n" + 
                "prevHash:" + prevHash + "\n" + 
                "nonce:" + nonce + "\n" + 
                "data:" + data + "\n" +
                "timeStamp:" + timestamp + "\n";
    }
}