package recyclerview.sectionlist;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;

import com.mmtechworks.polygam101.R;

import recyclerview.DividerItemDecoration;
import recyclerview.list.section.TreeNode;

/**
 * Created by per-erik on 16/11/14.
 */
public class SectionListActivity extends Activity {

    public static final String TAG = SectionListActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_recycler_view_example_2);
        Log.v("LOG_SXN", "32post");

        mRecyclerView = (RecyclerView) findViewById(R.id.cardlist);
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(getAdapter());
        Log.v("LOG_SXN", "38-loadedelements");

//        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe);
//        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeView.setRefreshing(true);
//                swipeView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeView.setRefreshing(false);
//                    }
//                }, 1000);
//            }
//        });
        Log.v("LOG_SXN", "52");
        ActionBar actionBar = getActionBar();
        if(actionBar!=null) actionBar.setTitle("My Section List");
    }

    private RecyclerView.Adapter getAdapter() {
        Log.v("LOG_SXN", "preadapter");
        SectionListAdapter sectionListAdapter = new SectionListAdapter(this);
        Log.v("LOG_SXN", "65-postadapter");

//        LinearLayout rootlay = (LinearLayout) findViewById(R.id.lrecyclerview);  //experiment
        TreeNode<String> root = new TreeNode<String>("root");
//        TreeNode<ImageView> rooti = new TreeNode<ImageView>(null); //added

        for (int i = 0; i < 10; ++i) {
            Log.v("LOG_SXN", "in forloop to 10");
            TreeNode<String> section = new TreeNode<String>(""+i);
//            TreeNode<ImageView> sectioni = new TreeNode<ImageView>(null);
            //int resId = rootlay.getResources().getIdentifier("mainroom01", "drawable", "com.mmtechworks.polygam101");

            root.addChild(section);
//            rooti.addChild(sectioni);
            System.out.println("-Sector " + i + "-");

//            LinearLayout A = new LinearLayout(this);            Log.v("LOG_SXN", "#" + i + "11");
//            A.setOrientation(LinearLayout.VERTICAL);            Log.v("LOG_SXN", "#" + i + "12");
//            TextView aLabel = new TextView(this); aLabel.setText("NewText"); Log.v("LOG_SXN", "13");
//            ImageView aImgView = new ImageView (this);
//            aImgView.setImageResource(R.drawable.carwashauto); Log.v("LOG_SXN", "14");
//            A.addView(aLabel);
//            A.addView(aImgView);                   Log.v("LOG_SXN", "#" + i + "15");

            for (int j = 1; j < 3; ++j) {
                Log.v("LOG_SXN", "#" + i + "16");
//                if (j==1) aImgView.setImageResource(R.drawable.saf);
//                if (j==2) aImgView.setImageResource(R.drawable.me);
                //A.addView(aImgView);
                section.addChild(new TreeNode<String>("Itm " + i + " " + j));
                Log.v("LOG_SXN", "#" + i + "16a");
//                Drawable d = getResources().getDrawable(R.drawable.me);
                Log.v("LOG_SXN", "#" + i + "16b");
                //Drawable di = getResources().getDrawable(int id, Resources.Theme theme);
                Log.v("LOG_SXN", "#" + i + "16c");
//                int resId = getResources().getIdentifier("me", "drawable", "com.mmtechworks.polygam101");
                Log.v("LOG_SXN", "#" + i + "16d");
                //ImageView imagee = (ImageView)findViewById(R.id.image);
                Log.v("LOG_SXN", "#" + i + "16e");
                //imagee.setImageDrawable(d);
                Log.v("LOG_SXN", "#" + i + "16f");
                //x imagee.setImageResource(R.drawable.me);
                Log.v("LOG_SXN", "#" + i + "16g");
                //sectioni.addChild(new TreeNode<Image>(aImgView.getImageResource(R.drawable.me)));
                Log.v("LOG_SXN", "#" + i + "16h");
//                sectioni.addChild(new TreeNode<ImageView>(aImgView));
                Log.v("LOG_SXN", "#" + i + "17yay");
            }
//            rootlay.addView(A);
            Log.v("LOG_SXN", "#" + i + "18eeeeousid");
        }
        //sectionListAdapter.setTree(rooti);
        sectionListAdapter.setTree(root);
        Log.v("LOG_SXN", "#19sweet");

        sectionListAdapter.setOnItemClickListener(new SectionListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TreeNode<String> entity) {
                System.out.println("BasicListActivity.onItemClick entity : " + entity);
            }
        });
        return sectionListAdapter;
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return linearLayoutManager;
    }
}
