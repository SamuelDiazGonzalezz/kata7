package software.ulpgc.Kata7.application.dbloader;

import software.ulpgc.Kata7.application.DatabaseRecorder;
import software.ulpgc.Kata7.application.DatabaseStore;
import software.ulpgc.Kata7.application.MainFrame;
import software.ulpgc.Kata7.io.RemoteMovieLoader;
import software.ulpgc.Kata7.model.Movie;
import software.ulpgc.Kata7.tasks.HistogramBuilder;
import software.ulpgc.Kata7.viewModel.Histogram;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Stream;

public class Main {
    private static File database = new File("movies.db");
    public static void main(String[] args) {
        try(Connection connection = openConnection()){
            importMoviesFromRemoteIfRequired(connection);
            Stream<Movie> movies = new DatabaseStore(connection).movies()
                    .filter(m -> m.releaseYear() > 1900)
                    .filter(m -> m.releaseYear() < 2050);
            Histogram histogram = new HistogramBuilder(movies.toList())
                    .title("Movies per year")
                    .x("Year")
                    .y("Frequency")
                    .legend("kata5 is2")
                    .build(Movie::releaseYear);
            MainFrame.create().display(histogram).setVisible(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void importMoviesFromRemoteIfRequired(Connection connection) throws SQLException {
        if(database.length() > 0) {return;}
        new DatabaseRecorder(connection).put(new RemoteMovieLoader().loadAll().stream());
        connection.commit();
    }

    private static Connection openConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + database.getAbsolutePath());
        connection.setAutoCommit(false);
        return connection;
    }
}
