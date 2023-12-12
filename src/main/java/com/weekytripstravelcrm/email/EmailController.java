package com.weekytripstravelcrm.email;

import com.weekytripstravelcrm.email.MailRequest;
import com.weekytripstravelcrm.email.MailResponse;
import com.weekytripstravelcrm.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public MailResponse sendEmail(@RequestBody MailRequest request){
        Map<String,Object> model = new HashMap<>();
        model.put("Name", request.getName());
        model.put("To", request.getTo());
        model.put("From", request.getFrom());
        model.put("Subject",request.getSubject());

        return emailService.sendEmail(request, model);
    }
}
