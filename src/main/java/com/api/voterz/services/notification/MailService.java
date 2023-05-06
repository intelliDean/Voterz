package com.api.voterz.services.notification;

import com.api.voterz.data.dtos.requests.EmailRequest;

public interface MailService {
    String sendHTMLMail(EmailRequest request);
}
