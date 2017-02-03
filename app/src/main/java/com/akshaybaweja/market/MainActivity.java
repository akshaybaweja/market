package com.akshaybaweja.market;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

public class MainActivity extends AppCompatActivity {

    MaterialViewPager materialViewPager;
    View headerLogo;
    ImageView headerLogoContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int tabCount = 5;

        headerLogo = findViewById(R.id.headerLogo);
        headerLogoContent = (ImageView) findViewById(R.id.headerLogoContent);

        this.materialViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        this.materialViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return RecyclerViewFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return tabCount;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getResources().getString(R.string.electronics);
                    case 1:
                        return getResources().getString(R.string.hardware);
                    case 2:
                        return getResources().getString(R.string.tools);
                    case 3:
                        return getResources().getString(R.string.services);
                    case 4:
                        return getResources().getString(R.string.misc);
                    default:
                        return "Page " + position;
                }
            }

            int oldItemPosition = -1;

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);

                if (oldItemPosition != position) {
                    oldItemPosition = position;

                    String imageUrl = null;
                    int color = Color.BLACK;
                    Drawable newDrawable = null;

                    switch (position) {
                        case 0:
                            imageUrl = "http://www.akshaybaweja.com/market/electronics.jpg";
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                color = getResources().getColor(R.color.purple, getTheme());
                            }
                            else {
                                color = getResources().getColor(R.color.purple);
                            }
                            newDrawable = getResources().getDrawable(R.drawable.ic, null);
                            break;
                        case 1:
                            imageUrl = "http://www.akshaybaweja.com/market/hardware.jpg";
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                color = getResources().getColor(R.color.orange, getTheme());
                            }
                            else {
                                color = getResources().getColor(R.color.orange);
                            }
                            newDrawable = getResources().getDrawable(R.drawable.hardware, null);
                            break;
                        case 2:
                            imageUrl = "http://www.akshaybaweja.com/market/tools.jpg";
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                color = getResources().getColor(R.color.cyan, getTheme());
                            }
                            else {
                                color = getResources().getColor(R.color.cyan);
                            }
                            newDrawable = getResources().getDrawable(R.drawable.tool, null);
                            break;
                        case 3:
                            imageUrl = "http://www.akshaybaweja.com/market/laser.jpg";
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                color = getResources().getColor(R.color.green, getTheme());
                            }
                            else {
                                color = getResources().getColor(R.color.green);
                            }
                            newDrawable = getResources().getDrawable(R.drawable.laser, null);
                            break;
                        case 4:
                            imageUrl = "http://www.akshaybaweja.com/market/misc.jpg";
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                color = getResources().getColor(R.color.red, getTheme());
                            }
                            else {
                                color = getResources().getColor(R.color.red);
                            }
                            newDrawable = getResources().getDrawable(R.drawable.misc, null);
                            break;
                    }

                    int fadeDuration = 100;
                    materialViewPager.setColor(color, fadeDuration);
                    materialViewPager.setImageUrl(imageUrl, fadeDuration);
                    toggleLogo(newDrawable, color, fadeDuration);
                }
            }
        });

        this.materialViewPager.getViewPager().setOffscreenPageLimit(tabCount);
        this.materialViewPager.getPagerTitleStrip().setViewPager(this.materialViewPager.getViewPager());

        Toolbar toolbar = materialViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if(actionBar!=null) {
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }


    private void toggleLogo(final Drawable newLogo, final int newColor, int duration){

        final AnimatorSet animatorSetDisappear = new AnimatorSet();
        animatorSetDisappear.setDuration(duration);
        animatorSetDisappear.playTogether(
                ObjectAnimator.ofFloat(headerLogo, "scaleX", 0),
                ObjectAnimator.ofFloat(headerLogo, "scaleY", 0)
        );

        final AnimatorSet animatorSetAppear = new AnimatorSet();
        animatorSetAppear.setDuration(duration);
        animatorSetAppear.playTogether(
                ObjectAnimator.ofFloat(headerLogo, "scaleX", 1),
                ObjectAnimator.ofFloat(headerLogo, "scaleY", 1)
        );

        animatorSetDisappear.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ((GradientDrawable) headerLogo.getBackground()).setColor(newColor);
                headerLogoContent.setImageDrawable(newLogo);
                animatorSetAppear.start();
            }
        });

        animatorSetDisappear.start();
    }

    public void makeCall(View v){
        TextView shop_contact = (TextView) v.findViewById(R.id.shop_contact);
        String phone = String.valueOf(shop_contact.getText());
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(phoneIntent);
    }

    public void cardClicked(View v){
        ViewGroup container = (ViewGroup) v.findViewById(R.id.card_view);

        View rv1 = v.findViewById(R.id.relativeLayout1);
        View rv2 = v.findViewById(R.id.relativeLayout2);

        if(rv1.getVisibility()==View.VISIBLE){
            TransitionManager.beginDelayedTransition(container,
                    new TransitionSet().addTransition(new Fade()).addTransition(new Slide(Gravity.RIGHT)));
            rv1.setVisibility(View.INVISIBLE);
            TransitionManager.beginDelayedTransition(container,
                    new TransitionSet().addTransition(new Fade()).addTransition(new Slide(Gravity.LEFT)));
            rv2.setVisibility(View.VISIBLE);
        } else {
            TransitionManager.beginDelayedTransition(container,
                    new TransitionSet().addTransition(new Fade()).addTransition(new Slide(Gravity.TOP)));
            rv2.setVisibility(View.INVISIBLE);
            TransitionManager.beginDelayedTransition(container,
                    new TransitionSet().addTransition(new Fade()).addTransition(new Slide(Gravity.BOTTOM)));
            rv1.setVisibility(View.VISIBLE);
        }
    }

    public void openMaps(View v){
        TextView add = (TextView) v.findViewById(R.id.shop_address);
        String address = String.valueOf(add.getText());

        Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

}