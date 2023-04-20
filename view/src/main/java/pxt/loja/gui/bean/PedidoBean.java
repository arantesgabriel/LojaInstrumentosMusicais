package pxt.loja.gui.bean;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import pxt.etq.domain.business.impl.PedidoBO;
import pxt.etq.domain.estoque.Cliente;
import pxt.etq.domain.estoque.ItemPedido;
import pxt.etq.domain.estoque.Pedido;
import pxt.etq.domain.estoque.Produto;
import pxt.framework.business.PersistenceService;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.controller.SearchFieldController;
import pxt.framework.faces.exception.CrudException;
import pxt.framework.persistence.PersistenceException;
import pxt.framework.validation.ValidationException;

// Sinaliza que essa classe será a controladora

@ManagedBean
@ViewScoped
public class PedidoBean extends CrudController<Pedido> {
	private static final long serialVersionUID = 1L;

	private Pedido domain;

	@EJB
	private PedidoBO pedidoBO;
	@EJB
	private PersistenceService persistenceService;
	private SearchFieldController<Cliente> searchCliente;
	private SearchFieldController<Produto> searchProduto;
	private Produto produto;
	private Integer quantidade;
	private BigDecimal total;

	@Override
	public Pedido getDomain() {

		if (domain == null) {
			domain = new Pedido();
		}
		return domain;
	}

	@Override
	public void setDomain(Pedido domain) {
		this.domain = domain;
	}

	@Override
	public PersistenceService getPersistenceService() {
		return persistenceService;
	}

	public Produto getProdutoNaoNulo() {
		if (produto == null) {
			this.produto = new Produto();
		}
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

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public List<ItemPedido> getListaItens() {
		return domain.getItensDoPedido();
	}

	public void setListaItens(List<ItemPedido> listaItens) {
		domain.setItemPedido(listaItens);
	}

	@SuppressWarnings("serial")
	public SearchFieldController<Cliente> getSearchCliente() {
		if (this.searchCliente == null) {
			this.searchCliente = new SearchFieldController<Cliente>(this.persistenceService, Cliente.class) {

				@Override
				public Cliente getObject() {
					return getDomain().getClienteNaoNulo();
				}

				@Override
				public void setObject(Cliente cliente) {
					getDomain().setCliente(cliente);
				}

				@Override
				public void buscar() throws Exception {
					setResultList((List<Cliente>) persistenceService.findByExample(((Cliente) getSearchObject())));
				}

				@Override
				public void limpar() {
					super.limpar();
				}
			};
		}
		return this.searchCliente;
	}

	@SuppressWarnings("serial")
	public SearchFieldController<Produto> getSearchProduto() {
		if (this.searchProduto == null) {
			this.searchProduto = new SearchFieldController<Produto>(this.persistenceService, Produto.class) {

				@Override
				public Produto getObject() {
					return getProdutoNaoNulo();
				}

				@Override
				public void setObject(Produto produto) {
					setProduto(produto);
				}

				@Override
				public void buscar() throws Exception {
					setResultList((List<Produto>) persistenceService.findByExample(((Produto) getSearchObject())));
				}

				@Override
				public void limpar() {
					super.limpar();
				}
			};
		}
		return this.searchProduto;
	}

	public void antesAdicionarItem() throws CrudException {

		if (domain.getCliente().getCodigo() == null) {
			throw new CrudException(CrudException.WARN_EXCEPTION_TYPE, "O campo cliente é obrigatório.");
		} else if (getProdutoNaoNulo().getCodigo() == null) {
			throw new CrudException(CrudException.WARN_EXCEPTION_TYPE, "O campo produto é obrigatório.");
		} else if (getQuantidade() == null || getQuantidade() <= 0) {
			throw new CrudException(CrudException.WARN_EXCEPTION_TYPE,
					"A quantidade informada deve ser maior que zero.");
		}

	}

	public void adicionarItem() {

		try {
			antesAdicionarItem();
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setProduto(getProdutoNaoNulo());
			itemPedido.setQuantidade(getQuantidade());
			itemPedido.setValorItem(produto.getValor());
			getListaItens().add(itemPedido);
			setTotal(pedidoBO.calcularTotalPedido(itemPedido.getValorItem(), quantidade));
		} catch (CrudException e) {
			msgInfo(e.getMessage());
		}

	}

	@Override
	public void salvar(ActionEvent arg0) {
		try {
			antesAdicionarItem();
			pedidoBO.efetuarPedido(domain, getListaItens());
			msgInfo("Pedido realizado com sucesso!");
		} catch (ValidationException e) {
			msgInfo(e.getMessage());
			e.printStackTrace();
		} catch (PersistenceException e) {
			msgInfo(e.getMessage());
			e.printStackTrace();
		} catch (CrudException e) {
			msgInfo(e.getMessage());
			e.printStackTrace();
		}
	}

}
