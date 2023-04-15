import com.skwita.Block;
import com.skwita.BlockChain;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class BlockChainTests {
    @Test
    public void testCalculateHash() {
        Block block = new Block();
        block.calculateHash();
        String expected = DigestUtils.sha256Hex(block.getPrevHash() + block.getData() + block.getNonce());
        Assertions.assertEquals(expected, block.getHash());
    }
    
    @Test
    public void testToString() {
        Block block = new Block();
        String str = block.toString();
        Assertions.assertTrue(str.contains("ind:"));
        Assertions.assertTrue(str.contains("hash:"));
        Assertions.assertTrue(str.contains("prevHash:"));
        Assertions.assertTrue(str.contains("nonce:"));
        Assertions.assertTrue(str.contains("data:"));
        Assertions.assertTrue(str.contains("timeStamp:"));
    }
    
    @Test
    public void testIndexCounter() {
        Block block1 = new Block();
        Block block2 = new Block();
        Assertions.assertEquals(block1.getIndex() + 1, block2.getIndex());
    }
    
    @Test
    public void testNonce() {
        Block block = new Block();
        block.calculateHash();
        Assertions.assertTrue(block.getHash().endsWith("0000"));
    }
    
    @Test
    public void testData() {
        Block block = new Block();
        String data = block.getData();
        Assertions.assertEquals(256, data.length());
        Assertions.assertTrue(data.matches("[a-zA-Z0-9]+"));
    }
    
    @Test
    public void testTimestamp() {
        Block block = new Block();
        block.calculateHash();
        Assertions.assertNotNull(block.getTimestamp());
    }

    @Test
    public void testAddBlock() {
        BlockChain chain = new BlockChain(new ArrayList<>());
        Block genesisBlock = chain.createGenesis();

        Block block1 = new Block();
        block1.setNonce(1);
        block1.setPrevHash(genesisBlock.getHash());
        block1.calculateHash();
        Assertions.assertTrue(chain.add(block1));

        Block block2 = new Block();
        block2.setNonce(2);
        block2.setPrevHash(block1.getHash());
        block2.calculateHash();
        Assertions.assertTrue(chain.add(block2));
    }

    @Test
    public void testInvalidBlock() {
        BlockChain chain = new BlockChain(new ArrayList<>());
        Block genesisBlock = chain.createGenesis();

        Block block1 = new Block();
        block1.setNonce(1);
        block1.setPrevHash(genesisBlock.getHash());
        block1.calculateHash();
        Assertions.assertTrue(chain.add(block1));

        Block block2 = new Block();
        block2.setNonce(2);
        block2.setPrevHash("invalid hash"); // set invalid previous hash
        block2.calculateHash();
        Assertions.assertFalse(chain.add(block2));
    }
}
