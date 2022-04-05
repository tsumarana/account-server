package com.zjnu.pojo;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

public class Verify {
    OutputStream os;
    String verifyCode;
    BufferedImage image;
    String op;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Verify{" +
                "os=" + os +
                ", verifyCode='" + verifyCode + '\'' +
                ", image=" + image +
                '}';
    }

    public Verify(OutputStream os, String verifyCode, BufferedImage image) {
        this.os = os;
        this.verifyCode = verifyCode;
        this.image = image;
    }

    public Verify() {
    }

    public Verify(OutputStream os, String verifyCode) {
        this.os = os;
        this.verifyCode = verifyCode;
    }

    public OutputStream getOs() {
        return os;
    }

    public void setOs(OutputStream os) {
        this.os = os;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

}
