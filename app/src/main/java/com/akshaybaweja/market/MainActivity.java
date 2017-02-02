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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPager;

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
                            color = getResources().getColor(R.color.purple, null);
                            newDrawable = getResources().getDrawable(R.drawable.ic, null);
                            break;
                        case 1:
                            imageUrl = "http://www.akshaybaweja.com/market/hardware.jpg";
                            color = getResources().getColor(R.color.orange, null);
                            newDrawable = getResources().getDrawable(R.drawable.hardware, null);
                            break;
                        case 2:
                            imageUrl = "http://www.akshaybaweja.com/market/tools.jpg";
                            color = getResources().getColor(R.color.cyan, null);
                            newDrawable = getResources().getDrawable(R.drawable.tool, null);
                            break;
                        case 3:
                            imageUrl = "http://www.akshaybaweja.com/market/laser.jpg";
                            color = getResources().getColor(R.color.green, null);
                            newDrawable = getResources().getDrawable(R.drawable.laser, null);
                            break;
                        case 4:
                            imageUrl = "http://www.akshaybaweja.com/market/misc.jpg";
                            color = getResources().getColor(R.color.red, null);
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

            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
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
        RelativeLayout rv1 = (RelativeLayout) v.findViewById(R.id.relativeLayout1);
        RelativeLayout rv2 = (RelativeLayout) v.findViewById(R.id.relativeLayout2);

        if(rv1.getVisibility()==RelativeLayout.VISIBLE){
            rv1.setVisibility(RelativeLayout.INVISIBLE);
            rv2.setVisibility(RelativeLayout.VISIBLE);
        } else {
            rv2.setVisibility(RelativeLayout.INVISIBLE);
            rv1.setVisibility(RelativeLayout.VISIBLE);
        }
    }
}