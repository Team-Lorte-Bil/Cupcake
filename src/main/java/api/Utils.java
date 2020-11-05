package api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Utils {
    private Utils() {
        throw new IllegalStateException("Utility class!");
    }
    
    /**
     * @param src String to be encoded
     * @return Encoded String
     */
    public static String encodeHtml(String src) {
        /*
         * Tak https://www.tutorialspoint.com/how-to-remove-the-html-tags-from-a-given-string-in-java
         */
        return src.replaceAll("\"[^\"]*+\"", "");
    }
    
    
    /**
     * A utility class for sending e-mail messages
     * @author www.codejava.net
     *
     */
    public static void sendEmail(String toAddress, String subject, String message) throws AddressException,
            MessagingException, UnsupportedEncodingException {
    
        // sets SMTP server properties
        Properties properties = new Properties();
    
        try (InputStream input = Cupcake.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException ex) {
        ex.printStackTrace();
        }
    
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("USERNAME"), properties.getProperty("PASSWORD"));
            }
        };
    
        Session session = Session.getInstance(properties, auth);
    
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
    
        msg.setFrom(new InternetAddress(properties.getProperty("SENT_FROM"), properties.getProperty("SENT_FROM_NAME")));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        // set plain text message
        msg.setContent(message, "text/html");
    
        // sends the e-mail
        Transport.send(msg);
    }
    
    public static void main(String[] args) {
        try {
            sendEmail("emil@evsn.dk", "test mail", "test besked");
        } catch (UnsupportedEncodingException | MessagingException e){
            System.out.println(e.getMessage());
        }
    }
    
}
