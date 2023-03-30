package pxt.loja.gui.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import pxt.etq.domain.business.impl.EstoqueBO;
import pxt.etq.domain.estoque.Estoque;
import pxt.etq.domain.estoque.Produto;
import pxt.framework.business.PersistenceService;
import pxt.framework.business.TransactionException;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.controller.CrudState;
import pxt.framework.faces.controller.SearchFieldController;
import pxt.framework.faces.exception.CrudException;
import pxt.framework.persistence.PersistenceException;
import pxt.framework.validation.ValidationException;

@ManagedBean
@ViewScoped
public class LiberarBean extends CrudController<Estoque> {
	private static final long serialVersionUID = 1L;

	private Estoque domain;

	@EJB
	private EstoqueBO estoqueBO;

	@EJB
	private PersistenceService persistenceService;

	private SearchFieldController<Produto> searchProduto;

	@Override
	public Estoque getDomain() {
		if (domain == null) {
			domain = new Estoque();
		}
		return domain;
	}

	@Override
	public void setDomain(Estoque domain) {
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

	@Override
	protected void antesSalvar() throws CrudException {
		if (getDomain().getQuantidade() == null) {
			this.msgWarn("A quantidade é um campo obrigatório");
		}
	}

	@Override
	public void salvar(ActionEvent arg0) {

		try {
			this.antesSalvar();
			this.addToList(getDomain());
			getEstadoCrud();
			this.configuraEstado(CrudState.ST_DEFAULT);
			estoqueBO.salvarEstoqueLiberacao(getDomain());
			msgInfo("Produto liberado com sucesso!");
		} catch (CrudException | PersistenceException | TransactionException e) {
			msgError(e, e.getMessage());
			e.printStackTrace();
		} catch (ValidationException e) {
			msgWarn(e.getMessage());
			e.printStackTrace();
		}
	}
}
