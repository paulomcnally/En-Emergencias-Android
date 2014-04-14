package com.mc.emergency;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private ExpandableHeightGridView gridViewTypes;
    private ExpandableHeightGridView gridViewNumbers;
    private static Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = getResources();

        // set types on gridView

        gridViewTypes = (ExpandableHeightGridView) this.findViewById(R.id.mainGridViewTypes);
        CustomGridAdapter gridAdapterTypes = new CustomGridAdapter(MainActivity.this, res.getStringArray(R.array.emergency_types),res.getStringArray(R.array.emergency_types_images));
        gridViewTypes.setAdapter(gridAdapterTypes);
        gridViewTypes.setExpanded(true);
        gridViewTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //text.setText((String) (gridView.getItemAtPosition(position)));
                Log.i("ITEM_CLICKED", "" + (String) (gridViewTypes.getItemAtPosition(position)));
            }
        });


        gridViewNumbers = (ExpandableHeightGridView) this.findViewById(R.id.mainGridViewNumbers);
        CustomGridAdapter gridAdapterNumbers = new CustomGridAdapter(MainActivity.this, res.getStringArray(R.array.emergency_numbers_text), res.getStringArray(R.array.emergency_numbers_images));
        gridViewNumbers.setAdapter(gridAdapterNumbers);
        gridViewNumbers.setExpanded(true);
        gridViewNumbers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent dial = new Intent();
                dial.setAction("android.intent.action.DIAL");
                dial.setData(Uri.parse("tel:" + res.getStringArray(R.array.emergency_numbers)[position] ));
                startActivity(dial);
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
