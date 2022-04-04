package com.prepfortech.accessor;

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

    public List<String> getMovies() {

        List<String> movies = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            String sql = "SELECT title from movie";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                movies.add(rs.getString(1));
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return movies;
    }
}
