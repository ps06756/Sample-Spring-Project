package com.prepfortech.accessor;

import com.prepfortech.accessor.model.ShowAudience;
import com.prepfortech.accessor.model.ShowDTO;
import com.prepfortech.accessor.model.ShowGenre;
import com.prepfortech.accessor.model.ShowType;
import com.prepfortech.exceptions.DependencyFailureException;
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
public class ShowAccessor {

    @Autowired
    DataSource dataSource;

    public List<ShowDTO> getShowsByName(final String showName) {
        String query = "SELECT showId, name, typeOfShow, genre, audience, rating, length, thumbnailPath from shows where "
                + " name like CONCAT('%', ?, '%')";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, showName);
            ResultSet resultSet = pstmt.executeQuery();
            List<ShowDTO> showList = new ArrayList<>();
            while (resultSet.next()) {
                ShowDTO showDTO = ShowDTO.builder()
                        .showId(resultSet.getString(1))
                        .name(resultSet.getString(2))
                        .typeOfShow(ShowType.valueOf(resultSet.getString(3)))
                        .genre(ShowGenre.valueOf(resultSet.getString(4)))
                        .audience(ShowAudience.valueOf(resultSet.getString(5)))
                        .rating(resultSet.getDouble(6))
                        .length(resultSet.getInt(7))
                        .thumbnailPath(resultSet.getString(8))
                        .build();
                showList.add(showDTO);
            }
            return showList;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }
}
