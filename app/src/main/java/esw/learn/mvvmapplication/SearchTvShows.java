package esw.learn.mvvmapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import esw.learn.mvvmapplication.adapters.TvShowAdapter;
import esw.learn.mvvmapplication.databinding.ActivitySearchTvShowsBinding;
import esw.learn.mvvmapplication.listeners.TvShowListener;
import esw.learn.mvvmapplication.models.TvShow;
import esw.learn.mvvmapplication.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchTvShows extends AppCompatActivity implements TvShowListener {

    private ActivitySearchTvShowsBinding searchTvShowsBinding;
    private SearchViewModel viewModel;
    private List<TvShow> tvShows = new ArrayList<>();
    private TvShowAdapter adapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchTvShowsBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_tv_shows);

        //initialize
        initialize();
    }

    private void initialize() {

        searchTvShowsBinding.imageBack.setOnClickListener(v -> {
            onBackPressed();
        });
        searchTvShowsBinding.tvShowRecycler.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        adapter = new TvShowAdapter(tvShows, this);
        searchTvShowsBinding.tvShowRecycler.setAdapter(adapter);
        searchTvShowsBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null){
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().isEmpty()){
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(() -> {

                                currentPage = 1;
                                totalAvailablePages = 1;
                                searchTvShow(s.toString());

                            });
                        }
                    }, 1000);
                } else {
                    tvShows.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        //add scroll listener
        searchTvShowsBinding.tvShowRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!searchTvShowsBinding.tvShowRecycler.canScrollVertically(1)){
                    if (!searchTvShowsBinding.inputSearch.getText().toString().trim().isEmpty()){
                        if (currentPage < totalAvailablePages){
                            currentPage += 1;
                            searchTvShow(searchTvShowsBinding.inputSearch.getText().toString().trim());
                        }
                    }

                }
            }
        });

        searchTvShowsBinding.inputSearch.requestFocus();

    }

    private void searchTvShow(String query){

        toggleLoading();
        viewModel.searchTvShow(query, currentPage).observe(this, tvSearchResponse -> {
            toggleLoading();
            if (tvSearchResponse != null){

                totalAvailablePages = tvSearchResponse.getPages() ;
                if (tvSearchResponse.getTv_shows() != null){

                    int oldCount = tvShows.size();
                    tvShows.addAll(tvSearchResponse.getTv_shows());
                    adapter.notifyItemRangeInserted(oldCount, tvShows.size());

                }

            }
        });

    }

    private void toggleLoading(){

        if (currentPage == 1){
            if (searchTvShowsBinding.getIsLoading() != null && searchTvShowsBinding.getIsLoading()){
                searchTvShowsBinding.setIsLoading(false);
            } else {
                searchTvShowsBinding.setIsLoading(true);
            }
        } else {
            if (searchTvShowsBinding.getIsLoadingMore() != null && searchTvShowsBinding.getIsLoadingMore()){
                searchTvShowsBinding.setIsLoadingMore(false);
            } else {
                searchTvShowsBinding.setIsLoadingMore(true);
            }
        }

    }

    @Override
    public void onTvShowClicked(TvShow tvShow) {

        Intent detailsIntent = new Intent(getApplicationContext(), ShowDetails.class);
        detailsIntent.putExtra("tvShow", tvShow);
        startActivity(detailsIntent);

    }
}