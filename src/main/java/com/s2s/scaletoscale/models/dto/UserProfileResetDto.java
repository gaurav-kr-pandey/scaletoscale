package com.s2s.scaletoscale.models.dto;

public class UserProfileResetDto {
    private String fpEmail;
    private String newPassword;
    private String otp;
    private String confirmNewPassword;

    public String getFpEmail() {
        return fpEmail;
    }

    public void setFpEmail(String fpEmail) {
        this.fpEmail = fpEmail;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
