package esw.learn.mvvmapplication.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import esw.learn.mvvmapplication.listeners.TvShowListener;
import esw.learn.mvvmapplication.models.TvShow;
import esw.learn.mvvmapplication.R;
import esw.learn.mvvmapplication.databinding.TvShowItemBinding;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>{

    private List<TvShow> tvShowList;
    private LayoutInflater layoutInflater;
    private TvShowListener listener;

    public TvShowAdapter(List<TvShow> tvShowList, TvShowListener listener) {
        this.tvShowList = tvShowList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        TvShowItemBinding tvShowBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.tv_show_item,
                parent,
                false
        );

        return new TvShowViewHolder(tvShowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        holder.bindTvShows(tvShowList.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShowList.size();
    }

    class TvShowViewHolder extends RecyclerView.ViewHolder{

        private TvShowItemBinding tvShowItemBinding;

        public TvShowViewHolder(TvShowItemBinding tvShowItemBinding){

            super(tvShowItemBinding.getRoot());
            this.tvShowItemBinding = tvShowItemBinding;

        }

        public void bindTvShows(TvShow tvShow){

            tvShowItemBinding.setTvShow(tvShow);
            tvShowItemBinding.executePendingBindings();
            tvShowItemBinding.getRoot().setOnClickListener(view -> {
                listener.onTvShowClicked(tvShow);
            });

        }

    }

}
