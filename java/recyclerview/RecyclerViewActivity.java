package recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mmtechworks.polygam101.R;
import recyclerview.basiclist.BasicListActivity;
import recyclerview.foldablelist.FoldableListActivity;
import recyclerview.sectionlist.SectionListActivity;

public class RecyclerViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.v("LOG_RecycVu", "line19.....");
    setContentView(R.layout.activity_recyclertypes);

    findViewById(R.id.btn_basic).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(RecyclerViewActivity.this, BasicListActivity.class));
        }
    });

    findViewById(R.id.btn_section).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    startActivity(new Intent(RecyclerViewActivity.this,SectionListActivity.class));
        }});

    findViewById(R.id.btn_foldable).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    startActivity(new Intent(RecyclerViewActivity.this,FoldableListActivity.class));
        }});

    findViewById(R.id.btn_hscroll).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(RecyclerViewActivity.this,HScrollActivity.class));
        }});
    }
}
