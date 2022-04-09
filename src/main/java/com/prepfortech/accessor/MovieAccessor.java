package com.prepfortech.accessor;

import com.prepfortech.accessor.models.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieAccessor {

    @Autowired
    private DataSource dataSource;

    public List<MovieDTO> getMovies() {
        List<MovieDTO> movies = new ArrayList<>();
        ResultSet rs = null;
        try(Connection connection = dataSource.getConnection()) {
            String sql = "SELECT title, tagline, budget from movie";
            PreparedStatement ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            System.out.println("fetchSize = " + rs.getFetchSize());
            while (rs.next()) {
                MovieDTO movieDTO = new MovieDTO(rs.getString(1), rs.getString(2));
                movies.add(movieDTO);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return movies;
    }
}
