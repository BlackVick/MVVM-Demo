package esw.learn.mvvmapplication;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.viewpager2.widget.ViewPager2;
import esw.learn.mvvmapplication.adapters.ImageSliderAdapter;
import esw.learn.mvvmapplication.databinding.ActivityShowDetailsBinding;
import esw.learn.mvvmapplication.utilities.Common;
import esw.learn.mvvmapplication.viewmodels.TvShowDetailViewModel;

public class ShowDetails extends AppCompatActivity {

    //activity binding
    private ActivityShowDetailsBinding activityShowDetailsBinding;

    //view model
    private TvShowDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityShowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_details);

        //initialize
        initialize();
    }

    private void initialize() {

        //view model
        viewModel = new ViewModelProvider(this).get(TvShowDetailViewModel.class);

        //get show details
        getTvShowDetails();

    }

    private void getTvShowDetails() {
        //set state
        activityShowDetailsBinding.setIsLoading(true);

        //get data
        String tvShowId = String.valueOf(getIntent().getIntExtra(Common.INTENT_SHOW_ID, -1));
        viewModel.getTvShowDetails(tvShowId).observe(this, detailsResponse -> {
            //set state
            activityShowDetailsBinding.setIsLoading(false);

            //check for null response
            if (detailsResponse.getTvShow() != null) {

                if (detailsResponse.getTvShow().getPictures() != null) {

                    loadImageSlider(detailsResponse.getTvShow().getPictures());

                }

            }
        });
    }

    private void loadImageSlider(String[] images) {

        activityShowDetailsBinding.imageViewPager.setOffscreenPageLimit(1);
        activityShowDetailsBinding.imageViewPager.setAdapter(new ImageSliderAdapter(images));
        activityShowDetailsBinding.imageViewPager.setVisibility(View.VISIBLE);
        activityShowDetailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);
        setupSliderIndicators(images.length);
        activityShowDetailsBinding.imageViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });

    }

    private void setupSliderIndicators(int count) {

        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {

            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.background_slider_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            activityShowDetailsBinding.sliderIndicator.addView(indicators[i]);

        }
        activityShowDetailsBinding.sliderIndicator.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);

    }

    private void setCurrentSliderIndicator(int position) {

        int childCount = activityShowDetailsBinding.sliderIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {

            ImageView imageView = (ImageView) activityShowDetailsBinding.sliderIndicator.getChildAt(i);
            if (i == position) {

                imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                                getApplicationContext(),
                                R.drawable.background_slider_indicator_active
                        )
                );

            } else {

                imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                                getApplicationContext(),
                                R.drawable.background_slider_indicator_inactive
                        )
                );

            }

        }

    }
}