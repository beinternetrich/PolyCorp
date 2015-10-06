package util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mmtechworks.polygam101.R;

import java.util.ArrayList;

import data.DBaseAdapter;
import model.Product;

import static util.DbBitmapUtility.getImage;

/**
 * Created by Marcy on 16/08/2015.
 */
public class MyBusinessList extends Activity{
    DBaseAdapter controller = new DBaseAdapter(this);
    ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_recycler_view_example_2);
        setContentView(R.layout.activity_sys_dblist);

        Log.v("BLIST", "BLIST Start...");
        ArrayList<Product> productList = new ArrayList<>();
        Log.v("BLIST", "new prod arraylist");
        controller.openn();
        Log.v("BLIST", "controller open");
        productList = controller.getBusinessList();
        String myDataArray[] = {};
        byte[] myByteArray[] = {};
        Bitmap myBtmapArray[] = {};
        // Parse the productList and display it in a listview.
        try{
            int arrSize = productList.size();
            myDataArray = new String[arrSize];
            //myByteArray = new byte[900][arrSize];
            myBtmapArray = new Bitmap[arrSize];

            Log.v("BLIST", "got buslist");
            int i=0;
            for (Product product : productList) {
                Log.v("BLIST", "go array data");
                myDataArray[i] = product.getProductname();
                //myDataArray[i] = myDataArray[i] + " : \r\n ";
                //myDataArray[i] = myDataArray[i] + product.getProductdshort();
                //myDataArray[i] = product.getProductname() + " : \r\n " + product.getProductdshort();
                Log.v("BLIST", "goto array img");
                myBtmapArray[i] = getImage(product.getProductimage());
                Log.v("ProdVal", myDataArray[i]);
                i++;
            }
        }
        catch(Exception e){
            Log.v("MYBiz","Error....");
            e.printStackTrace();
        }

        Log.v("BLIST", "completed. now assign");
        //Lets display the array in  ListView
        mListView=(ListView) findViewById(R.id.viewIdSysDbs);
        Log.v("BLIST", "1assign");

        //Bitmap getImage(byte[] image) ;
        //return BitmapFactory.decodeByteArray(image, 0, image.length);
        //logoImage.setImageBitmap(getImage(byte[]image));

        //ArrayAdapter<byte[]> imgAdpr = new ArrayAdapter<>(this, R.layout.g_imageview,R.id.image1,myImageArray);
        //ArrayAdapter<Bitmap> imgAdpr = new ArrayAdapter<>(this, R.layout.g_imageview,R.id.image1,myBtmapArray);
        //mImageView.setBackgroundDrawable(mAdapter.mContext.getResources().getDrawable(R.drawable.sampleimage9));
        //logoImage.setImageBitmap(BitmapFactory.decodeByteArray(productList.accImage, 0, productList.accImage.length));
        Log.v("BLIST", "2assign");
        ArrayAdapter<String> strAdpr = new ArrayAdapter<String>(this,R.layout.g_textview,R.id.text1,myDataArray);
        Log.v("BLIST", "3assign");

        if(mListView != null){
            Log.v("BLIST", "4assign");
            mListView.setAdapter(strAdpr);
            Log.v("BLIST", "5assign");
            //mListView.setAdapter(imgAdpr);
            Log.v("BLIST", "6assign");
        } else {
            Log.v("BLIST","Recorded Null");
        }
    }
}