package com.yongche.enumdata;

/**
 * Created by mma on 16/12/29.
 * COMPRESS_COMMON_LEVEL_COMPATIBLE_MODEL,UNCOMPRESS_COMPATIBLE_MODEL
 * 以上两个模式,目前仅仅在选车列表中和php交互使用
 * COMPATIBLE_FOR_PHP_ZGIP_MODEL
 * 该模式与php中的GZIP模式交互
 */
public enum CompressModelEnum {

    /*压缩级别为6的兼容模式,(兼容GZIP和PKZIP格式)*/
    COMPRESS_COMMON_LEVEL_COMPATIBLE_MODEL(6,true),

    /*兼容GZIP和PKZIP格式的解压缩模式*/
    UNCOMPRESS_COMPATIBLE_MODEL(true),

    /*用来压缩/解压缩PHP中对应的GZIP,实际上在java中还是zlib,只是nowrap为false*/
    COMPATIBLE_FOR_PHP_ZGIP_MODEL(6,false);

    private int level;

    /*
     If 'nowrap' is true then the ZLIB header and checksum fields will
     not be used in order to support the compression format used in
     both GZIP and PKZIP.*/
    private boolean nowrap;

    CompressModelEnum(int level, boolean nowrap) {
        this.level = level;
        this.nowrap = nowrap;
    }

    CompressModelEnum(boolean nowrap) {
        this.nowrap = nowrap;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isNowrap() {
        return nowrap;
    }

    public void setNowrap(boolean nowrap) {
        this.nowrap = nowrap;
    }
}
