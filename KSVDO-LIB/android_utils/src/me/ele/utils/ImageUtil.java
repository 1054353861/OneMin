package me.ele.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import me.ele.utils.StreamUtil;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageUtil {

    private static final int QUALITY = 100;
    
    private ImageUtil() {
        
    }

    public static Bitmap toRoundness(Resources res, int id) {
        Bitmap bitmap = BitmapFactory.decodeResource(res, id);
        return toRoundness(bitmap);
    }

    public static Bitmap toRoundness(Bitmap bitmap) {
        return toRoundCorner(bitmap, bitmap.getWidth() / 2);
    }

    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static BitmapDrawable toRoundCorner(Resources res,
            BitmapDrawable bitmapDrawable, int pixels) {
        Bitmap bitmap = bitmapDrawable.getBitmap();
        bitmapDrawable = new BitmapDrawable(res, toRoundCorner(bitmap, pixels));
        return bitmapDrawable;
    }

    public static BitmapDrawable toRoundCorner(Resources res, Bitmap bitmap,
            int pixels) {
        return new BitmapDrawable(res, toRoundCorner(bitmap, pixels));
    }

    public static InputStream bitmap2InputStream(Bitmap bm) {
        if (bm == null) return null;
        InputStream is = new ByteArrayInputStream(bitmap2Bytes(bm));
        return is;
    }

    public static Bitmap bytesToBitmap(byte[] byteArray) {
        if (byteArray.length != 0) {
            return BitmapFactory
                    .decodeByteArray(byteArray, 0, byteArray.length);
        } else {
            return null;
        }
    }

    public static Drawable bytesToDrawable(byte[] byteArray) {
        ByteArrayInputStream ins = new ByteArrayInputStream(byteArray);
        return Drawable.createFromStream(ins, null);
    }

    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, QUALITY, baos);
        try {
            baos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StreamUtil.closeIO(baos);
        }
        return baos.toByteArray();
    }

    public static void saveBitmap2Output(Bitmap bm, OutputStream output) {
        bm.compress(Bitmap.CompressFormat.PNG, QUALITY, output);
        try {
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StreamUtil.closeIO(output);
        }
    }
}
