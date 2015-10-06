package model;

/**
 * Created by Marcy on 17/07/2015.
 */
public class Product {
    //Class Object Variables
    protected int    productid;     //`product_id` INTEGER, COMMENT 'Product ID',
    //public String productcode;    //`product_code` TEXT, COMMENT 'Product Code',
    protected String productname;   //`name`        TEXT,COMMENT 'Product Name',
    protected String productdshort; //`description_short`   TEXT,COMMENT 'Short Desc',
    //public int    price;          //`price`   INTEGER,COMMENT 'Customer Retail Price',
    //public int    saleprice;      //`sale_price`  INTEGER,COMMENT 'On-Sale Price',
    protected int   productcostprice; //`cost_price`    INTEGER,COMMENT 'Cost Price',
    protected int   productcatid; //`cat_id`    INTEGER,COMMENT 'Main Category ID',
    //public int    popularity;     //`popularity`  INTEGER,COMMENT 'Popularity',
    //public int    taxtype;        //`tax_type`    INTEGER,COMMENT 'Tax Type'
    protected byte[] productimage;  //`image`       BLOB,COMMENT 'Product Image'
    //public int    productcreated; //`created`     LONG,COMMENT 'Created Date'


    //Class Object Initializer
    public Product() {}


    //Class Object Morphers/Overriders/New Obj Parameter Variations
    public Product(int id, String name, String desc_short, int cost_price, int cat_id) {
        this.productid = id;
        this.productname = name;
        this.productdshort = desc_short;
        this.productcostprice = cost_price;
        this.productcatid = cat_id;
    }

    public Product(String name, String desc_short, int cat_id, byte[] image) {
        this.productname = name;
        this.productdshort = desc_short;
        this.productcatid = cat_id;
        this.productimage = image;
    }

    public Product(String name, String desc_short, int cat_id) {
        this.productname = name;
        this.productdshort = desc_short;
        this.productcatid = cat_id;
    }


    //Class Object Variable Getters and Setters

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductdshort() {        return productdshort;    }

    public void setProductdshort(String productdshort) {
        this.productdshort = productdshort;
    }

    public int getProductcostprice() {
        return productcostprice;
    }

    public void setProductcostprice(int productcostprice) {        this.productcostprice = productcostprice;    }

    public int getProductcatid() {
        return productcatid;
    }

    public void setProductcatid(int productcatid) {
        this.productcatid = productcatid;
    }

    public byte[] getProductimage() { return productimage; }

    public void setProductimage(byte[] productimage) { this.productimage = productimage; }
}
