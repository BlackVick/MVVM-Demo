package esw.learn.mvvmapplication.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import esw.learn.mvvmapplication.R;
import esw.learn.mvvmapplication.databinding.ItemContainerEpisodeBinding;
import esw.learn.mvvmapplication.databinding.ItemContainerSliderImageBinding;
import esw.learn.mvvmapplication.models.EpisodeData;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder>{

    private List<EpisodeData> episodes;
    private LayoutInflater layoutInflater;

    public EpisodesAdapter(List<EpisodeData> episodes) {
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ItemContainerEpisodeBinding episodeBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_container_episode,
                parent,
                false
        );
        return new EpisodeViewHolder(episodeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        holder.bindEpisode(episodes.get(position));
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    static class EpisodeViewHolder extends RecyclerView.ViewHolder{

        private ItemContainerEpisodeBinding itemContainerEpisodeBinding;

        public EpisodeViewHolder(ItemContainerEpisodeBinding itemContainerEpisodeBinding){
            super(itemContainerEpisodeBinding.getRoot());

            this.itemContainerEpisodeBinding = itemContainerEpisodeBinding;
        }

        public void bindEpisode(EpisodeData episode){

            String title = "S";
            String season = episode.getSeason();

            if (season.length() == 1){
                season = "0".concat(season);
            }

            String episodeNumber = episode.getEpisode();
            if (episodeNumber.length() == 1){
                episodeNumber = "0".concat(episodeNumber);
            }
            episodeNumber = "E".concat(episodeNumber);
            title = title.concat(season).concat(episodeNumber);

            itemContainerEpisodeBinding.setTitle(title);
            itemContainerEpisodeBinding.setName(episode.getName());
            itemContainerEpisodeBinding.setAirDate(episode.getAir_date());



        }

    }

}
