package luisdev.backend_portifolio.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class DotenvConfig {

    @Bean
    public Dotenv dotenv() {
        // Carrega o arquivo .env. Certifique-se de que ele esteja na raiz da pasta BackEnd
        return Dotenv.configure().ignoreIfMissing().load();
    }

    @Bean
    public JavaMailSender javaMailSender(Dotenv dotenv) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        // Configurações do JavaMailSender usando as variáveis do Dotenv
        mailSender.setHost(dotenv.get("SMTP_HOST", "smtp.gmail.com"));
        mailSender.setPort(Integer.parseInt(dotenv.get("SMTP_PORT", "587")));
        mailSender.setUsername(dotenv.get("EMAIL_USER"));
        mailSender.setPassword(dotenv.get("EMAIL_PASS"));
        
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        // Opcional: habilitar debug para testes
        // props.put("mail.debug", "true");
        
        return mailSender;
    }
}
