/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package until;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import model.Account;

public class MailSender extends Thread {

    static final List<MimeMessage> queue = new ArrayList<>();
    public static boolean isSend = false;
    public static int count = 0, total = 0;

    static {
        MailSender sender = new MailSender();
        sender.start();
    }

    public void startProgress(Account acc, String title, String content, File... files) {
        List<Account> list = new ArrayList<>();
        list.add(acc);
        startProgress(list, title, content, files);
    }

    public void startProgress(List<Account> list, String title, String content, File... files) {
        if(Variable.IS_SENDING){
            Dialog.showMessageDialog("", "You cannot start new sending progress because another sending progress still running!");
            return;
        }
        total = list.size();
        count = 0;

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (Account acc : list) {
                    MailSender.send(title, content, acc.getEmail(), files);
                    count++;
                    updateProgress(count, total);
                    Platform.runLater(() -> {
                        Variable.SENDING_LABEL.setText("SENDING..." + count + "/" + total);
                    });
                    if (!Variable.IS_SENDING) {
                        break;
                    }

                }
                return null;
            }

            @Override
            protected void cancelled() {
                if (Auth.isManager()) {
                    Dialog.showMessageDialog("", "Email sending progress has been canceled");
                    Variable.MAIN_CONTROLLER.showSendingProgress(false);
                }
                Variable.IS_SENDING = false;
            }

            @Override
            protected void failed() {
                if (Auth.isManager()) {
                    Dialog.showMessageDialog("", "Email sending progress has failed");
                    Variable.MAIN_CONTROLLER.showSendingProgress(false);

                }
                Variable.IS_SENDING = false;
            }

            @Override
            protected void succeeded() {
                if (Auth.isManager()) {
                    if (Variable.IS_SENDING) {
                        Dialog.showMessageDialog("", "All your email has been sent");
                    }
                    Variable.MAIN_CONTROLLER.showSendingProgress(false);
                }
                Variable.IS_SENDING = false;
            }

        };
        Variable.SENDING_PROGRESSBAR.progressProperty().bind(task.progressProperty());
        Thread t = new Thread(task);
        t.setDaemon(true);
        Variable.THREAD_SENDING=t;
        t.start();
        
        Variable.IS_SENDING = true;
        if (Auth.isManager()) {
            Variable.MAIN_CONTROLLER.showSendingProgress(true);
        }
        Variable.SENDING_LABEL.setText("SENDING...");
    }

    public static void send(String title, String mess, String list, File... files) {
        try {
            Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", 587);

            String accountName = Value.DEFAULT_EMAIL;
            String accountPassword = Value.DEFAULT_PASSWORD;
            Session s = Session.getInstance(p,
                    new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(accountName, accountPassword);
                }
            });

            String from = Value.DEFAULT_EMAIL;
            String subject = title;
            String body = mess;
            MimeMessage msg = new MimeMessage(s);
            msg.setContent(body, "text/html; charset=UTF-8");
            msg.setFrom(new InternetAddress("GameStore "));
            if (list.indexOf(",") != list.lastIndexOf(",")) {
                msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(list));
            } else {
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(list));
            }
            msg.setSubject(subject);

            MimeMultipart multipart = new MimeMultipart();

            MimeBodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(body, "text/html; charset=UTF-8");
            multipart.addBodyPart(contentPart);

            if (files.length == 0) {
                MimeBodyPart filePart = new MimeBodyPart();
                for (File file : files) {
                    FileDataSource fds = new FileDataSource(file);
                    filePart.setDataHandler(new DataHandler(fds));
                    filePart.setFileName(file.getName());
                    multipart.addBodyPart(filePart);
                }
            }

            msg.setContent(multipart);
            MailSender.queue(msg);
        } catch (MessagingException e) {

        }
    }

    public static void queue(MimeMessage mail) {
        synchronized (queue) {
            queue.add(mail);
            queue.notify();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (queue) {
                    isSend = false;
                    if (queue.size() > 0) {
                        try {
                            MimeMessage mail = queue.remove(0);
                            Transport.send(mail);
                            System.out.println("SENT");
                        } catch (MessagingException e) {
                            System.out.println("CANNOT SEND");
                        }
                    } else {
                        queue.wait();
                    }
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
