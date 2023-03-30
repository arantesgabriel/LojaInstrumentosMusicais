package pxt.etq.domain.estoque;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "gabrielpa.tetqpedido")
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODPED")
	private Integer codigo;
	@ManyToOne
	@JoinColumn(name = "CODCLI", referencedColumnName = "CODCLI")
	private Cliente cliente;
	@OneToOne
	@JoinColumn(name = "NUMID", referencedColumnName = "NUMID")
	private ItemPedido item;
	@Column(name = "DATAPED")
	private Date data;
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public ItemPedido getItem() {
		return item;
	}
	public void setItem(ItemPedido item) {
		this.item = item;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
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
		Pedido other = (Pedido) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
}
