package com.prepfortech.accessor;

import com.prepfortech.accessor.model.ProfileType;
import com.prepfortech.exceptions.DependencyFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class ProfileAccessor {

    @Autowired
    private DataSource dataSource;

    public void addNewProfile(final String userId, final String name, final ProfileType type) {
        String sql = "INSERT INTO profile values (?, ?, ?, ?, ?)";

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, UUID.randomUUID().toString());
            pstmt.setString(2, name);
            pstmt.setString(3, type.toString());
            pstmt.setDate(4, new Date(System.currentTimeMillis()));
            pstmt.setString(5, userId);
            pstmt.execute();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }
}
