package com.zeromax.users.security;

import com.twilio.Twilio;
import com.zeromax.users.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class TwilioInitializer {

    @Autowired
    public  TwilioInitializer(){
        Twilio.init(Constants.TWILIO_SID, Constants.TWILIO_TOKEN);
        log.info("twilio init");
    }
}
