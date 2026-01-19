package software.ulpgc.Kata7.io;

import software.ulpgc.Kata7.model.Movie;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class RemoteMovieLoader implements MovieLoader{
    private static final String url = "https://datasets.imdbws.com/title.basics.tsv.gz";
    @Override
    public List<Movie> loadAll() {
        try {
            return loadFrom(new URL(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Movie> loadFrom(URL url) throws IOException {
        return loadFrom(url.openConnection());
    }

    private List<Movie> loadFrom(URLConnection urlConnection) {
        try (InputStream inputStream = decompress(urlConnection.getInputStream())) {
            return loadFrom(toReader(inputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Movie> loadFrom(BufferedReader reader) throws IOException {
        List<Movie> movies = new ArrayList<>();
        TsvMovieParser tsvMovieParser = new TsvMovieParser();
        reader.readLine();
        while(reader.ready()){
            String line = reader.readLine();
            movies.add(tsvMovieParser.toMovie(line));
        }
        return movies;
    }

    private BufferedReader toReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private InputStream decompress(InputStream inputStream) throws IOException {
        return new GZIPInputStream(new BufferedInputStream(inputStream));
    }
}
