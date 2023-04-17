package pxt.etq.domain.estoque;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "gabrielpa.tetqpedido")
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CODPEDETQ_SEQ")
	@SequenceGenerator(sequenceName = "CODPEDETQ_SEQ", allocationSize = 1, name = "CODPEDETQ_SEQ")
	@Column(name = "CODPED")
	private Integer codigo;

	@ManyToOne
	@JoinColumn(name = "CODCLI", referencedColumnName = "CODCLI")
	private Cliente cliente;

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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Cliente getClienteNaoNulo() {
		if (cliente == null) {
			return this.cliente = new Cliente();
		}
		return this.cliente;
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
