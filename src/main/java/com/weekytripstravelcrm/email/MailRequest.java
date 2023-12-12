package com.weekytripstravelcrm.email;

import freemarker.template.Template;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailRequest {
    private String name;
    private String to;
    private String from;
    private String subject;
    private String templateName;
}
