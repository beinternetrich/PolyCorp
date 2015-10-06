package recyclerview.sectionlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mmtechworks.polygam101.R;

import recyclerview.list.section.AbstractTreeNodeAdapter;
import recyclerview.list.section.TreeNode;

/**
 * Created by per-erik on 15/11/14.
 */
public class SectionListAdapter extends AbstractTreeNodeAdapter<String, SectionListAdapter.ViewHolder> {

    public static final String TAG = SectionListAdapter.class.getSimpleName();
    private final Context mContext;
    private final LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public SectionListAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        int resourceId = R.layout.section_header;
        if (i == 2) {
            resourceId = R.layout.section_item;
        }

        View view = inflater.inflate(resourceId, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
// /        viewHolder.mImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.me));
        //String url = mDataset[position];
        Log.i("TEST", "-----------position=" + position);
        //((MainActivity)MainActivity.getInstance()).imageDownloader.download(url, viewHolder.mImageView);
//        Uri uri = Uri.fromFile(new File(String.valueOf(getItem(position))));
//        viewHolder.image.setImageBitmap(null);
//        Picasso.with(viewHolder.image.getContext()).load(uri).into(viewHolder.image);

////////////////all above is new
        viewHolder.bind(mData.get(position));
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTextView;
//        private final ImageView mImageView; //added for image

        private TreeNode<String> mEntity;
//        private TreeNode<ImageView> mEntityi; //

        public ViewHolder(View v) {
            super(v);
//            mImageView = (ImageView) v.findViewById(R.id.imageitem);
            mTextView = (TextView) v.findViewById(R.id.label);

            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(mEntity);
                    }
                }
            });
        }

        public void bind(TreeNode<String> entity) {
            mEntity = entity;
            mTextView.setText(entity.getData().toString());
        }

        @Override
        public String toString() {
            return "ViewHolder{" + mTextView.getText() + "}";
        }

    }

//    public SectionListAdapter(String[] myDataset) {
//        mDataset = myDataset;  ////whole module new and mydataset init.
//    }


    public static interface OnItemClickListener {
        public void onItemClick(TreeNode<String> entity);
    }
}