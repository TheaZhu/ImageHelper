package com.thea.image;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;

/**
 * Created by Thea on 2016/2/18 0018.
 */
public class ImageHelper {

    /**
     * 旋转图片
     * @param bmp
     * @param degree
     * @return
     */
    public static Bitmap rotateImage(Bitmap bmp, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
    }

    /**
     * 调整图片色光三原色（色相，饱和度，亮度）
     *
     * @param bmp
     * @param hue
     * @param saturation
     * @param lum
     * @return
     */
    public static Bitmap handleImageEffect(Bitmap bmp, int hue, int saturation, int lum) {
        Bitmap bitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);


        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, hue);    //red
        hueMatrix.setRotate(1, hue);    //green
        hueMatrix.setRotate(2, hue);    //blue

        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(lum, lum, lum, 1);

        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(lumMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return bitmap;
    }

    /**
     * 通过色彩矩阵调整图片
     * @param bmp
     * @param matrix  4*5的矩阵
     * @return
     */
    public static Bitmap handleImageEffect(Bitmap bmp, float[] matrix) {
        Bitmap bitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(),
                Bitmap.Config.ARGB_8888);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(matrix);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return bitmap;
    }

    /**
     * 将图像处理为底片效果
     * @param bmp
     * @return
     */
    public static Bitmap handleImageNegative(Bitmap bmp) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        int[] oldPixels = new int[width * height];
        int[] newPixels = new int[width * height];
        bmp.getPixels(oldPixels, 0, width, 0, 0, width, height);
        int color;

        for (int i = 0; i < width * height; i++) {
            color = oldPixels[i];
            int red = Math.max(0, Math.min(255 - Color.red(color), 255));
            int green = Math.max(0, Math.min(255 - Color.green(color), 255));
            int blue = Math.max(0, Math.min(255 - Color.blue(color), 255));
            int alpha = Color.alpha(color);

            newPixels[i] = Color.argb(alpha, red, green, blue);
        }

        bitmap.setPixels(newPixels, 0, width, 0, 0, width, height);

        return bitmap;
    }

    /**
     * 将图像处理为底片效果
     * @param bmp
     * @return
     */
    public static Bitmap handleImageNegativeByMatrix(Bitmap bmp) {
        float[] matrix = {-1, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 1, 0};
        return handleImageEffect(bmp, matrix);
    }

    /**
     * 将图像处理为怀旧效果
     * @param bmp
     * @return
     */
    public static Bitmap handleImageOldPhoto(Bitmap bmp) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        int[] oldPixels = new int[width * height];
        int[] newPixels = new int[width * height];
        bmp.getPixels(oldPixels, 0, width, 0, 0, width, height);
        int color;
        int r, g, b;

        for (int i = 0; i < width * height; i++) {
            color = oldPixels[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            int red = (int) Math.max(0, Math.min(0.393 * r + 0.769 * g + 0.189 * b, 255));
            int green = (int) Math.max(0, Math.min(0.349 * r + 0.686 * g + 0.168 * b, 255));
            int blue = (int) Math.max(0, Math.min(0.272 * r + 0.534 * g + 0.131 * b, 255));
            int alpha = Color.alpha(color);

            newPixels[i] = Color.argb(alpha, red, green, blue);
        }

        bitmap.setPixels(newPixels, 0, width, 0, 0, width, height);

        return bitmap;
    }

    /**
     * 将图像处理为怀旧效果
     * @param bmp
     * @return
     */
    public static Bitmap handleImageOldPhotoByMatrix(Bitmap bmp) {
        float[] matrix = {0.393f, 0.769f, 0.189f, 0, 0,
                0.349f, 0.686f, 0.168f, 0, 0,
                0.272f, 0.534f, 0.131f, 0, 0,
                0, 0, 0, 1, 0};
        return handleImageEffect(bmp, matrix);
    }

    /**
     * 将图像处理为浮雕效果
     * @param bmp
     * @return
     */
    public static Bitmap handleImageRelief(Bitmap bmp) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        int[] oldPixels = new int[width * height];
        int[] newPixels = new int[width * height];
        bmp.getPixels(oldPixels, 0, width, 0, 0, width, height);

        for (int i = 1; i < width * height; i++) {
            int preColor = oldPixels[i - 1];
            int preR = Color.red(preColor);
            int preG = Color.green(preColor);
            int preB = Color.blue(preColor);
            int alpha = Color.alpha(preColor);

            int color = oldPixels[i];
            int r = Color.red(color);
            int g = Color.green(color);
            int b = Color.blue(color);

            int red = Math.max(0, Math.min(preR - r + 127, 255));
            int green = Math.max(0, Math.min(preG - g + 127, 255));
            int blue = Math.max(0, Math.min(preB - b + 127, 255));
//            int alpha = Color.alpha(color);

            newPixels[i] = Color.argb(alpha, red, green, blue);
        }

        bitmap.setPixels(newPixels, 0, width, 0, 0, width, height);

        return bitmap;
    }

    /**
     * 通过xfermode将图片转换为圆形图片
     * @param bmp
     * @return
     */
    public static Bitmap convertCircleImage(Bitmap bmp) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //Dst
        int radius = Math.min(width, height) / 2;
        canvas.drawCircle(width / 2, height / 2, radius, paint);    //遮罩层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //Src
        canvas.drawBitmap(bmp, 0, 0, paint);
        paint.setXfermode(null);

        return bitmap;
    }

    /**
     * 通过BitmapShader将图片转换为圆形图片
     * @param bmp
     * @return
     */
    public static Bitmap convertCircleImageByShader(Bitmap bmp) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        BitmapShader shader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(shader);
        canvas.drawCircle(width / 2, height / 2, Math.min(width, height) / 2, paint);

        return bitmap;
    }

    /**
     * 图片倒影
     * @param bmp
     * @return
     */
    public static Bitmap invertImage(Bitmap bmp) {
        Bitmap bitmap = rotateImage(bmp, 180);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置线性渐变的着色器
        paint.setShader(new LinearGradient(0, 0, bmp.getWidth(), bmp.getHeight() * 0.5f,
                0XDD000000, 0X10000000, Shader.TileMode.CLAMP));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, 0, bmp.getWidth(), bmp.getHeight(), paint);

        return bitmap;
    }
}
