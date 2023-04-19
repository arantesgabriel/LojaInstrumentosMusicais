package pxt.etq.domain.estoque;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "gabrielpa.tetqestoquemov")
public class EstoqueMovimentacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TETQCODMOV_SEQ")
	@SequenceGenerator(sequenceName = "TETQCODMOV_SEQ", allocationSize = 1, name = "TETQCODMOV_SEQ")
	@Column(name = "CODMOV")
	private Integer codigo;
	@ManyToOne
	@JoinColumn(name = "CODPRO", referencedColumnName = "CODPRO")
	private Produto produto;
	@Column(name = "DATMOV")
	private Date data;
	@Column(name = "QDEMOV")
	private Integer quantidadeMovimentada;
	@Enumerated(EnumType.STRING)
	@Column(name = "TIPOP")
	private TipoOperacao tipoOperacao;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getQuantidadeMovimentada() {
		return quantidadeMovimentada;
	}

	public void setQuantidadeMovimentada(Integer quantidadeMovimentada) {
		this.quantidadeMovimentada = quantidadeMovimentada;
	}

	public TipoOperacao getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(TipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
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
		EstoqueMovimentacao other = (EstoqueMovimentacao) obj;
		return Objects.equals(codigo, other.codigo);
	}

}
