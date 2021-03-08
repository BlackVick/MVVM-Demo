package esw.learn.mvvmapplication;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import esw.learn.mvvmapplication.adapters.WatchlistAdapter;
import esw.learn.mvvmapplication.databinding.ActivityWatchListBinding;
import esw.learn.mvvmapplication.listeners.WatchlistListener;
import esw.learn.mvvmapplication.models.TvShow;
import esw.learn.mvvmapplication.utilities.Common;
import esw.learn.mvvmapplication.viewmodels.WatchListViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class WatchList extends AppCompatActivity implements WatchlistListener {

    private ActivityWatchListBinding activityWatchListBinding;
    private WatchListViewModel viewModel;
    private WatchlistAdapter adapter;
    private List<TvShow> watchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchListBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_watch_list
        );

        //initialize
        initialize();
    }

    private void initialize() {

        viewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
        activityWatchListBinding.imageBack.setOnClickListener(v -> {
            onBackPressed();
        });
        watchlist = new ArrayList<>();

        loadWatchList();

    }

    private void loadWatchList() {
        activityWatchListBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModel.loadWatchlist().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShows -> {
                    activityWatchListBinding.setIsLoading(false);
                    if (tvShows.size() > 0) {

                        watchlist.clear();

                    }
                    watchlist.addAll(tvShows);
                    adapter = new WatchlistAdapter(watchlist, this);
                    activityWatchListBinding.watchListRecycler.setAdapter(adapter);
                    activityWatchListBinding.watchListRecycler.setVisibility(View.VISIBLE);
                    compositeDisposable.dispose();
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Common.IS_WATCHLIST_UPDATED){

            loadWatchList();
            Common.IS_WATCHLIST_UPDATED = false;

        }
    }

    @Override
    public void onTvShowClicked(TvShow tvShow) {

        Intent detailsIntent = new Intent(getApplicationContext(), ShowDetails.class);
        detailsIntent.putExtra("tvShow", tvShow);
        startActivity(detailsIntent);

    }

    @Override
    public void removeTvShowFromWatchlist(TvShow tvShow, int position) {
        CompositeDisposable compositeDisposableDelete = new CompositeDisposable();
        compositeDisposableDelete.add(viewModel.removeTvShowFromWatchlist(tvShow)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    watchlist.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, adapter.getItemCount());
                    compositeDisposableDelete.dispose();
                }));
    }
}