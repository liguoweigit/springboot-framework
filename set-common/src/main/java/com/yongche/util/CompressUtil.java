package com.yongche.util;



import com.yongche.enumdata.CompressModelEnum;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @author mma
 */
public abstract class CompressUtil {

    static final String s = "{\"name\":\"yongche\",\"age\":1,\"name1\":\"yongche1\"}";
    /**
     * 压缩
     *
     * @param data
     *            待压缩数据
     * @return byte[] 压缩后的数据
     */
    /*public static byte[] compress(byte[] data) {
        byte[] output = new byte[0];

        Deflater compresser = new Deflater();

        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        compresser.end();
        return output;
    }*/

    /**
     * 压缩
     *
     * @param data
     *            待压缩数据
     *
     * @param os
     *            输出流
     */
    /*public static void compress(byte[] data, OutputStream os) {
        DeflaterOutputStream dos = new DeflaterOutputStream(os);

        try {
            dos.write(data, 0, data.length);

            dos.finish();

            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 解压缩
     *
     * @param data
     *            待压缩的数据
     * @return byte[] 解压缩后的数据
     */
    /*public static byte[] decompress(byte[] data) {
        byte[] output = new byte[0];

        Inflater decompresser = new Inflater();
        decompresser.reset();
        decompresser.setInput(data);

        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        decompresser.end();
        return output;
    }*/

    /**
     * 解压缩
     *
     * @param is
     *            输入流
     * @return byte[] 解压缩后的数据
     */
    /*public static byte[] decompress(InputStream is) {
        InflaterInputStream iis = new InflaterInputStream(is);
        ByteArrayOutputStream o = new ByteArrayOutputStream(1024);
        try {
            int i = 1024;
            byte[] buf = new byte[i];

            while ((i = iis.read(buf, 0, i)) > 0) {
                o.write(buf, 0, i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return o.toByteArray();
    }*/

    /**
     * 解压缩指定数据
     * @param data  要解压的数据
     * @param model 解压的模式
     * @return
     */
    public static byte[] decompressData(byte[] data, CompressModelEnum model) throws Exception {
        byte[] unCompressed = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        Inflater deCompressor = new Inflater(model.isNowrap());
        try {
            deCompressor.setInput(data);
            byte[] buf = new byte[1024];
            while (!deCompressor.finished()) {
                int count = deCompressor.inflate(buf);
                bos.write(buf, 0, count);
            }
            unCompressed = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("decompress data error");
        }finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            deCompressor.end();
        }
        return unCompressed;
    }

    /**
     * 压缩数据
     * @param input
     * @param model 压缩模式
     * @return
     */
    public static byte[] compressData(byte[] input,CompressModelEnum model) throws Exception {
        byte[] out = null;
        Deflater deflater = new Deflater(model.getLevel(),model.isNowrap());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            deflater.setInput(input);
            deflater.finish();
            byte[] buf = new byte[1024];
            while (!deflater.finished()) {
                int count = deflater.deflate(buf);
                bos.write(buf, 0, count);
            }
            out = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("compress data error");
        }finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            deflater.end();
        }
        return out;
    }
}

