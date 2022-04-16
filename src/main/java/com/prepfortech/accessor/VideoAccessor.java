package com.prepfortech.accessor;

import com.prepfortech.accessor.models.VideoDTO;
import com.prepfortech.exception.DependencyFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class VideoAccessor {

    @Autowired
    private DataSource dataSource;

    public VideoDTO findById(String videoId) {
        String query = "SELECT * from video where videoId = ?";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, videoId);
            ResultSet resultSet = pstmt.executeQuery();
            VideoDTO videoDTO = null;
            if (resultSet.next()) {
                videoDTO = new VideoDTO();
                videoDTO.setVideoId(resultSet.getString(1));
                videoDTO.setName(resultSet.getString(2));
                videoDTO.setSeriesId(resultSet.getString(3));
                videoDTO.setShowId(resultSet.getString(4));
                videoDTO.setRating(resultSet.getDouble(5));
                videoDTO.setTotalLength(resultSet.getInt(7));
            }
            return videoDTO;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }

    public boolean updateVideoRating(String videoId, double newRating) {
        String query = "UPDATE video set rating = ? where videoId = ?";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setDouble(1, newRating);
            pstmt.setString(2, videoId);
            return (pstmt.executeUpdate() == 1 ? true : false);
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }
}
