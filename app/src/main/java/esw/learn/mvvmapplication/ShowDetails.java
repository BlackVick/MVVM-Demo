package esw.learn.mvvmapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

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

    private void getTvShowDetails(){
        //set state
        activityShowDetailsBinding.setIsLoading(true);

        //get data
        String tvShowId = String.valueOf(getIntent().getIntExtra(Common.INTENT_SHOW_ID, -1));
        viewModel.getTvShowDetails(tvShowId).observe(this, detailsResponse -> {
            //set state
            activityShowDetailsBinding.setIsLoading(false);

            Toast.makeText(getApplicationContext(), detailsResponse.getTvShow().getUrl(), Toast.LENGTH_SHORT).show();
        });
    }
}