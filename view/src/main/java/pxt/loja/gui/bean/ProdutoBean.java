package pxt.loja.gui.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pxt.etq.domain.business.impl.ProdutoBO;
import pxt.etq.domain.estoque.Fornecedor;
import pxt.etq.domain.estoque.Produto;
import pxt.framework.business.PersistenceService;
import pxt.framework.business.TransactionException;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.controller.SearchFieldController;
import pxt.framework.faces.exception.CrudException;

// Sinaliza que essa classe será a controladora

@ManagedBean
@ViewScoped
public class ProdutoBean extends CrudController<Produto> {
	private static final long serialVersionUID = 4069931526797373122L;

	private Produto domain;

	@EJB
	private ProdutoBO produtoBO;

	@EJB
	private PersistenceService persistenceService;

	private SearchFieldController<Fornecedor> searchFornecedor;

	@Override
	public Produto getDomain() {

		if (domain == null) {
			domain = new Produto();
		}
		return domain;
	}

	@Override
	public void setDomain(Produto domain) {
		this.domain = domain;
	}

	@Override
	public PersistenceService getPersistenceService() {
		return persistenceService;
	}

	@Override
	protected void antesSalvar() throws CrudException {

		if (getDomain().getDescricao() == null || getDomain().getDescricao().isEmpty()) {
			this.msgWarn("A descrição é um campo obrigatório");
		}

		domain.setIndicadorAtivo(true);
	}

	public Fornecedor getFornecedorNaoNulo() {
		return getDomain().getFornecedorNaoNulo();
	}

	public void setFornecedor(Fornecedor fornecedor) {
		getDomain().setFornecedor(fornecedor);
	}

	@SuppressWarnings("serial")
	public SearchFieldController<Fornecedor> getSearchFornecedor() {
		if (this.searchFornecedor == null) {
			this.searchFornecedor = new SearchFieldController<Fornecedor>(this.persistenceService, Fornecedor.class) {

				@Override
				public Fornecedor getObject() {
					return getFornecedorNaoNulo();
				}

				@Override
				public void setObject(Fornecedor fornecedor) {
					setFornecedor(fornecedor);
				}

				@Override
				public void buscar() throws Exception {
					setResultList(
							(List<Fornecedor>) persistenceService.findByExample(((Fornecedor) getSearchObject())));
				}

				@Override
				public void limpar() {
					super.limpar();
				}
			};
		}
		return this.searchFornecedor;
	}

	@Override
	protected void salvar() throws CrudException {

		try {
			produtoBO.salvarProduto(domain);
		} catch (TransactionException e) {
			msgWarn(e.getMessage());
			e.printStackTrace();
		}
	}

}
