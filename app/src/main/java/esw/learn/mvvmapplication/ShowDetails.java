package esw.learn.mvvmapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import esw.learn.mvvmapplication.adapters.EpisodesAdapter;
import esw.learn.mvvmapplication.adapters.ImageSliderAdapter;
import esw.learn.mvvmapplication.databinding.ActivityShowDetailsBinding;
import esw.learn.mvvmapplication.databinding.LayoutEpisodesBottomSheetBinding;
import esw.learn.mvvmapplication.models.TvShow;
import esw.learn.mvvmapplication.utilities.Common;
import esw.learn.mvvmapplication.viewmodels.TvShowDetailViewModel;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import java.util.Locale;

public class ShowDetails extends AppCompatActivity {

    //activity binding
    private ActivityShowDetailsBinding activityShowDetailsBinding;

    //view model
    private TvShowDetailViewModel viewModel;

    //bottom sheet
    private BottomSheetDialog bottomSheetDialog;
    private LayoutEpisodesBottomSheetBinding bottomSheetBinding;

    //data
    private TvShow tvShow;
    private Boolean isShowAvailableInWatchlist = false;

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

        //get current tv show
        tvShow = (TvShow) getIntent().getSerializableExtra("tvShow");

        //check if available offline
        checkTvShowInWatchlist();

        //get show details
        getTvShowDetails();

    }

    private void checkTvShowInWatchlist() {

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModel.getTvShowFromWatchlist(String.valueOf(tvShow.getId()))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShow -> {
                    isShowAvailableInWatchlist = true;
                    activityShowDetailsBinding.imageWatchList.setImageResource(R.drawable.ic_add);
                    compositeDisposable.dispose();
                }));

    }

    private void getTvShowDetails() {
        //set state
        activityShowDetailsBinding.setIsLoading(true);

        //get data
        String tvShowId = String.valueOf(tvShow.getId());
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
                    if (activityShowDetailsBinding.textReadMore.getText().toString().equals("Read More")) {
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
                if (detailsResponse.getTvShow().getGenres() != null) {
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
                activityShowDetailsBinding.buttonEpisodes.setOnClickListener(v -> {
                    if (bottomSheetDialog == null) {
                        bottomSheetDialog = new BottomSheetDialog(ShowDetails.this);
                        bottomSheetBinding = DataBindingUtil.inflate(
                                LayoutInflater.from(ShowDetails.this),
                                R.layout.layout_episodes_bottom_sheet,
                                findViewById(R.id.episodesContainer),
                                false
                        );
                        bottomSheetDialog.setContentView(bottomSheetBinding.getRoot());
                        bottomSheetBinding.episodesRecyclerView.setAdapter(
                                new EpisodesAdapter(detailsResponse.getTvShow().getEpisodes())
                        );
                        bottomSheetBinding.textTitle.setText(
                                String.format("Episodes | %s", tvShow.getName())
                        );
                        bottomSheetBinding.imageClose.setOnClickListener(v1 -> {
                            bottomSheetDialog.dismiss();
                        });

                    }

                    //optional section start
                    FrameLayout frameLayout = bottomSheetDialog.findViewById(
                            com.google.android.material.R.id.design_bottom_sheet
                    );
                    if (frameLayout != null) {
                        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
                        bottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }

                    //show dialog
                    bottomSheetDialog.show();
                });

                activityShowDetailsBinding.imageWatchList.setOnClickListener(v -> {

                    CompositeDisposable compositeDisposable = new CompositeDisposable();
                    if (isShowAvailableInWatchlist) {

                        compositeDisposable.add(viewModel.removeTvShowFromWatchlist(tvShow)
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {

                                    isShowAvailableInWatchlist = false;
                                    Common.IS_WATCHLIST_UPDATED = true;
                                    activityShowDetailsBinding.imageWatchList.setImageResource(R.drawable.ic_watchlist_icon);
                                    Toast.makeText(this, "Removed from WatchList", Toast.LENGTH_SHORT).show();
                                    compositeDisposable.dispose();

                                }));

                    } else {

                        compositeDisposable.add(viewModel.addToWatchList(tvShow)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {

                                    Common.IS_WATCHLIST_UPDATED = true;
                                    activityShowDetailsBinding.imageWatchList.setImageResource(R.drawable.ic_add);
                                    Toast.makeText(this, "Added to WatchList", Toast.LENGTH_SHORT).show();
                                    compositeDisposable.dispose();
                                })
                        );

                    }

                });
                activityShowDetailsBinding.imageWatchList.setVisibility(View.VISIBLE);

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

    private void loadBasicTvShowDetail() {
        activityShowDetailsBinding.setTvShowName(tvShow.getName());
        activityShowDetailsBinding.setNetworkCountry(
                tvShow.getNetwork() + " (" +
                        tvShow.getCountry() + ")"
        );
        activityShowDetailsBinding.setStatus(tvShow.getStatus());
        activityShowDetailsBinding.setStartedDate(
                "Started on: " +
                        tvShow.getStartDate()
        );
        activityShowDetailsBinding.textName.setVisibility(View.VISIBLE);
        activityShowDetailsBinding.textNetworkCountry.setVisibility(View.VISIBLE);
        activityShowDetailsBinding.textStatus.setVisibility(View.VISIBLE);
        activityShowDetailsBinding.textStarted.setVisibility(View.VISIBLE);
    }
}