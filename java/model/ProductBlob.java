package model;

/*
 * ***************************************************
 * ImageBlob.java
 * Model of Image in game.db
 * ***************************************************
 */
public class ProductBlob {
    //Class Object Variables
    public int    mImageId; //COMMENT 'Product_Id',
    public byte[] mImageRef; //COMMENT 'Image'

    public ProductBlob() {}

    public ProductBlob(int product_id, byte[] image) {
        this.mImageId = product_id;
        this.mImageRef = image;
    }

    public ProductBlob(byte[] image) {
        this.mImageRef = image;
    }


    public int getmImageId() {
        return mImageId;
    }

    public void setmImageId(int mImageId) {
        this.mImageId = mImageId;
    }

    public byte[] getmImageRef() {
        return mImageRef;
    }

    public void setmImageRef(byte[] mImageRef) {
        this.mImageRef = mImageRef;
    }
}
