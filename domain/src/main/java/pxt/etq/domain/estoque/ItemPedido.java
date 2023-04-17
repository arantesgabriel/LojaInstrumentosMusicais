package pxt.etq.domain.estoque;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "gabrielpa.tetqitempedido")
public class ItemPedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NUMIDITE_SEQ")
	@SequenceGenerator(sequenceName = "NUMIDITE_SEQ", allocationSize = 1, name = "NUMIDITE_SEQ")
	@Column(name = "NUMID")
	private Integer numeroID;

	@ManyToOne
	@JoinColumn(name = "CODPED", referencedColumnName = "CODPED")
	private Pedido pedido;

	@OneToOne
	@JoinColumn(name = "CODPRO", referencedColumnName = "CODPRO")
	private Produto produto;

	@Column(name = "QDEPRO")
	private Integer quantidade;

	@Column(name = "VLRITEM")
	private BigDecimal valorItem;

	public Integer getNumeroID() {
		return numeroID;
	}

	public void setNumeroID(Integer numeroID) {
		this.numeroID = numeroID;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

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

	public BigDecimal getValorItem() {
		return valorItem;
	}

	public void setValorItem(BigDecimal valorItem) {
		this.valorItem = valorItem;
	}

	public Produto getProdutoNaoNulo() {
		if (produto == null) {
			return new Produto();
		}
		return this.produto;
	}

	public Pedido getPedidoNaoNulo() {
		if (pedido == null) {
			pedido = new Pedido();
		}
		return this.pedido;
	}

}
