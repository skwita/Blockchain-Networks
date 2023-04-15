package com.skwita;

import java.util.*;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BlockChain extends ArrayList<Block> {
   
    // Create a blockchain out of list of blocks
    public BlockChain(List<Block> blocks) {
        this.addAll(blocks);
    }

    // Adding a block to this blockchain if it is valid
    @Override
    public boolean add(Block block) {
        if (this.size() == 0 || validate(block)) {
            super.add(block);
            return true;
        }
        return false;
    }
    
    // Generate first block
    public Block createGenesis() {
        Block firstBlock = new Block();
        firstBlock.setNonce(0);
        firstBlock.setPrevHash("0");
        firstBlock.calculateHash();
        super.add(firstBlock);
        return firstBlock;
    }
    
    // Check if the new block is valid for addition to this blockchain
    private boolean validate(Block block) {
        Block lastBlock = this.get(this.size() - 1);
        
        boolean hashEndsWithZeros = block.getHash().endsWith("0000");
        boolean currentPrevHashEqualsLastBlockHash = lastBlock.getHash().equals(block.getPrevHash());
        boolean blockHasValidIndex = lastBlock.getIndex() == block.getIndex() - 1;

        return hashEndsWithZeros && 
               currentPrevHashEqualsLastBlockHash && 
               blockHasValidIndex;
    }

    // Check if the blockchain is valid
    public boolean isValid() {
        Block currentBlock;
        Block previousBlock;
        
        // Iterate over all blocks in the chain (except the genesis block)
        for (int i = 1; i < this.size(); i++) {
            currentBlock = this.get(i);
            previousBlock = this.get(i - 1);
            
            // Check if the hash of the current block is valid
            if (!currentBlock.getHash().endsWith("0000")) {
                return false;
            }
            
            // Check if the previous hash of the current block matches the hash of the previous block
            if (!previousBlock.getHash().equals(currentBlock.getPrevHash())) {
                return false;
            }

            // Check if the timestamp of the current block is after the one of the previous block
            if (!currentBlock.getTimestamp().after(previousBlock.getTimestamp())) {
                return false;
            }
        }
        
        // The blockchain is valid if all blocks are valid and linked correctly
        return true;
    }
}
