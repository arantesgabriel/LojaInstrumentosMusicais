package pxt.loja.gui.bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import pxt.etq.domain.business.impl.PedidoBO;
import pxt.etq.domain.estoque.Cliente;
import pxt.etq.domain.estoque.ItemPedido;
import pxt.etq.domain.estoque.Produto;
import pxt.framework.business.PersistenceService;
import pxt.framework.business.TransactionException;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.controller.SearchFieldController;
import pxt.framework.persistence.PersistenceException;
import pxt.framework.validation.ValidationException;

// Sinaliza que essa classe será a controladora

@ManagedBean
@ViewScoped
public class PedidoBean extends CrudController<ItemPedido> {
	private static final long serialVersionUID = 1L;

	private ItemPedido domain;

	@EJB
	private PedidoBO pedidoBO;

	@EJB
	private PersistenceService persistenceService;

	private SearchFieldController<Cliente> searchCliente;

	private SearchFieldController<Produto> searchProduto;

	private List<ItemPedido> listaDeProdutosSelecionados;

	@Override
	public ItemPedido getDomain() {

		if (domain == null) {
			domain = new ItemPedido();
		}
		return domain;
	}

	@Override
	public void setDomain(ItemPedido domain) {
		this.domain = domain;
	}

	@Override
	public PersistenceService getPersistenceService() {
		return persistenceService;
	}

	public Produto getProduto() {
		return getDomain().getProdutoNaoNulo();
	}

	public void setProduto(Produto produto) {
		getDomain().setProduto(produto);
	}

	@SuppressWarnings("serial")
	public SearchFieldController<Cliente> getSearchCliente() {
		if (this.searchCliente == null) {
			this.searchCliente = new SearchFieldController<Cliente>(this.persistenceService, Cliente.class) {

				@Override
				public Cliente getObject() {
					return getDomain().getPedidoNaoNulo().getClienteNaoNulo();
				}

				@Override
				public void setObject(Cliente cliente) {
					getDomain().getPedidoNaoNulo().setCliente(cliente);
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
					return getProduto();
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

	
	public void setListaDeProdutosSelecionados(List<ItemPedido> produtosSelecionados) {
		this.listaDeProdutosSelecionados = produtosSelecionados;
	}

	public List<ItemPedido> getListaDeProdutosSelecionados() {
		if (listaDeProdutosSelecionados == null) {
			listaDeProdutosSelecionados = new ArrayList<ItemPedido>();
		}
		return listaDeProdutosSelecionados;
	}

	public void adicionarItemALista() {
		getDomain().setValorItem(getDomain().getProduto().getValor());
		getListaDeProdutosSelecionados().add(getDomain());
		domain = new ItemPedido();
	}

	@Override
	public void salvar(ActionEvent arg0) {
		try {
			pedidoBO.efetuarPedido(domain, listaDeProdutosSelecionados);
		} catch (ValidationException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}

}
