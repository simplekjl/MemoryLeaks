package company.example.memoryleaks;

import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class MainActivity extends AppCompatActivity {

    private TextView mInfo;
    public static RefWatcher getRefWatcher(Context context) {
        MainActivity application = (MainActivity) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;
    private String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        test = "holi";
        refWatcher = LeakCanary.install(getApplication());
        refWatcher.watch(test);
        changeIt();

        mInfo = (TextView) findViewById(R.id.txtInfo);
        mInfo.setText("The information of the apps is going to appear here...");
        //http://stackoverflow.com/a/13656833
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get VM Heap Size by calling:
                String mTotalMemory = String.valueOf(Runtime.getRuntime().totalMemory());
                //Get Allocated VM Memory by calling:
                String mFreeMemory = String.valueOf(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
                //Get VM Heap Size Limit by calling:
                String mMaxMemory = String.valueOf(Runtime.getRuntime().maxMemory());
                //Get Native Allocated Memory by calling:
                String mHeapAllocatedSize = String.valueOf(Debug.getNativeHeapAllocatedSize());
                mInfo.setText("Total memory: "+mTotalMemory +"\n"+"Allocated Memory Free Memory: "+mFreeMemory+
                "\nMax Memory: "+mMaxMemory+"\nAllocated Memory: "+mHeapAllocatedSize);
                Snackbar.make(view, "Information in the TextView", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void changeIt() {
        test=null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
