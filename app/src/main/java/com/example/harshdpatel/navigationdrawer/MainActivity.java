package com.example.harshdpatel.navigationdrawer;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    private AnimatedVectorDrawable mMenuDrawable;
    private AnimatedVectorDrawable mBackDrawable;

    private DrawerLayout mDrawerLayout;
    private ListView mListview;

    private ActionBarDrawerToggle drawerListener;
    private boolean mSlideState;

    private MyAdapter myAdapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myAdapter = new MyAdapter(this);

        mMenuDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_menu_animatable);
        mBackDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_back_animatable);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mListview = (ListView) findViewById(R.id.list);

        drawerListener = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.Drawer_Open, R.string.Drawer_Close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                mSlideState = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mSlideState = false;
            }
        };
        mDrawerLayout.setDrawerListener(drawerListener);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        mListview.setAdapter(myAdapter);
        // mListview.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,frames));

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectposition(position);
            }
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerListener.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }

    public void selectposition(int position) {
        mListview.setItemChecked(position, true);
        String title = myAdapter.getItem(position).toString();
        setTitle(title);
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home)
        {
             Openslider();
            return true;
        } else if (drawerListener.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void Openslider() {
        if (mSlideState) {
           // Drawable homeDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.animation_file);
           // getSupportActionBar().setHomeAsUpIndicator(homeDrawable);
            getSupportActionBar().setHomeAsUpIndicator(mBackDrawable);
            mBackDrawable.start();
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            getSupportActionBar().setHomeAsUpIndicator(mMenuDrawable);
            mMenuDrawable.start();
          //  Drawable homeDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.animation_file);
          //  getSupportActionBar().setHomeAsUpIndicator(homeDrawable);
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    }
}

class MyAdapter extends BaseAdapter
{
    private Context context;
    private String[] frames;
    private int[] images = {R.drawable.ic_home_black_24dp,
                            R.drawable.ic_insert_emoticon_black_24dp,
                            R.drawable.ic_games_black_24dp,
                            R.drawable.ic_mail_outline_black_24dp};

    MyAdapter(Context context)
    {
        this.context = context;
        frames = context.getResources().getStringArray(R.array.frames);
    }

    @Override
    public int getCount() {
        return frames.length;
    }

    @Override
    public Object getItem(int position) {
        return frames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;

        if(convertView ==null)
       {
           LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
           row = inflater.inflate(R.layout.custom_row,null);

       }else
       {
           row = convertView;
       }

        ImageView im1 = (ImageView) row.findViewById(R.id.imageView1);
        TextView txt1 = (TextView) row.findViewById(R.id.textView1);
        TextView txt2 = (TextView) row.findViewById(R.id.textView2);

        im1.setImageResource(images[position]);
        txt1.setText(frames[position]);
        txt2.setVisibility(View.GONE);

        return row;
    }
}
