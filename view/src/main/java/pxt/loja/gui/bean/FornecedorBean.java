package pxt.loja.gui.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import pxt.etq.domain.business.impl.FornecedorBO;
import pxt.etq.domain.estoque.Fornecedor;
import pxt.framework.business.PersistenceService;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.controller.CrudState;
import pxt.framework.faces.exception.CrudException;
import pxt.framework.persistence.PersistenceException;

@ManagedBean
@ViewScoped
public class FornecedorBean extends CrudController<Fornecedor> {
	private static final long serialVersionUID = 1L;

	private Fornecedor domain;

	@EJB
	private FornecedorBO fornecedorBO;

	@EJB
	private PersistenceService persistenceService;

	@Override
	public Fornecedor getDomain() {

		if (domain == null) {
			domain = new Fornecedor();
		}
		return domain;

	}

	@Override
	public void setDomain(Fornecedor domain) {
		this.domain = domain;
	}

	@Override
	public PersistenceService getPersistenceService() {
		return persistenceService;
	}

	@Override
	protected void antesSalvar() throws CrudException {

		// Validações do campo nome.
		if (domain.getNome() == null || domain.getNome().isEmpty()) {
			throw new CrudException(CrudException.WARN_EXCEPTION_TYPE, "O campo nome é obrigatório.");
		}

		if (domain.getNome().length() < 3) {
			throw new CrudException(CrudException.WARN_EXCEPTION_TYPE, "É necessário ter no mínimo três caracteres no campo nome.");
		}

		// Validações do campo CNPJ.
		if (domain.getCnpj() == null || domain.getCnpj().isEmpty()) {
			throw new CrudException(CrudException.WARN_EXCEPTION_TYPE, "O campo CPF/CNPJ é obrigatório.");
		}
		if (domain.getCnpj().length() < 14) {
			throw new CrudException(CrudException.WARN_EXCEPTION_TYPE, "O campo CNPJ precisa ter 14 dígitos.");
		}

	}

	@Override
	public void salvar(ActionEvent arg0) {
		try {

			if (getEstadoCrud() == CrudState.ST_INSERT) {
				this.antesSalvar();
				fornecedorBO.cadastrarFornecedor(domain);
				msgInfo("Fornecedor cadastrado com sucesso!");
				this.addToList(getDomain());
			}

			if (getEstadoCrud() == CrudState.ST_EDIT) {
				this.antesSalvar();
				fornecedorBO.cadastrarFornecedor(domain);
				msgInfo("Fornecedor cadastrado com sucesso!");
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
