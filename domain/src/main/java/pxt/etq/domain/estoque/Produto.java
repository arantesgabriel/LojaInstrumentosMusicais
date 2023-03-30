package pxt.etq.domain.estoque;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "gabrielpa.tetqproduto")
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODPRO_SEQ")
	@SequenceGenerator(sequenceName = "CODPRO_SEQ", allocationSize = 1, name = "CODPRO_SEQ")
	@Column(name = "CODPRO")
	private Integer codigo;
	@Column(name = "DESPRO")
	private String descricao;
	@Column(name = "INDATV")
	private Boolean indicadorAtivo = true;
	@Column(name = "VLRPRO")
	private BigDecimal valor;
	@ManyToOne
	@JoinColumn(name = "CODFRN", referencedColumnName = "CODFRN")
	private Fornecedor fornecedor;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getIndicadorAtivo() {
		return indicadorAtivo;
	}

	public void setIndicadorAtivo(Boolean indicadorAtivo) {
		this.indicadorAtivo = indicadorAtivo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	public Fornecedor getFornecedorNaoNulo() {
		if (fornecedor == null) {
			return new Fornecedor();
		}
		return this.fornecedor;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return Objects.equals(codigo, other.codigo);
	}

}
