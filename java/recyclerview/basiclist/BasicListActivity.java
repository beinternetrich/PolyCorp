package recyclerview.basiclist;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.mmtechworks.polygam101.R;

import java.util.ArrayList;
import java.util.List;

import recyclerview.DividerItemDecoration;

/**
 * Created by per-erik on 16/11/14.
 */
public class BasicListActivity extends Activity {

    private RecyclerView mRecyclerView;

    private int                           mEntityCounter = 0;
    private List<BasicListAdapter.Entity> mData          = new ArrayList<BasicListAdapter.Entity>();
    private BasicListAdapter mBasicListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_recycler_view_example);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.setAdapter(getAdapter());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // This part is just added to show the animations.
        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                swipeView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeView.setRefreshing(false);
                        addData(10, 5);
                    }
                }, 1000);
            }
        });

        ActionBar actionBar = getActionBar();
        if(actionBar!=null) actionBar.setTitle("My Basic List");
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    private RecyclerView.Adapter getAdapter() {
        mBasicListAdapter = new BasicListAdapter(this);
        addData(15, 0);

        mBasicListAdapter.setData(new ArrayList<BasicListAdapter.Entity>(mData));
        mBasicListAdapter.setOnItemClickListener(new BasicListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BasicListAdapter.Entity entity) {
                System.out.println("BasicListActivity.onItemClick entity : " + entity);
            }
        });
        return mBasicListAdapter;
    }

    private void addData(int add, int del) {
        for (int i = 0; i < del; ++i) {
            int r = (int) (Math.random() * mData.size());
            mData.remove(r);
        }

        for (int i = 0; i < add; ++i) {
            int r = (int) (Math.random() * mData.size());
            mData.add(r, new BasicListAdapter.Entity("Item " + (++mEntityCounter)));
        }

        mBasicListAdapter.setData(new ArrayList<BasicListAdapter.Entity>(mData));
    }
}
