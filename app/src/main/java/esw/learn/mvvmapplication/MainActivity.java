package esw.learn.mvvmapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import esw.learn.mvvmapplication.adapters.TvShowAdapter;
import esw.learn.mvvmapplication.listeners.TvShowListener;
import esw.learn.mvvmapplication.models.TvShow;
import esw.learn.mvvmapplication.utilities.Common;
import esw.learn.mvvmapplication.viewmodels.TvShowViewModel;
import esw.learn.mvvmapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements TvShowListener {

    //activity binding
    private ActivityMainBinding activityMainBinding;

    //view model
    private TvShowViewModel tvShowViewModel;

    //data
    private List<TvShow> tvShowList = new ArrayList<>();
    private TvShowAdapter adapter;

    //pagination value
    private int currentPage = 1;
    private int totalAvailable = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //activity binding
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //init
        initialize();

    }

    private void initialize(){
        activityMainBinding.tvListRecycler.setHasFixedSize(true);

        //view model
        tvShowViewModel = new ViewModelProvider(this).get(TvShowViewModel.class);

        //set adapter
        adapter = new TvShowAdapter(tvShowList, this);
        activityMainBinding.tvListRecycler.setAdapter(adapter);

        //add scroll listener
        activityMainBinding.tvListRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityMainBinding.tvListRecycler.canScrollVertically(1)){

                    if (currentPage <= totalAvailable){
                        currentPage += 1;
                        getTvShows();
                    }

                }
            }
        });

        //get tv shows
        getTvShows();
    }

    private void getTvShows(){
        //set state
        toggleLoading();


        tvShowViewModel.getTvShows(currentPage).observe(this, tvResponse -> {

            //update state
            toggleLoading();

            if (tvResponse != null) {
                //set total
                totalAvailable = tvResponse.getPages();

                if (tvResponse.getTv_shows() != null) {
                    int oldCount = tvShowList.size();

                    tvShowList.addAll(tvResponse.getTv_shows());
                    adapter.notifyItemRangeInserted(oldCount, tvShowList.size());

                }

            }

        });
    }

    private void toggleLoading(){

        if (currentPage == 1){
            if (activityMainBinding.getIsLoading() != null && activityMainBinding.getIsLoading()){
                activityMainBinding.setIsLoading(false);
            } else {
                activityMainBinding.setIsLoading(true);
            }
        } else {
            if (activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore()){
                activityMainBinding.setIsLoadingMore(false);
            } else {
                activityMainBinding.setIsLoadingMore(true);
            }
        }

    }

    @Override
    public void onTvShowClicked(TvShow tvShow) {

        Intent detailsIntent = new Intent(MainActivity.this, ShowDetails.class);
        detailsIntent.putExtra("tvShow", tvShow);
        startActivity(detailsIntent);

    }
}