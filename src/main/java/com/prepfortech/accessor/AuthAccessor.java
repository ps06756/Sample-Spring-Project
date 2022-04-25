package com.prepfortech.accessor;

import com.prepfortech.accessor.models.AuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class AuthAccessor {

    @Autowired
    private DataSource dataSource;

    public AuthDTO findByToken(String token) {
        String sql = "SELECT authId, token, userId from auth where token = ?";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, token);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return new AuthDTO(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3));
            }
            return null;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean storeToken(String userId, String token) {
        String sql = "INSERT INTO auth values (?, ?, ?)";
        String uuid = UUID.randomUUID().toString();
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, uuid);
            pstmt.setString(2, token);
            pstmt.setString(3, userId);

            return pstmt.executeUpdate() == 1 ? true : false;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
