package br.com.instore.web.tools;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMail {

    private static JavaMail instance;
    private Properties prop = new Properties();
    private Session session = null;
    private String email = "teste@instore.com.br";
    private String senha = "instore321";
    private JavaMail() {
        
        

        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.user", email);
        prop.put("mail.smtp.password", senha);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(prop, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, senha);
            }
        });

        session.setDebug(true);
    }

    public void _send(String title , String text , String emails) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(email));
            Address[] address = InternetAddress.parse(emails);
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(title);
            message.setContent(text, "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static JavaMail getInstance() {
        if (null == instance) {
            instance = new JavaMail();
        }
        return instance;
    }

    public static void send(String title , String text  , String emails) {
        getInstance()._send(title , text , emails);
    }
    
    public static void main(String[] args) {
        JavaMail.send("lala", "alalalala", "alex.goncalves@instore.com.br");
    }
}
