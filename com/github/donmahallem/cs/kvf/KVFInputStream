package com.github.donmahallem.cs.kvf;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

/**
 * InputStream converting KVF Files to "dirty" json
 */
public class KVFInputStream extends InputStream {

    private final int TAB = 9, RET = '\r', LINE = '\n', BRACES_OPEN = '{', BRACES_CLOSE = '}', QUOTES = '"';
    private final boolean mWithHead;
    private final InputStream mInputStream;
    private STATUS mStatus = STATUS.BEFORE_KEY;
    //Buffer for future read() outputs
    private Queue<Integer> mBuffer = new LinkedList<Integer>();


    /**
     * Expects an InputStream of the items_game.txt file
     * @param inputStream
     */
    public KVFInputStream(InputStream inputStream) {
        this(inputStream,false);
    }


    /**
     * Expects an InputStream of the items_game.txt file
     * @param inputStream
     * @param withHead parse the file with the topmost "items_game" key
     */
    public KVFInputStream(InputStream inputStream,boolean withHead) {
        this.mInputStream = inputStream;
        this.mWithHead=withHead;
        if(this.mWithHead){
            this.mBuffer.add(BRACES_OPEN);
        }
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.mInputStream.close();
    }

    private boolean mStreamEnded=false;
    private boolean mHeadSkipped=false;

    @Override
    public int read() throws IOException {
        //Skips items_game key
        if(!this.mHeadSkipped&&!this.mWithHead){
            for(int i=0;i<12;i++){
                this.mInputStream.read();
            }
            this.mHeadSkipped=true;
        }
        if (this.mBuffer.size() > 0) {
            return this.mBuffer.remove();
        }
        if(this.mStreamEnded){
            return -1;
        }
        while (true) {
            int chr = this.mInputStream.read();
            if (chr == -1) {
                this.mStreamEnded=true;
                if(this.mWithHead) {
                    return BRACES_CLOSE;
                }else
                    return -1;
            }
            if (chr == TAB || chr == RET || chr == LINE) {
                continue;
            }
            if (chr == QUOTES) {
                if (this.mStatus == STATUS.BEFORE_KEY) {
                    this.mStatus = STATUS.IN_KEY;
                } else if (this.mStatus == STATUS.IN_KEY) {
                    this.mStatus = STATUS.AFTER_KEY;
                    this.mBuffer.add((int) ':');
                } else if (this.mStatus == STATUS.AFTER_KEY) {
                    this.mStatus = STATUS.IN_VALUE;
                } else if (this.mStatus == STATUS.IN_VALUE) {
                    this.mStatus = STATUS.AFTER_VALUE;
                } else if (this.mStatus == STATUS.AFTER_VALUE) {
                    this.mStatus = STATUS.IN_KEY;
                    this.mBuffer.add(QUOTES);
                    return ',';
                }
            }
            if (chr == BRACES_OPEN) {
                this.mStatus = STATUS.BEFORE_KEY;
            } else if (chr == BRACES_CLOSE) {
                this.mStatus = STATUS.AFTER_VALUE;
            }
            return chr;
        }
    }

    private enum STATUS {
        IN_KEY,
        IN_VALUE,
        AFTER_VALUE,
        AFTER_KEY,
        BEFORE_KEY;
    }
}
