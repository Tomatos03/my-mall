package com.mall.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/8/28
 */
@Service
public class EmailService implements IEmailService {
    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    /**
     * 发送邮件给指定邮箱
     *
     * @param receiveEmail 接收者邮箱
     * @param subject 标题
     * @param content 邮件内容
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/28 16:56
     */
    @Override
    public void sendEmail(String receiveEmail, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(senderEmail);
        simpleMailMessage.setTo(receiveEmail);

        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);

        mailSender.send(simpleMailMessage);
    }

    /**
     * 发送Html内容邮件给指定邮箱
     *
     * @param receiveEmail 接收者邮箱
     * @param subject 标题
     * @param content Html内容
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/28 16:56
     */
    @Override
    public void sendHtmlEmail(String receiveEmail, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(senderEmail);
        helper.setTo(receiveEmail);

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    /**
     * 发送附带附件的邮件给指定邮箱
     *
     * @param receiveEmail 接收者邮箱
     * @param subject 标题
     * @param content 邮件内容
     * @param filePathList 附件文件路径
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/28 16:56
     */
    @Override
    public void sendAttachmentsEmail(String receiveEmail, String subject, String content, List<String> filePathList) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(senderEmail);
        helper.setTo(receiveEmail);

        helper.setSubject(subject);
        helper.setText(content, true);


        //添加附件资源
        for (String item : filePathList) {
            FileSystemResource file = new FileSystemResource(new File(item));
            String fileName = item.substring(item.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
        }

        mailSender.send(message);
    }
}
