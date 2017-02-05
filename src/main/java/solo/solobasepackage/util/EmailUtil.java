package solo.solobasepackage.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cn.nukkit.Server;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.Config;
import solo.solobasepackage.Main;

public class EmailUtil{
	
	private EmailUtil(){
		
	}
	
	public static ArrayList<Map.Entry<String, String>> emails = new ArrayList<Map.Entry<String, String>>();
	private static int index = 0;
	
	@SuppressWarnings({ "deprecation", "serial", "unchecked" })
	public static void init(){
		Config config = new Config(new File(Main.getInstance().getDataFolder(), "emailSetting.yml"), Config.YAML, new LinkedHashMap<String, Object>(){{
			put("emails", new LinkedHashMap<String, String>(){{
				put("example@google.com", "examplePassword");
				put("example2@google.com", "examplePassword2");
			}});
		}});
		
		((LinkedHashMap<String, String>) config.get("emails")).entrySet().forEach((e) -> emails.add(e));
	}
	
	public static Map.Entry<String, String> getNextEmail(){
		if(++index > emails.size() - 1){
			index = 0;
		}
		return emails.get(index);
	}
	
	public static boolean isValidEmail(String email){
		boolean err = false;
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";   
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		if(m.matches()){
			err = true;
		}
		return err;
	}
	
	public static String createCertificationCode(){
		return createCertificationCode(2);
	}
	
	public static String createCertificationCode(int level){
		int length = 4;
		char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		if(level < 2){
			//NOTHING DO
		}else if(level >= 2){
			length = 6;
			if(level >= 3){
				chars = new char[]{
						'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
						'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
						'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
						'U', 'V', 'W', 'X', 'Y', 'Z'
						};
				if(level >= 4){
					length = 8;
					if(level >= 5){
						chars = new char[]{
								'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
								'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
								'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
								'U', 'V', 'W', 'X', 'Y', 'Z',
								'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
								'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
								'u', 'v', 'w', 'x', 'y', 'z'
								};
					}
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < length; i++){
			sb.append(chars[new Random().nextInt(chars.length - 1)]);
		}
		return sb.toString();
	}
	
	public static void sendEmail(String recipientEmail, String subject, String body){
		Server.getInstance().getScheduler().scheduleAsyncTask(new MailTask(recipientEmail, subject, body));
	}
}

class MailTask extends AsyncTask{

	public String senderEmail;
	public String password;
	public String recipientEmail;
	public String subject;
	public String body;
	
	public MailTask(String recipientEmail, String subject, String body){
		super();
		Map.Entry<String, String> emailInfo = EmailUtil.getNextEmail();
		this.senderEmail = emailInfo.getKey();
		this.password = emailInfo.getValue();
		this.recipientEmail = recipientEmail;
		this.subject = subject;
		this.body = body;
	}
	
	@Override
	public void onRun(){
		String username = this.senderEmail.split("@")[0]; //이메일 주소중 @ naver.com앞주소만 기재합니다.
		String password = this.password; //이메일 비밀번호를 기재합니다.
		String address = this.senderEmail.split("@")[1];
		String host;
		int port;
		
		if(address.endsWith("gmail.com")){
			port = 465;
			host = "smtp.gmail.com";
		}else if(address.endsWith("naver.com")){
			this.setResult(false);
			return; // NOT SUPPORT YET
		}else{
			this.setResult(false);
			return; // NOT SUPPORT YET
		}
		 
		// 메일 내용
		//recipient = "solo_5@naver.com"; //메일을 발송할 이메일 주소를 기재해 줍니다.
		//subject = "네이버를 사용한 발송 테스트입니다.";
		//body = "내용 무";
		 
		Properties props = System.getProperties();
		
		props.put("mail.smtp.user", username + "@" + address);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.dass", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		   
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
			String un = username;
			String pw = password;
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(un, pw);
			}
		});
		session.setDebug(Debug.debug); //for debug
		
		try{
			Message mimeMessage = new MimeMessage(session);
			
			// 이메일 보낸 시간
			mimeMessage.setSentDate(new Date());
			
			// 이메일 발신자
			mimeMessage.setFrom(new InternetAddress(username + "@" + address));
			
			// 이메일 수신자
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(this.recipientEmail));
			
			// 이메일 제목
			mimeMessage.setSubject(this.subject);
			
			// 이메일 내용
			mimeMessage.setText(this.body);
			
			// 이메일 보내기
			Transport.send(mimeMessage);
			this.setResult(true);
		}catch(AddressException ae){
			this.setResult(false);
			ae.printStackTrace();
		}catch(MessagingException me){
			this.setResult(false);
			me.printStackTrace();
		}
	}
	
	@Override
	public void onCompletion(Server server){
		if((boolean) this.getResult()){
			Debug.normal(Main.getInstance(), "메일을 성공적으로 발송하였습니다.");
		}else{
			Debug.critical(Main.getInstance(), "메일 발송에 실패하였습니다.");
			Debug.critical(Main.getInstance(), "이메일 : " + this.senderEmail);
		}
	}
}