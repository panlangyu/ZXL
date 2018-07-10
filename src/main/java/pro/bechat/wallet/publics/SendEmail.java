package pro.bechat.wallet.publics;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.*;

/**
 * create SendEmail by huc
 * 2018/7/9  上午11:18
 */
public class SendEmail {

    private static Session session;
    private static String  user;

    private MimeMessage msg;
    private String      text;
    private String      html;
    private List<MimeBodyPart> attachments = new ArrayList<MimeBodyPart>();

    private SendEmail() {
    }

    public static Properties defaultConfig(Boolean debug) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.debug", "false");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.debug", null != debug ? debug.toString() : "false");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.port", "25");
        return props;
    }

    /**
     * smtp entnterprise qq
     *
     * @param debug
     * @return
     */
    public static Properties SMTP_ENT_QQ(boolean debug) {
        Properties props = defaultConfig(debug);
        props.put("mail.smtp.host", "smtp.exmail.qq.com");
        return props;
    }

    /**
     * smtp qq
     *
     * @param debug enable debug
     * @return
     */
    public static Properties SMTP_QQ(boolean debug) {
        Properties props = defaultConfig(debug);
        props.put("mail.smtp.host", "smtp.qq.com");
        return props;
    }

    /**
     * smtp 163
     *
     * @param debug enable debug
     * @return
     */
    public static Properties SMTP_163(Boolean debug) {
        Properties props = defaultConfig(debug);
        props.put("mail.smtp.host", "smtp.163.com");
        return props;
    }

    /**
     * config username and password
     *
     * @param props    email property config
     * @param username email auth username
     * @param password email auth password
     */
    public static void config(Properties props, final String username, final String password) {
        props.setProperty("username", username);
        props.setProperty("password", password);
        config(props);
    }

    public static void config(Properties props) {
        final String username = props.getProperty("username");
        final String password = props.getProperty("password");
        user = username;
        session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    /**
     * set email subject
     *
     * @param subject subject title
     * @return
     * @throws MessagingException
     */
    public static SendEmail subject(String subject) throws MessagingException {
        SendEmail ohMyEmail = new SendEmail();
        ohMyEmail.msg = new MimeMessage(session);
        ohMyEmail.msg.setSubject(subject, "UTF-8");
        return ohMyEmail;
    }

    /**
     * set email from
     *
     * @param nickName from nickname
     * @return
     * @throws MessagingException
     */
    public SendEmail from(String nickName) throws MessagingException {
        return from(nickName, user);
    }

    /**
     * set email nickname and from user
     *
     * @param nickName
     * @param from
     * @return
     * @throws MessagingException
     */
    public SendEmail from(String nickName, String from) throws MessagingException {
        try {
            nickName = MimeUtility.encodeText(nickName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        msg.setFrom(new InternetAddress(nickName + " <" + from + ">"));
        return this;
    }

    public SendEmail replyTo(String... replyTo) throws MessagingException {
        String result = Arrays.asList(replyTo).toString().replaceAll("(^\\[|\\]$)", "").replace(", ", ",");
        msg.setReplyTo(InternetAddress.parse(result));
        return this;
    }

    public SendEmail replyTo(String replyTo) throws MessagingException {
        msg.setReplyTo(InternetAddress.parse(replyTo.replace(";", ",")));
        return this;
    }

    public SendEmail to(String... to) throws Exception {
        return addRecipients(to, Message.RecipientType.TO);
    }

    public SendEmail to(String to) throws MessagingException {
        return addRecipient(to, Message.RecipientType.TO);
    }

    public SendEmail cc(String... cc) throws MessagingException {
        return addRecipients(cc, Message.RecipientType.CC);
    }

    public SendEmail cc(String cc) throws MessagingException {
        return addRecipient(cc, Message.RecipientType.CC);
    }

    public SendEmail bcc(String... bcc) throws MessagingException {
        return addRecipients(bcc, Message.RecipientType.BCC);
    }

    public SendEmail bcc(String bcc) throws MessagingException {
        return addRecipient(bcc, Message.RecipientType.BCC);
    }

    private SendEmail addRecipients(String[] recipients, Message.RecipientType type) throws MessagingException {
        String result = Arrays.asList(recipients).toString().replace("(^\\[|\\]$)", "").replace(", ", ",");
        msg.setRecipients(type, InternetAddress.parse(result));
        return this;
    }

    private SendEmail addRecipient(String recipient, Message.RecipientType type) throws MessagingException {
        msg.setRecipients(type, InternetAddress.parse(recipient.replace(";", ",")));
        return this;
    }

    public SendEmail text(String text) {
        this.text = text;
        return this;
    }

    public SendEmail html(String html) {
        this.html = html;
        return this;
    }

    public SendEmail attach(File file) throws MessagingException {
        attachments.add(createAttachment(file, null));
        return this;
    }

    public SendEmail attach(File file, String fileName) throws MessagingException {
        attachments.add(createAttachment(file, fileName));
        return this;
    }

    private MimeBodyPart createAttachment(File file, String fileName) throws MessagingException {
        MimeBodyPart   attachmentPart = new MimeBodyPart();
        FileDataSource fds            = new FileDataSource(file);
        attachmentPart.setDataHandler(new DataHandler(fds));
        try {
            attachmentPart.setFileName(null == fileName ? MimeUtility.encodeText(fds.getName()) : MimeUtility.encodeText(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attachmentPart;
    }

    public void send() throws MessagingException {
        if (text == null && html == null)
            throw new NullPointerException("At least one context has to be provided: Text or Html");

        MimeMultipart cover;
        boolean       usingAlternative = false;
        boolean       hasAttachments   = attachments.size() > 0;

        if (text != null && html == null) {
            // TEXT ONLY
            cover = new MimeMultipart("mixed");
            cover.addBodyPart(textPart());
        } else if (text == null && html != null) {
            // HTML ONLY
            cover = new MimeMultipart("mixed");
            cover.addBodyPart(htmlPart());
        } else {
            // HTML + TEXT
            cover = new MimeMultipart("alternative");
            cover.addBodyPart(textPart());
            cover.addBodyPart(htmlPart());
            usingAlternative = true;
        }

        MimeMultipart content = cover;
        if (usingAlternative && hasAttachments) {
            content = new MimeMultipart("mixed");
            content.addBodyPart(toBodyPart(cover));
        }

        for (MimeBodyPart attachment : attachments) {
            content.addBodyPart(attachment);
        }

        msg.setContent(content);
        msg.setSentDate(new Date());
        Transport.send(msg);
    }

    private MimeBodyPart toBodyPart(MimeMultipart cover) throws MessagingException {
        MimeBodyPart wrap = new MimeBodyPart();
        wrap.setContent(cover);
        return wrap;
    }

    private MimeBodyPart textPart() throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(text);
        return bodyPart;
    }

    private MimeBodyPart htmlPart() throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(html, "text/html; charset=utf-8");
        return bodyPart;
    }

    public static void sendEmails(String to,String con){
        String from = "shaosiming@vip.163.com"; 				// 邮件发送人的邮件地址
        //String to = "service@huc.im"; 										// 邮件接收人的邮件地址
        final String username = "shaosiming@vip.163.com";  	//发件人的邮件帐户
        final String password = "adminking93";   					//发件人的邮件密码


        //定义Properties对象,设置环境信息
        Properties props =  new Properties();

        //设置邮件服务器的地址
        props.setProperty("mail.smtp.host", "smtp.vip.163.com"); // 指定的smtp服务器
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.transport.protocol", "smtp");//设置发送邮件使用的协议
        //创建Session对象,session对象表示整个邮件的环境信息
        Session session = Session.getInstance(props);
        //设置输出调试信息
        session.setDebug(true);
        try {
            //Message的实例对象表示一封电子邮件
            MimeMessage message = new MimeMessage(session);
            //设置发件人的地址
            message.setFrom(new InternetAddress(from));
            //设置主题
            message.setSubject("[HUC] Welcome to JavaMail World!");
            //设置邮件的文本内容
            message.setContent(con,"text/html;charset=utf-8");

            //message.addRecipients(Message.RecipientType.CC,new Address[]{new InternetAddress("m18824852014@163.com")});
            //从session的环境中获取发送邮件的对象
            Transport transport=session.getTransport();
            //连接邮件服务器
            transport.connect("smtp.vip.163.com",25, username, password);
            //设置收件人地址,并发送消息
            transport.sendMessage(message,new Address[]{new InternetAddress(to)});
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SendEmail.sendEmails("service@huc.im","尊敬的用户：现在您就能使用 188****2014@163.com 这个邮箱来收发邮件了。为了方便好友知道这是您的邮箱，请尽快设置您的昵称。");
    }

}
