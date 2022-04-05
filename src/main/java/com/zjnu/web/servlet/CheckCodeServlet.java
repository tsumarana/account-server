package com.zjnu.web.servlet;

import com.alibaba.fastjson.JSON;
import com.zjnu.pojo.Verify;
import com.zjnu.util.CheckCodeUtil;
import org.apache.axis.encoding.Base64;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class CheckCodeServlet {

    @RequestMapping("/checkCodeServlet")
    public void checkCodeServlet(HttpServletRequest req ,HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Verify verify = CheckCodeUtil.outputVerifyImage(100, 50, outputStream, 4);
        BufferedImage image = verify.getImage();
        ImageIO.write(image, "png", outputStream);
        String encode = Base64.encode(outputStream.toByteArray());
        encode ="data:image/jpg;base64,"+encode;
        verify.setOp(encode);
        verify.setImage(null);
        String checkcode = verify.getVerifyCode();
        System.out.println(checkcode);
        session.setAttribute("checkCode",checkcode);
        String jsonString = JSON.toJSONString(verify);
        resp.getWriter().write(jsonString);
    }
}
