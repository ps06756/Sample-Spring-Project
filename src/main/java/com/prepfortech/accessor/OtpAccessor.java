package com.prepfortech.accessor;

import com.prepfortech.accessor.model.OtpDTO;
import com.prepfortech.accessor.model.OtpSentTo;
import com.prepfortech.accessor.model.OtpState;
import com.prepfortech.exceptions.DependencyFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

@Repository
public class OtpAccessor {

    @Autowired
    DataSource dataSource;

    public OtpDTO getUnsedOtp(final String userId, final String otp, final OtpSentTo otpSentTo) {
        String query = "SELECT * from otp where userId = ? and otp = ? and state = ? and sentTo = ?";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.setString(2, otp);
            pstmt.setString(3, OtpState.UNUSED.toString());
            pstmt.setString(4, otpSentTo.toString());

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return OtpDTO.builder()
                        .otpId(resultSet.getString(1))
                        .userId(userId)
                        .otp(otp)
                        .state(OtpState.UNUSED)
                        .createdAt(resultSet.getDate(5))
                        .sentTo(OtpSentTo.valueOf(resultSet.getString(6)))
                        .build();
            }
            else {
                return null;
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }

    public void updateOtpState(final String otpId, final OtpState otpState) {
        String query = "UPDATE otp set state = ? where otpId = ?";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, otpState.toString());
            pstmt.setString(2, otpId);
            pstmt.executeUpdate();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }

    public void addNewOtp(final String userId, final String otp, final OtpSentTo sentTo) {
        String query = "insert into otp values (?, ?, ?, ?, ?, ?)";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, UUID.randomUUID().toString());
            pstmt.setString(2, userId);
            pstmt.setString(3, otp);
            pstmt.setString(4, OtpState.UNUSED.name());
            pstmt.setDate(5, new Date(System.currentTimeMillis()));
            pstmt.setString(6, sentTo.name());
            pstmt.execute();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }
}
