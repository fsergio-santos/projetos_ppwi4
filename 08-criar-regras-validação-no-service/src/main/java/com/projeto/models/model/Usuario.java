package com.projeto.models.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "TAB_USUARIO")
public class Usuario implements Serializable {
 	
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
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Size(min = 10, max = 80, message="Digite o mínimo {min} e o máximo {max}")
	@NotEmpty
	@NotBlank(message="o campo nome do usuário é obrigatório")
	@NotNull(message = "o campo nome do usuário é obrigatório")
	@Column(name="USUARIO_NOME", length = 80, nullable = false)
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	@Size(min = 6, max = 100, message="Digite o mínimo {min} e o máximo {max}")
	@NotEmpty
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
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Size(min = 10, max = 100, message="Digite o mínimo {min} e o máximo {max}")
	@NotEmpty
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

	
	@ManyToOne
	@JoinColumn(name="DEPARTAMENTO_ID")
	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
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
				+ ", lastLogin=" + lastLogin + ", foto=" + foto + ", contentType=" + contentType + "]";
	}
	
	
	
	
}
