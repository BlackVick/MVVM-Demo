package esw.learn.mvvmapplication;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.viewpager2.widget.ViewPager2;
import esw.learn.mvvmapplication.adapters.ImageSliderAdapter;
import esw.learn.mvvmapplication.databinding.ActivityShowDetailsBinding;
import esw.learn.mvvmapplication.utilities.Common;
import esw.learn.mvvmapplication.viewmodels.TvShowDetailViewModel;

import java.util.Locale;

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

        //back btn
        activityShowDetailsBinding.imageBack.setOnClickListener(v -> {
            onBackPressed();
        });

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
                activityShowDetailsBinding.setTvShowImageUrl(detailsResponse.getTvShow().getImage_path());
                activityShowDetailsBinding.imageTvShow.setVisibility(View.VISIBLE);
                activityShowDetailsBinding.setDescription(
                        String.valueOf(
                                HtmlCompat.fromHtml(
                                        detailsResponse.getTvShow().getDescription(),
                                        HtmlCompat.FROM_HTML_MODE_LEGACY
                                )
                        )
                );
                activityShowDetailsBinding.textDescription.setVisibility(View.VISIBLE);
                activityShowDetailsBinding.textReadMore.setVisibility(View.VISIBLE);
                activityShowDetailsBinding.textReadMore.setOnClickListener(v -> {
                    if (activityShowDetailsBinding.textReadMore.getText().toString().equals("Read More")){
                        activityShowDetailsBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                        activityShowDetailsBinding.textDescription.setEllipsize(null);
                        activityShowDetailsBinding.textReadMore.setText(R.string.read_less);
                    } else {
                        activityShowDetailsBinding.textDescription.setMaxLines(4);
                        activityShowDetailsBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                        activityShowDetailsBinding.textReadMore.setText(R.string.read_more);
                    }
                });
                activityShowDetailsBinding.setRating(
                        String.format(
                                Locale.getDefault(),
                                "%.2f",
                                Double.parseDouble(detailsResponse.getTvShow().getRating())
                        )
                );
                if (detailsResponse.getTvShow().getGenres() != null){
                    activityShowDetailsBinding.setGenre(detailsResponse.getTvShow().getGenres()[0]);
                } else {
                    activityShowDetailsBinding.setGenre("N/A");
                }
                activityShowDetailsBinding.setRuntime(detailsResponse.getTvShow().getRuntime() + " Mins");
                activityShowDetailsBinding.viewDivider1.setVisibility(View.VISIBLE);
                activityShowDetailsBinding.layoutMisc.setVisibility(View.VISIBLE);
                activityShowDetailsBinding.viewDivider2.setVisibility(View.VISIBLE);
                activityShowDetailsBinding.buttonWebsite.setOnClickListener(v -> {
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW);
                    websiteIntent.setData(Uri.parse(detailsResponse.getTvShow().getUrl()));
                    startActivity(websiteIntent);
                });
                activityShowDetailsBinding.buttonWebsite.setVisibility(View.VISIBLE);
                activityShowDetailsBinding.buttonEpisodes.setVisibility(View.VISIBLE);
                loadBasicTvShowDetail();

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

    private void loadBasicTvShowDetail(){
        activityShowDetailsBinding.setTvShowName(getIntent().getStringExtra(Common.INTENT_SHOW_NAME));
        activityShowDetailsBinding.setNetworkCountry(
                getIntent().getStringExtra(Common.INTENT_SHOW_NETWORK) + " (" +
                        getIntent().getStringExtra(Common.INTENT_SHOW_COUNTRY) + ")"
        );
        activityShowDetailsBinding.setStatus(getIntent().getStringExtra(Common.INTENT_SHOW_STATUS));
        activityShowDetailsBinding.setStartedDate(
                "Started on: " +
                getIntent().getStringExtra(Common.INTENT_SHOW_START)
        );
        activityShowDetailsBinding.textName.setVisibility(View.VISIBLE);
        activityShowDetailsBinding.textNetworkCountry.setVisibility(View.VISIBLE);
        activityShowDetailsBinding.textStatus.setVisibility(View.VISIBLE);
        activityShowDetailsBinding.textStarted.setVisibility(View.VISIBLE);
    }
}