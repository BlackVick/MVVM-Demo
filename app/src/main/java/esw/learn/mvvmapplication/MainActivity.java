package esw.learn.mvvmapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import esw.learn.mvvmapplication.ViewModels.TvShowViewModel;

public class MainActivity extends AppCompatActivity {

    //view model
    private TvShowViewModel tvShowViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view model
        tvShowViewModel = new ViewModelProvider(this).get(TvShowViewModel.class);

        //get tv shows
        getTvShows();
    }

    private void getTvShows(){
        tvShowViewModel.getTvShows(0).observe(this, tvResponse -> {

            //print total shows
            Toast.makeText(getApplicationContext(), "Total Pages: " + tvResponse.getPages(), Toast.LENGTH_SHORT).show();

        });
    }
}