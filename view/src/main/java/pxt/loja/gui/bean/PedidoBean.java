package pxt.loja.gui.bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pxt.etq.domain.business.impl.PedidoBO;
import pxt.etq.domain.estoque.ItemPedido;
import pxt.etq.domain.estoque.Produto;
import pxt.framework.business.PersistenceService;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.controller.SearchFieldController;

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

	private SearchFieldController<List<Produto>> searchProduto;

	private List<Produto> listaDeProdutosSelecionados;

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

	public Produto getProdutoNaoNulo() {
		return getDomain().getProdutoNaoNulo();
	}

	public void setProduto(Produto produto) {
		getDomain().setProduto(produto);
	}

	public void setListaDeProdutosSelecionados(List<Produto> produtosSelecionados) {
		this.listaDeProdutosSelecionados = produtosSelecionados;
	}

	public List<Produto> getListaDeProdutosSelecionados() {
		if (listaDeProdutosSelecionados == null) {
			listaDeProdutosSelecionados = new ArrayList<Produto>();
		}
		return listaDeProdutosSelecionados;
	}

	@SuppressWarnings("serial")
	public SearchFieldController<List<Produto>> getSearchProduto() {
		if (this.searchProduto == null) {
			this.searchProduto = new SearchFieldController<List<Produto>>(this.persistenceService, Produto.class) {

				@Override
				public void setObject(List<Produto> produtos) {
					setListaDeProdutosSelecionados(produtos);
				}

				@Override
				public List<Produto> getObject() {
					return getListaDeProdutosSelecionados();
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

}
