package com.projeto.models.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



@Entity
@Table(name = "TAB_USUARIO")
public class Usuario implements UserDetails, Serializable {
 	
	private static final long serialVersionUID = 559856630679379436L;

	private Long    id;
	private String  username;
	private String  password;
	private String  confirmPassword;
	private String  email;
	private boolean ativo = Boolean.FALSE;
	private Integer failedLogin = 0;
	private Date    lastLogin;
	private String  foto;
	private String  contentType; 
	
	private Departamento departamento;
	
	private List<Role> roles = new ArrayList<Role>(); 
		
	public Usuario() {
		
	}


	public Usuario(Long id, String username, String password, String confirmPassword, String email, boolean ativo,
			Integer failedLogin, Date lastLogin, String foto, String contentType) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.email = email;
		this.ativo = ativo;
		this.failedLogin = failedLogin;
		this.lastLogin = lastLogin;
		this.foto = foto;
		this.contentType = contentType;
	}
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USUARIO_ID")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
    @Override
	@Size(min = 10, max = 80, message="Digite o mínimo {min} e o máximo {max} caracteres")
	@NotEmpty(message="O nome do usuário é obrigatório")
	@NotBlank(message="O nome do usuário é obrigatório")
	@NotNull(message = "O nome do usuário é obrigatório")
	@Column(name="USUARIO_NOME", length = 80, nullable = false)
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	@NotEmpty(message="A senha do usuário é obrigatório, não pode ser vázia")
	@NotBlank(message="A senha do usuário é obrigatório")
	@NotNull(message = "A senha do usuário é obrigatório")
	@Column(name="USUARIO_PASSWORD", length = 100, nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Transient
	//@NotEmpty(message="A senha deve ser informada não pode ser vázia ")
	//@NotBlank(message="A senha deve ser informada não pode estar em branco ")
	//@NotNull(message = "A senha deve ser informada não pode ser nula")
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Email(message = "E-mail inválido")
	@Size(min = 10, max = 100, message="Digite o mínimo {min} e o máximo {max}")
	@NotEmpty(message="O e-mail do usuário deve ser informado")
	@NotBlank(message="O e-mail do usuário é obrigatório")
	@NotNull(message = "O e-mail do usuário é obrigatório")
	@Column(name="USUARIO_EMAIL", length = 100, nullable = false, unique = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="USUARIO_ATIVO", nullable = false)
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Column(name="USUARIO_FALHA_LOGIN",  nullable = true )
	public Integer getFailedLogin() {
		return failedLogin;
	}

	public void setFailedLogin(Integer failedLogin) {
		this.failedLogin = failedLogin;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name="USUARIO_LAST_LOGIN", columnDefinition = "DATE", nullable = true)
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Column(name="USUARIO_FOTO", length = 100, nullable = true)
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	@Column(name="USUARIO_CONTENT_TYPE", length = 30, nullable = true)
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

    @NotNull(message = "O departamento deve ser informado!")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="DEPARTAMENTO_ID", nullable = false)
	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	@Size(min = 1, message = "Deve informar pelo menos uma {min} role para usuário")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable( name = "TAB_USUARIO_ROLE", 
				joinColumns = @JoinColumn(name="USUARIO_ID"),
				inverseJoinColumns = @JoinColumn(name="ROLE_ID"))
	public List<Role> getRoles() {
		return roles;
	}


	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	@Transient
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    List<GrantedAuthority> autoridades = new ArrayList<GrantedAuthority>();
		for (Role role: this.getRoles()) {
			autoridades.add(new SimpleGrantedAuthority("ROLE_"+role.getNome().toUpperCase()));
		}
		return autoridades;
	}
	

	@Transient
	@Override
	public boolean isAccountNonExpired() {
		return ativo;
	}

	@Transient
	@Override
	public boolean isAccountNonLocked() {
		return ativo;
	}

	@Transient
	@Override
	public boolean isCredentialsNonExpired() {
		return ativo;
	}

    @Transient
	@Override
	public boolean isEnabled() {
		return ativo;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", password=" + password + ", confirmPassword="
				+ confirmPassword + ", email=" + email + ", ativo=" + ativo + ", failedLogin=" + failedLogin
				+ ", lastLogin=" + lastLogin + ", foto=" + foto + ", contentType=" + contentType + ", departamento="
				+ departamento + ", roles=" + roles + "]";
	}


	
	
	
	
	
}
