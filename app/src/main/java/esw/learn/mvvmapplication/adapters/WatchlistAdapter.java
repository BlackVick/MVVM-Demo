package esw.learn.mvvmapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import esw.learn.mvvmapplication.R;
import esw.learn.mvvmapplication.databinding.TvShowItemBinding;
import esw.learn.mvvmapplication.listeners.WatchlistListener;
import esw.learn.mvvmapplication.models.TvShow;

import java.util.List;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder>{

    private List<TvShow> tvShowList;
    private LayoutInflater layoutInflater;
    private WatchlistListener listener;

    public WatchlistAdapter(List<TvShow> tvShowList, WatchlistListener listener) {
        this.tvShowList = tvShowList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WatchlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        TvShowItemBinding tvShowBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.tv_show_item,
                parent,
                false
        );

        return new WatchlistViewHolder(tvShowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistViewHolder holder, int position) {
        holder.bindTvShows(tvShowList.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShowList.size();
    }

    class WatchlistViewHolder extends RecyclerView.ViewHolder{

        private TvShowItemBinding tvShowItemBinding;

        public WatchlistViewHolder(TvShowItemBinding tvShowItemBinding){

            super(tvShowItemBinding.getRoot());
            this.tvShowItemBinding = tvShowItemBinding;

        }

        public void bindTvShows(TvShow tvShow){

            tvShowItemBinding.setTvShow(tvShow);
            tvShowItemBinding.executePendingBindings();
            tvShowItemBinding.getRoot().setOnClickListener(view -> {
                listener.onTvShowClicked(tvShow);
            });
            tvShowItemBinding.imageDelete.setOnClickListener(v -> {
                listener.removeTvShowFromWatchlist(tvShow, getAdapterPosition());
            });
            tvShowItemBinding.imageDelete.setVisibility(View.VISIBLE);

        }

    }

}
