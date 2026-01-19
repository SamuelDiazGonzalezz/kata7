package software.ulpgc.Kata7.io;

import software.ulpgc.Kata7.model.Movie;

import java.util.stream.Stream;

public interface Store {
    Stream<Movie> movies();
}
