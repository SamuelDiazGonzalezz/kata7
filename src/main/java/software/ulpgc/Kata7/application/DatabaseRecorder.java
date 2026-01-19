package software.ulpgc.Kata7.application;

import software.ulpgc.Kata7.io.Recorder;
import software.ulpgc.Kata7.model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Stream;

public class DatabaseRecorder implements Recorder {
    private final Connection connection;
    private final PreparedStatement statement;

    public DatabaseRecorder(Connection connection) throws SQLException {
        this.connection = connection;
        createTableIfNotExits();
        statement = connection.prepareStatement("INSERT INTO movies (title, releaseYear, duration) VALUES (?, ?, ?)");
    }


    @Override
    public void put(Stream<Movie> movies) {
        movies.forEach(this::record);

    }

    private void record(Movie movie) {
        try{
            write(movie);
            executeBatchIfRequired();
            connection.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int count = 0;
    private void executeBatchIfRequired() throws SQLException {
        if(++count % 10_000 == 0) statement.executeBatch();
    }
    private void write(Movie movie) throws SQLException {
        statement.setString(1, movie.title());
        statement.setInt(2, movie.releaseYear());
        statement.setInt(3, movie.duration());
        statement.addBatch();
    }

    private void createTableIfNotExits() throws SQLException {
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS movies(title TEXT, releaseYear INTEGER, duration INTEGER)");
    }
}
