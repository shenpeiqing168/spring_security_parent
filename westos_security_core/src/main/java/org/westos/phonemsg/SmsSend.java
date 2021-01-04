package org.westos.phonemsg;

public interface SmsSend {
    boolean sendSms(String mobile, String content);
}