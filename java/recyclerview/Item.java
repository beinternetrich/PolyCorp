package recyclerview;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;

import java.io.Serializable;

public class Item implements Serializable {

	private static final long serialVersionUID = -6882745111884490060L;

	private int id;
	private String title;
	private String subtitle;
	private Image image;
	private Drawable drawable;
	private Bitmap bitmap;

	@Override
	public boolean equals(Object inObject) {
		if (inObject instanceof Item) {
			Item inItem = (Item) inObject;
			return this.id == inItem.id;
		}
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	public Bitmap getBitmap() {		return bitmap;	}

	public void setBitmap(Bitmap bitmap) {		this.bitmap = bitmap;	}

	@Override
	public String toString() {
		return title;
	}
}