package software.ulpgc.Kata7.tasks;

import software.ulpgc.Kata7.model.Movie;
import software.ulpgc.Kata7.viewModel.Histogram;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class HistogramBuilder {
    private final List<Movie> movies;
    private final Map<String, String> labels;
    public HistogramBuilder(List<Movie> movies) {
        this.movies = movies;
        labels = new HashMap<>();
    }

    public Histogram build(Function<Movie, Integer> transform){
        Histogram histogram = new Histogram(labels);
        for (Movie movie: movies){
            histogram.put(transform.apply(movie));
        }
        return histogram;
    }

    public HistogramBuilder title(String label){
        labels.put("title", label);
        return this;
    }
    public HistogramBuilder x(String label) {
        labels.put("x", label);
        return this;
    }
    public HistogramBuilder y(String label) {
        labels.put("y", label);
        return this;
    }
    public HistogramBuilder legend(String label){
        labels.put("legend", label);
        return this;
    }
}
