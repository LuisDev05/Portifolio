package luisdev.backend_portifolio.service;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import luisdev.backend_portifolio.dto.ContactDTO;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String emailDestiny;

    public EmailService(JavaMailSender mailSender, Dotenv dotenv) {
        this.mailSender = mailSender;
        // Pega o e-mail de destino do arquivo .env
        this.emailDestiny = dotenv.get("EMAIL_DESTINY");
    }

    public void enviarEmail(ContactDTO contactDTO) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            
            // Flag 'true' indica que a mensagem será do tipo multipart (suporta anexos)
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(emailDestiny);
            helper.setSubject("Novo Contato do Portfólio: " + contactDTO.getAssunto());
            
            // Corpo do e-mail estruturado em HTML
            String conteudoEmail = String.format(
                    "<h3>Novo contato recebido do portfólio</h3>" +
                    "<p><strong>Nome:</strong> %s</p>" +
                    "<p><strong>Email:</strong> %s</p>" +
                    "<p><strong>Mensagem:</strong><br>%s</p>",
                    contactDTO.getNome(),
                    contactDTO.getEmail(),
                    contactDTO.getMensagem()
            );
            
            helper.setText(conteudoEmail, true);
            helper.setReplyTo(contactDTO.getEmail());

            // Processa o anexo se existir
            if (contactDTO.getArquivo() != null && !contactDTO.getArquivo().isEmpty()) {
                helper.addAttachment(
                        contactDTO.getArquivo().getOriginalFilename(),
                        contactDTO.getArquivo()
                );
            }

            mailSender.send(message);
        } catch (MessagingException e) {
            // Tratamento de exceção customizado para notificar falhas no envio
            throw new RuntimeException("Erro ao enviar o e-mail: " + e.getMessage(), e);
        }
    }
}
