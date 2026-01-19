package software.ulpgc.Kata7.io;

import software.ulpgc.Kata7.model.Movie;

public class TsvMovieParser implements MovieParser{
    @Override
    public String[] parse(String s) {
        return s.split("\\t");
    }

    public Movie toMovie(String line) {
        String[] splits = parse(line);
        return new Movie(
                splits[2],
                toInt(splits[5]),
                toInt(splits[7])
        );
    }

    private int toInt(String split) {
        if(split.equals("\\N")) return -1;
        return Integer.parseInt(split);
    }
}
