package com.prepfortech.accessor;

import com.prepfortech.accessor.model.EmailVerificationStatus;
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
import java.util.UUID;

@Repository
public class UserAccessor {
    @Autowired
    private DataSource dataSource;

    /** Gets the user based on his email, if user exists returns its UserDTO object else returns null */
    public UserDTO getUserByEmail(final String email) {
        String query = "SELECT userId, name, email, password, phoneNo, state, role, emailVerificationStatus from user where email = ?";
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
                        .emailVerificationStatus(EmailVerificationStatus.valueOf(resultSet.getString(8)))
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

    public void addNewUser(final String email, final String name, final String password, final String phoneNo,
                           final UserState userState, final UserRole userRole) {

        String insertQuery = "INSERT INTO user values (?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(insertQuery);
            pstmt.setString(1, UUID.randomUUID().toString());
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.setString(5, phoneNo);
            pstmt.setString(6, userState.name());
            pstmt.setString(7,  userRole.name());
            pstmt.execute();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }

    public UserDTO getUserByPhoneNo(final String phoneNo) {
        String query = "SELECT userId, name, email, password, phoneNo, state, role from user where phoneNo = ?";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, phoneNo);

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

    public void updateUserRole(final String userId, final UserRole updatedRole) {
        String query = "UPDATE user set role = ? where userId = ?";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, updatedRole.toString());
            pstmt.setString(2, userId);
            pstmt.executeUpdate();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }

    public void updateEmailVerificationStatus(final String userId, final EmailVerificationStatus newStatus) {
        String query = "UPDATE user set emailVerificationStatus = ? where userId = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, newStatus.toString());
            pstmt.setString(2, userId);
            pstmt.executeUpdate();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }
}
