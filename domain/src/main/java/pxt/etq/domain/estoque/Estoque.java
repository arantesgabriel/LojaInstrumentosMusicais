package pxt.etq.domain.estoque;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "gabrielpa.tetqestoque")
public class Estoque implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@OneToOne
	@JoinColumn(name = "CODPRO", referencedColumnName = "CODPRO")
	private Produto produto;
	@Column(name = "QDEPRO")
	private Integer quantidade;
	@Column(name = "QDEREC")
	private Integer quantidadeRecebimento;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getQuantidadeRecebimento() {
		return quantidadeRecebimento;
	}

	public void setQuantidadeRecebimento(Integer quantidadeRecebimento) {
		this.quantidadeRecebimento = quantidadeRecebimento;
	}
	
	public Produto getProdutoNaoNulo() {
		if (produto == null) {
			return new Produto();
		}
		return this.produto;
	}

}
