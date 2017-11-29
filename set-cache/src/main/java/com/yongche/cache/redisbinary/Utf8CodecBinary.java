package com.yongche.cache.redisbinary;

import jmind.redis.codec.RedisCodec;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import static java.nio.charset.CoderResult.OVERFLOW;

/**
 * Created by mma on 16/12/28.
 */
public class Utf8CodecBinary extends RedisCodec<String, byte[]> {

    private Charset charset;
    private CharsetDecoder decoder;
    private CharBuffer chars;

    /**
     * Initialize a new instance that encodes and decodes strings using
     * the UTF-8 charset;
     */
    public Utf8CodecBinary() {
        charset = Charset.forName("UTF-8");
        decoder = charset.newDecoder();
        chars = CharBuffer.allocate(1024);
    }

    @Override
    public String decodeKey(ByteBuffer bytes) {
        return decode(bytes);
    }

    @Override
    public byte[] decodeValue(ByteBuffer bytes) {
        chars.clear();
        bytes.mark();
        decoder.reset();
        try {
            byte[] b = new byte[bytes.remaining()];
            int i = 0;
            while (bytes.hasRemaining()){
                b[i] = bytes.get();
                i++;
            }
            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] encodeKey(String key) {
        return encode(key);
    }

    @Override
    public byte[] encodeValue(byte[] value) {
        return value;
    }

    private String decode(ByteBuffer bytes) {
        chars.clear();
        bytes.mark();
        decoder.reset();
        while (decoder.decode(bytes, chars, true) == OVERFLOW || decoder.flush(chars) == OVERFLOW) {
            chars = CharBuffer.allocate(chars.capacity() * 2);
            bytes.reset();
        }
        return chars.flip().toString();
    }

    private byte[] encode(String string) {
        return string.getBytes(charset);
    }
}
