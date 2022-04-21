package com.zjnu.web.servlet;

import com.alibaba.fastjson.JSON;
import com.zjnu.pojo.Verify;
import com.zjnu.util.CheckCodeUtil;
import org.apache.axis.encoding.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
public class CheckCodeServlet {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @RequestMapping("/checkCodeServlet")
    public void checkCodeServlet(HttpServletRequest req ,HttpServletResponse resp) throws IOException {
        String id = req.getSession().getId();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Verify verify = CheckCodeUtil.outputVerifyImage(100, 50, outputStream, 4);
        BufferedImage image = verify.getImage();
        ImageIO.write(image, "png", outputStream);
        String encode = Base64.encode(outputStream.toByteArray());
        encode ="data:image/jpg;base64,"+encode;
        verify.setOp(encode);
        verify.setImage(null);
        String checkcode = verify.getVerifyCode();
        id = id + ":" + checkcode;
        stringRedisTemplate.opsForValue().set(id,checkcode,180, TimeUnit.SECONDS);
        String jsonString = JSON.toJSONString(verify);
        resp.getWriter().write(jsonString);
    }
}
