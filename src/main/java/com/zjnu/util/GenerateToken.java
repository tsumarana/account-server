package com.zjnu.util;

import com.zjnu.pojo.LoginBean;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

public class GenerateToken {

    private long time = 1000*60*60*24;
    private String signature = "lixh";
    public String generate(String id,String userid, String username,String role){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //添加头
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //payload
                .claim("id",id)
                .claim("userid",userid)
                .claim("username",username)
                .claim("role",role)
                //有效时间
                .setExpiration(new Date(System.currentTimeMillis()+time))
                .setId(UUID.randomUUID().toString())
                //signature
                .signWith(SignatureAlgorithm.HS256,signature)
                //拼接
                .compact();
        System.out.println(jwtToken);
        return jwtToken;
    }
    //校验token
    public LoginBean verify(String token){
        LoginBean loginBean  = new LoginBean();
        JwtParser jwtParser = Jwts.parser();
        if(token == null || token.equals("null")) {
            loginBean.setRole("204");
        }else {
            try {
                Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
                Claims body = claimsJws.getBody();
                String username = String.valueOf(body.get("username"));
                String _id = (String) body.get("userid");
                String role = (String) body.get("role");

                if(role.equals("1")){
                    role = "201";
                }else{
                    role = "200";
                }
                int id = Integer.parseInt(_id);
                long time = body.getExpiration().getTime();
                long nowTime = new Date().getTime();
                if (nowTime - time < 0) {
                    if (username != null) {
                        loginBean.setUsername(username);
                        loginBean.setId(id);
                        loginBean.setRole(role);
                    }else{
                        System.out.println("token过期");
                    }
                }
            }catch (Exception e){
                loginBean.setRole("401");
                System.out.println("校验出错");
            }
        }
        return loginBean;
    }


}
