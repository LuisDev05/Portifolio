package luisdev.backend_portifolio.controller;

import luisdev.backend_portifolio.dto.ContactDTO;
import luisdev.backend_portifolio.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contato")
@CrossOrigin(origins = "*") // Permite acesso do seu FrontEnd (ajustar depois para a URL do front)
public class ContactController {

    private final EmailService emailService;

    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<String> enviarContato(@ModelAttribute ContactDTO contactDTO) {
        try {
            // Validação de segurança: limita o tamanho do anexo a 5MB (5 * 1024 * 1024 bytes)
            if (contactDTO.getArquivo() != null && !contactDTO.getArquivo().isEmpty()) {
                if (contactDTO.getArquivo().getSize() > 5 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("O arquivo excede o limite máximo de 5MB.");
                }
            }

            // Invoca o serviço para envio do e-mail
            emailService.enviarEmail(contactDTO);
            return ResponseEntity.ok("E-mail enviado com sucesso!");

        } catch (Exception e) {
            // Retorna o status 500 caso ocorra falha ao tentar enviar
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao tentar enviar a mensagem: " + e.getMessage());
        }
    }
}
