package com.myweb.auth.captchaCode;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.gimpy.FishEyeGimpyRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Component
public class CaptChaService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static int verificationCodeExpiresTime = 5*60;
    private static int captchaWidth = 200;
    private static int captchaHeight = 60;

    public String getCaptchaVerificationCode(String uuid) {
        Captcha captcha = new Captcha.Builder(captchaWidth, captchaHeight)
                .addText().addBackground(new GradiatedBackgroundProducer())
                .gimp(new FishEyeGimpyRenderer())
                .build();
        redisTemplate.opsForValue().set(uuid, captcha.getAnswer(), verificationCodeExpiresTime, TimeUnit.SECONDS);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(captcha.getImage(), "png", Base64.getEncoder().wrap(outputStream));
            outputStream.close();
            return outputStream.toString("UTF-8");
        } catch (IOException e) {
            return null;
        }
    }


    public boolean verifyCode(String uuid , String inputCode) {
        String redisCodeValue = redisTemplate.opsForValue().get(uuid);
        if(redisCodeValue==null) {
            return false;
        }
        else if(!redisCodeValue.equals(inputCode.trim())) {
            return false;
        }
        else {
            redisTemplate.delete(redisCodeValue);
            return true;
        }
    }
}
