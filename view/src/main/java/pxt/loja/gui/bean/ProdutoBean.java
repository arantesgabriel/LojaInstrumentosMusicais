package pxt.loja.gui.bean;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import pxt.etq.domain.business.impl.ProdutoBO;
import pxt.etq.domain.estoque.Fornecedor;
import pxt.etq.domain.estoque.Produto;
import pxt.framework.business.PersistenceService;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.controller.CrudState;
import pxt.framework.faces.controller.SearchFieldController;
import pxt.framework.faces.exception.CrudException;
import pxt.framework.persistence.PersistenceException;

// Sinaliza que essa classe será a controladora

@ManagedBean
@ViewScoped
public class ProdutoBean extends CrudController<Produto> {
	private static final long serialVersionUID = 1L;

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
	protected void antesSalvar() throws CrudException {

		if (getDomain().getDescricao() == null || getDomain().getDescricao().isEmpty()) {
			throw new CrudException(CrudException.WARN_EXCEPTION_TYPE, "O campo descrição é obrigatório");
		}
		
		if (domain.getValor() == null) {
			throw new CrudException(CrudException.WARN_EXCEPTION_TYPE, "O campo valor é obrigatório.");
		}
		
		if (domain.getFornecedor() == null) {
			throw new CrudException(CrudException.WARN_EXCEPTION_TYPE, "O campo fornecedor é obrigatório.");
		}
		
		if (domain.getDescricao().length() < 5) {
			throw new CrudException(CrudException.WARN_EXCEPTION_TYPE, "É necessário ter no mínimo 5 caracteres no campo descrição");
		}
		if (domain.getValor().compareTo(BigDecimal.ZERO) <= 0) {
			throw new CrudException(CrudException.WARN_EXCEPTION_TYPE, "O valor digitado deve ser maior que zero.");
		} 
		
		domain.setIndicadorAtivo(true);

	}

	@Override
	public void salvar(ActionEvent arg0) {
		try {

			if (getEstadoCrud() == CrudState.ST_INSERT) {
				this.antesSalvar();
				produtoBO.salvarProduto(domain);
				this.msgInfo("Produto cadastrado com sucesso!");
				this.addToList(getDomain());
			}

			if (getEstadoCrud() == CrudState.ST_EDIT) {
				this.antesSalvar();
				produtoBO.salvarProduto(domain);
				this.msgInfo("Produto cadastrado com sucesso!");
				getListagem().clear();
				this.addToList(getDomain());
			}

			this.configuraEstado(CrudState.ST_DEFAULT);

		} catch (CrudException | PersistenceException e) {
			this.msgWarn(e.getMessage());
			e.printStackTrace();
		}

	}

}
