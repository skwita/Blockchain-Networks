package com.skwita;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Block {
    private static int indexCounter = 0;
    private int index;
    private String prevHash;
    private String hash;
    private String data;
    private int nonce; 
    
    public Block() {
        this.index = indexCounter++;
        this.data = RandomStringUtils.randomAlphanumeric(256);
    }

    public boolean calculateHash() {
        String temp = prevHash + hash + data + nonce;
        String resultHash = DigestUtils.sha256Hex(temp);
        if (resultHash.endsWith("0000")) {
            this.hash = resultHash;
            return true;
        } else {
            nonce += 1;
            return false;
        }
    }

    @Override
    public String toString() {
        return "ind:" + index + "\n" + 
                "hash:" + hash + "\n" + 
                "prevHash:" + prevHash + "\n" + 
                "nonce:" + nonce + "\n" + 
                "data:" + data + "\n";
    }
}