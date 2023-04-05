package pxt.etq.domain.estoque;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "gabrielpa.tetqcliente")
public class Cliente implements Serializable {
	private static final long serialVersionUID = -3284097315958966215L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODCLIETQ_SEQ")
	@SequenceGenerator(sequenceName = "CODCLIETQ_SEQ", allocationSize = 1, name = "CODCLIETQ_SEQ")
	@Column(name = "CODCLI")
	private Integer codigo;
	@Column(name = "NOMCLI")
	private String nome;
	@Column(name = "CPFCNP")
	private String cpfCnpj;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		nome = nome.replaceAll("[^a-zA-Z ]+", "");
		this.nome = nome;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(codigo, other.codigo);
	}

}
