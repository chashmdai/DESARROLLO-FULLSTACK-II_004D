package cl.nexbyte.service;

import cl.nexbyte.api.dto.AuthResponse;
import cl.nexbyte.api.dto.LoginRequest;
import cl.nexbyte.api.dto.RegisterRequest;
import cl.nexbyte.model.Usuario;
import cl.nexbyte.repository.UsuarioRepository;
import cl.nexbyte.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class AuthService {
  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder encoder;
  private final JwtService jwt;

  public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder encoder, JwtService jwt) {
    this.usuarioRepository = usuarioRepository;
    this.encoder = encoder;
    this.jwt = jwt;
  }

  @Transactional
  public AuthResponse register(RegisterRequest req) {
    if (usuarioRepository.existsByEmail(req.getEmail())) {
      throw new IllegalArgumentException("Email ya registrado");
    }
    Usuario u = new Usuario();
    u.setNombre(req.getNombre());
    u.setEmail(req.getEmail().toLowerCase());
    u.setHashPassword(encoder.encode(req.getPassword()));
    u.setTelefono(req.getTelefono());
    u.setRol(Usuario.Rol.CLIENTE);
    u.setCreadoEn(LocalDateTime.now());
    usuarioRepository.save(u);

    String token = jwt.generate(u.getEmail(), Map.of("uid", u.getId(), "rol", u.getRol().name()));
    return new AuthResponse(token, u.getId(), u.getNombre(), u.getEmail(), u.getRol().name());
  }

  @Transactional
  public AuthResponse login(LoginRequest req) {
    Usuario u = usuarioRepository.findByEmail(req.getEmail().toLowerCase())
      .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));
    if (!encoder.matches(req.getPassword(), u.getHashPassword())) {
      throw new IllegalArgumentException("Credenciales inválidas");
    }
    u.setUltimoLogin(LocalDateTime.now());
    String token = jwt.generate(u.getEmail(), Map.of("uid", u.getId(), "rol", u.getRol().name()));
    return new AuthResponse(token, u.getId(), u.getNombre(), u.getEmail(), u.getRol().name());
  }
}
