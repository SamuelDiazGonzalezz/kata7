package software.ulpgc.Kata7.application.remoteloader;

import software.ulpgc.Kata7.application.MainFrame;
import software.ulpgc.Kata7.io.RemoteMovieLoader;
import software.ulpgc.Kata7.model.Movie;
import software.ulpgc.Kata7.tasks.HistogramBuilder;
import software.ulpgc.Kata7.viewModel.Histogram;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        RemoteMovieLoader remoteMovieLoader = new RemoteMovieLoader();
        List<Movie> movies = remoteMovieLoader.loadAll();
        movies = movies.stream()
                .filter(m -> m.releaseYear() > 1900)
                .filter(m -> m.releaseYear() < 2050).toList();
        Histogram histogram = new HistogramBuilder(movies)
                .title("movies per year")
                .x("year")
                .y("frequency")
                .build(Movie::releaseYear);


        MainFrame.create()
                .display(histogram)
                .setVisible(true);
    }
}
