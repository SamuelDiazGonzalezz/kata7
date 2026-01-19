package software.ulpgc.Kata7.application.webservice;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import software.ulpgc.Kata7.application.DatabaseStore;
import software.ulpgc.Kata7.model.Movie;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final File database = new File("movies.db");
    public static void main(String[] args) {
        Javalin app = Javalin.create();
        app.get("/", Main::hello);
        app.get("/histogram/{from},{to}", Main::histogramJson);
        app.start(8080);
    }

    private static void histogramJson(@NotNull Context context) {
        int from;
        int to;

        try {
            from = Integer.parseInt(context.pathParam("from"));
            to = Integer.parseInt(context.pathParam("to"));
        } catch (NumberFormatException e) {
            context.status(400).result("Integer conversion error");
            return;
        }
        if(from > to) {
            context.status(400).result("Integer range error");
            return;
        }
        try {
            Stream<Movie> movies = new DatabaseStore(openConnection()).movies();
            movies =  moviesFilterByYear(movies, from, to);
            /*Histogram histogram = new HistogramBuilder(movies.toList()).x("Years")
                    .y("Frequency")
                    .title("Movies per year")
                    .build(Movie::releaseYear);
            */
            context.status(200);
            context.json(moviesToMap(movies, from, to));
        } catch (SQLException e) {
            context.status(400).result("Database connection error");
        }
    }

    private static Map<Integer, Long> moviesToMap(Stream<Movie> movies, int from, int to) {
        return movies.filter(m -> m.releaseYear() >= from)
                .filter(m -> m.releaseYear() < to)
                .collect(Collectors.groupingBy(Movie::releaseYear, Collectors.counting()));
    }


    private static Stream<Movie> moviesFilterByYear(Stream<Movie> movies, int from, int to) {
        return movies.filter(m -> m.releaseYear() >= from)
                .filter(m -> m.releaseYear() <= to);
    }

    private static void hello(@NotNull Context context) {
        context.status(200);
        context.result("Hello World!");
    }
    private static Connection openConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + database.getAbsolutePath());
        connection.setAutoCommit(false);
        return connection;
    }
}

