package com.prepfortech.accessor;

import com.prepfortech.accessor.model.UserDTO;
import com.prepfortech.accessor.model.UserRole;
import com.prepfortech.accessor.model.UserState;
import com.prepfortech.exceptions.DependencyFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserAccessor {
    @Autowired
    private DataSource dataSource;

    /** Gets the user based on his email, if user exists returns its UserDTO object else returns null */
    public UserDTO getUserByEmail(final String email) {
        String query = "SELECT userId, name, email, password, phoneNo, state, role from user where email = ?";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, email);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                UserDTO userDTO = UserDTO.builder()
                        .userId(resultSet.getString(1))
                        .name(resultSet.getString(2))
                        .email(resultSet.getString(3))
                        .password(resultSet.getString(4))
                        .phoneNo(resultSet.getString(5))
                        .state(UserState.valueOf(resultSet.getString(6)))
                        .role(UserRole.valueOf(resultSet.getString(7)))
                        .build();
                return userDTO;
            }
            return null;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }
}
