package pl.edu.wat.service;

import lombok.Data;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Paweł Skrzypkowski
 * Wojskowa Akademia Techniczna im. Jarosława Dąbrowskiego, Warszawa 2018.
 */

@Service
public class EmailService {
    private static final String myEmail = "szpitalwat@gmail.com";
    static final String MESSAGE;

    static {
            MESSAGE = "Witaj w System rejestracji pacjentów w szpitalu.<br/>" +
                    "Aby się zalogować kliknij lub przekopiuj poniższy link:<br/>" +
                    "<a href='http://pawelskrzypkowski.pl/szpital/login'>Strona logowania</a>";
    }

    @Data
    public class Email{
        private String subject, to, text;

        private Session mailSession;

        public Email(String to, String subject, String text) {
            Properties emailProperties = System.getProperties();
            emailProperties.put("mail.smtp.port", "587");
            emailProperties.put("mail.smtp.auth", "true");
            emailProperties.put("mail.smtp.starttls.enable", "true");
            this.mailSession = Session.getDefaultInstance(emailProperties, null);
            this.subject = subject;
            this.to = to;
            this.text = text;
        }
    }

    public Email emailInstantiate(String to, String subject, String text){
        return new Email(to, subject, text);
    }

    public boolean checkCorrectness(Email email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(myEmail);
        if (m.matches() == false || email.subject == null || email.to == null
                || email.text == null || email.subject.equals("") || email.to.equals("") || email.text.equals(""))
            return false;
        return true;
    }

    public void send(String mail) {
            Email email = emailInstantiate(mail, "Rejestracja w systemie szpitala", EmailService.MESSAGE);
            if (checkCorrectness(email)) {
                sendEmail(email.mailSession, email.to, email.subject, email.text);
                System.out.println("Wiadomość została wysłana.");
            } else{
                System.out.println("Wysyłanie wiadomości niepowiodło się");
            }
    }

    private void sendEmail(Session session, String toEmail, String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(session);
            // set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(myEmail, "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse(myEmail, false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8", "html");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", myEmail, "szpitalwat123");
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("Email sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
