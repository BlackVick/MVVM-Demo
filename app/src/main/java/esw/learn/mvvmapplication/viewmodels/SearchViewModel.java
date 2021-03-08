package esw.learn.mvvmapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import esw.learn.mvvmapplication.api.TvSearchResponse;
import esw.learn.mvvmapplication.repositories.SearchTvShowsRepository;

public class SearchViewModel extends ViewModel {

    private SearchTvShowsRepository repository;

    public SearchViewModel(){
        repository = new SearchTvShowsRepository();
    }

    public LiveData<TvSearchResponse> searchTvShow(String query, int page){
        return repository.searchTvShow(query, page);
    }

}
