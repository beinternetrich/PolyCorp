package util;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public final class DbBitmapUtility {

    //private DbBitmapUtility() {}

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {

        String encodedImageString = Base64.encodeToString(image, Base64.DEFAULT);
        byte[] byteArray = Base64.decode(encodedImageString, Base64.DEFAULT);
        Bitmap bmImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        //return BitmapFactory.decodeByteArray(image, 0, image.length);
        return bmImage;
    }







    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static byte[] getBytes(Drawable drawable) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int quality = 100; //0 to 100?
        //Drawable drawable = this.appliccontext.getResource().getDrawable(R.drawable.me);
        //Drawable drawable = this.appliccontext.getDrawable(R.drawable.me);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, stream);
        return stream.toByteArray();
    }

}