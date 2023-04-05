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
@Table(name = "gabrielpa.tetqfornecedor")
public class Fornecedor implements Serializable {
	private static final long serialVersionUID = 8510855018349481925L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODFRNETQ_SEQ")
	@SequenceGenerator(sequenceName = "CODFRNETQ_SEQ", allocationSize = 1, name = "CODFRNETQ_SEQ")
	@Column(name = "CODFRN")
	private Integer codigo;
	@Column(name = "NOMFRN")
	private String nome;
	@Column(name = "CNPJ")
	private String cnpj;

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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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
		Fornecedor other = (Fornecedor) obj;
		return Objects.equals(codigo, other.codigo);
	}

}
