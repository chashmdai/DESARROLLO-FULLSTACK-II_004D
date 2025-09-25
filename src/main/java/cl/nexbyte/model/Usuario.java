package cl.nexbyte.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
public class Usuario {
  public enum Rol { ADMIN, CLIENTE }

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nombre;
  private String email;

  @Column(name = "hash_password")
  private String hashPassword;

  @Enumerated(EnumType.STRING)
  private Rol rol = Rol.CLIENTE;

  private String telefono;

  @Column(name = "creado_en")
  private LocalDateTime creadoEn;

  @Column(name = "ultimo_login")
  private LocalDateTime ultimoLogin;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getHashPassword() { return hashPassword; }
  public void setHashPassword(String hashPassword) { this.hashPassword = hashPassword; }
  public Rol getRol() { return rol; }
  public void setRol(Rol rol) { this.rol = rol; }
  public String getTelefono() { return telefono; }
  public void setTelefono(String telefono) { this.telefono = telefono; }
  public LocalDateTime getCreadoEn() { return creadoEn; }
  public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
  public LocalDateTime getUltimoLogin() { return ultimoLogin; }
  public void setUltimoLogin(LocalDateTime ultimoLogin) { this.ultimoLogin = ultimoLogin; }
}
