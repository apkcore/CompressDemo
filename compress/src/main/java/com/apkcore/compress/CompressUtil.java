package com.apkcore.compress;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by apkcore on 2017/12/24. 01:39
 * mail:apkcore@gmail.com
 */
public class CompressUtil {
    static {
        System.loadLibrary("compress");
    }

    private CompressUtil() {
    }

    /**
     * 使用native方法进行图片压缩。
     * Bitmap的格式必须是ARGB_8888 {@link Bitmap.Config}。
     *
     * @param bitmap   图片数据
     * @param quality  压缩质量
     * @param dstFile  压缩后存放的路径
     * @param optimize 是否使用哈夫曼算法
     * @return 是否成功
     */
    public static boolean nativeCompressBitmap(Bitmap bitmap, int quality, String dstFile, boolean optimize) {
        String dirPath = dstFile.substring(0, dstFile.lastIndexOf(File.separator));
        return nativeCompress(bitmap, quality, dstFile, dirPath, optimize) == 1;
    }

    /**
     * 使用native方法进行图片压缩。
     * Bitmap的格式必须是ARGB_8888 {@link Bitmap.Config}。
     *
     * @param bitmap   图片数据
     * @param quality  压缩质量
     * @param dstFile  压缩后存放的路径
     * @param dirPath  文件存放的目录
     * @param optimize 是否使用哈夫曼算法
     * @return 成功为1，不为argb_8888为-1，失败为0
     */
    private static native int nativeCompress(Bitmap bitmap, int quality, String dstFile, String dirPath, boolean optimize);

}
