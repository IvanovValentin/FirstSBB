package com.javaschool.ivanov.Service;


import com.javaschool.ivanov.DAO.UserDao;
import com.javaschool.ivanov.Domain.User;
import com.javaschool.ivanov.Exception.IncorrectDataException;
import com.javaschool.ivanov.Exception.ObjectExistException;
import com.javaschool.ivanov.Security.MD5;
import com.javaschool.ivanov.Security.PasswordGenerator;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Stateless
public class UserService {

    private final static Logger log = Logger.getLogger(UserService.class);
    private static final int CUSTOMER_STATUS = 1;

    @EJB
    private UserDao userDao;


    /**
     * create user in data base
     * @param login - user login
     * @param password - user password
     * @throws ObjectExistException - if this user already exist in data base
     */

    public void registration(String login, String password) throws ObjectExistException
    {
        log.info(login+" Start 'registration' method.");
            if (userDao.find(login) == null) {
                userDao.create(new User(login, password, CUSTOMER_STATUS));
            }
            else throw new ObjectExistException();

    }

    /**
     * @param login - user login
     * @param password - user password
     * @return - access level
     * @throws IncorrectDataException
     */
    public int authorization(String login, String password) throws IncorrectDataException
    {
        log.info(login+" Start 'authorization' method.");
            User user = userDao.find(login);
        if(user != null) {
            if (password.equals(user.getPassword())) {
                    return user.getAccessLevel();
            } else throw new IncorrectDataException();

        }
        else throw new IncorrectDataException();
    }


    public void sendPassword(String email) throws IncorrectDataException, MessagingException
    {
        log.info(email+" Start 'sendPassword' method.");
        User user = userDao.find(email);
        if(user != null) {
            String password = PasswordGenerator.generate();
            String passwordMD5 = MD5.getHash(password);
            user.setPassword(passwordMD5);
            userDao.update(user);
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("sbb.spw","XaxeLL21");
                        }
                    });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sbb.spw@sbb.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Восстановление пароля");
            message.setText("Здравствуйте," +
                    "\n\n Вы (или кто-то еще) запросили изменение пароля." +
                    "\n     Ваш новый пароль : " + password + "" +
                    "\n Если вы не запрашивали изменение пароля - проигнорируйте это сообщение");

            Transport.send(message);
        } else throw new IncorrectDataException();
    }
}
