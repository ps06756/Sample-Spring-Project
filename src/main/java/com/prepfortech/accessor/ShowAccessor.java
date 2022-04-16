package com.prepfortech.accessor;

import com.prepfortech.accessor.models.PreferredAudience;
import com.prepfortech.accessor.models.ShowDTO;
import com.prepfortech.accessor.models.ShowGenre;
import com.prepfortech.accessor.models.ShowType;
import com.prepfortech.exception.DependencyFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ShowAccessor {

    @Autowired
    private DataSource dataSource;

    public List<ShowDTO> findByName(String name) {
        String query = "SELECT * from shows where name like '%" + name + "%' ";
        List<ShowDTO> listOfShows = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, name);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ShowDTO showDTO = new ShowDTO();
                showDTO.setShowId(resultSet.getString(1));
                showDTO.setShowName(resultSet.getString(2));
                showDTO.setShowType(ShowType.valueOf(resultSet.getString(3)));
                showDTO.setGenre(ShowGenre.valueOf(resultSet.getString(4)));
                showDTO.setAudience(PreferredAudience.valueOf(resultSet.getString(5)));
                showDTO.setAverageRating(resultSet.getDouble(6));
                showDTO.setTotalLength(resultSet.getInt(7));
                listOfShows.add(showDTO);
            }
            return listOfShows;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }
}
