package com.mc.emergency;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends ActionBarActivity {

    private static final String MY_AD_UNIT_ID = "ca-app-pub-2015513932539714/1215785827";

    private InterstitialAd interstitial;


    private ExpandableHeightGridView gridViewTypes;
    private ExpandableHeightGridView gridViewNumbers;
    private static Resources res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init admob
        adMobInit();

        res = getResources();

        // set types on gridView

        gridViewTypes = (ExpandableHeightGridView) this.findViewById(R.id.mainGridViewTypes);
        CustomGridAdapter gridAdapterTypes = new CustomGridAdapter(MainActivity.this, res.getStringArray(R.array.emergency_types_enabled), res.getStringArray(R.array.emergency_types_images));
        gridViewTypes.setAdapter(gridAdapterTypes);
        gridViewTypes.setExpanded(true);
        gridViewTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                displayInterstitial();

                ImageView i = (ImageView) parent.findViewById(R.id.grid_item_image);

                AlphaAnimation alpha = new AlphaAnimation(0.5F, 0.5F); // change values as you want
                alpha.setDuration(9);
                alpha.setFillAfter(true);
                i.startAnimation(alpha);


                if (!res.getStringArray(R.array.emergency_types_pages)[position].equals("empty")) {

                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), WebActivity.class);
                    intent.putExtra("page", res.getStringArray(R.array.emergency_types_pages)[position]);
                    intent.putExtra("title", res.getStringArray(R.array.emergency_types)[position]);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);

                    AlphaAnimation alphaOut = new AlphaAnimation(1F, 1F); // change values as you want
                    alphaOut.setDuration(0);
                    alphaOut.setFillAfter(true);
                    i.startAnimation(alphaOut);
                } else {
                    Toast.makeText(getApplicationContext(), res.getString(R.string.message_empty), Toast.LENGTH_LONG).show();
                }

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
                dial.setData(Uri.parse("tel:" + res.getStringArray(R.array.emergency_numbers)[position]));
                startActivity(dial);
            }
        });

    }

    private void adMobInit() {
        // Create the interstitial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(MY_AD_UNIT_ID);

        // Create ad request.
        AdRequest adRequest = new AdRequest.Builder().build();

        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);
    }

    // Invoke displayInterstitial() when you are ready to display an interstitial.
    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // re-init admob
        adMobInit();

    }

}
