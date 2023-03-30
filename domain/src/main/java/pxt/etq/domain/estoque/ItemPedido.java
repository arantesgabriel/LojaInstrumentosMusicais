package pxt.etq.domain.estoque;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "gabrielpa.tetqitempedido")
public class ItemPedido {

	@Id
	@Column(name = "NUMID")
	private Integer numeroID;
	@ManyToOne
	@JoinColumn(name = "CODPRO", referencedColumnName = "CODPRO")
	private Produto produto;
	@Column(name = "QDEPRO")
	private Integer quantidade;

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

}
