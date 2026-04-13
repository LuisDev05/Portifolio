package luisdev.backend_portifolio.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ContactDTO {
    private String nome;
    private String email;
    private String assunto;
    private String mensagem;
    private MultipartFile arquivo; // Campo opcional para envio de anexo
}
