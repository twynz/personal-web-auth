package com.myweb.auth.rest;


import com.myweb.auth.captchaCode.CaptChaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import java.util.UUID;

@RestController
@RequestMapping("/captcha")
public class CaptchaResource {

    @Autowired
    private CaptChaService captChaService;

    @RequestMapping(value = "/getCodePic", method = RequestMethod.GET, consumes = "application/json",
            produces = "application/json")
    @Description("Generate Captcha Pic.")
    public VerficationDTO getCaptchaPic() {
        UUID imageId = UUID.randomUUID();
        String imagePicEncode = captChaService.getCaptchaVerificationCode(imageId.toString());
        VerficationDTO verficationDTO = new VerficationDTO(imageId.toString(),imagePicEncode);
        return verficationDTO;
    }

    @RequestMapping(value = "/verifyCode", method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    @Description("Verify Captcha Code.")
    public Boolean verifyCode(String imageId, String code) {
        return captChaService.verifyCode(imageId,code);
    }

    class VerficationDTO {

        public VerficationDTO(String imageId, String imageCode) {
            this.imageId = imageId;
            this.imageCode = imageCode;
        }

        public String imageId;

        public String imageCode;

        public String getImageId() {
            return imageId;
        }

        public void setImageId(String imageId) {
            this.imageId = imageId;
        }

        public String getImageCode() {
            return imageCode;
        }

        public void setImageCode(String imageCode) {
            this.imageCode = imageCode;
        }
    }

}
