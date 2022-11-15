package com.zeromax.users.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.zeromax.users.utils.Constants;
import org.springframework.stereotype.Service;

@Service
public class PhoneSenderService {

    public void sendSms(String phone, String link){
        Message.creator(new PhoneNumber(phone), new PhoneNumber(Constants.TWILIO_NUMBER), link).create();
    }
}
