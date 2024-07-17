package mingetal.MCM.Login.services;

import mingetal.MCM.Login.entities.UsuarioEntity;
import mingetal.MCM.Login.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JavaMailSender emailSender;
    private static final String DIGITS = "0123456789";
    private static final int CODE_LENGTH = 6;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser(UsuarioEntity usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        return "user added to the system";
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    public String generateNumericCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        }
        return code.toString();
    }

    public void sendEmail(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("outlook_40A2E162221393E3@outlook.com");
        message.setTo(to);
        message.setSubject("Cambio de Contraseña");
        message.setText(text);
        emailSender.send(message);
    }

    public boolean compareCode(String correo, String code) {
        String savedCode = usuarioRepository.findByCorreo(correo).getCodigo_cambio_contrasenia();
        return savedCode.equals(code);
    }
}
