package pxt.loja.gui.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pxt.etq.domain.estoque.Fornecedor;
import pxt.framework.business.PersistenceService;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.exception.CrudException;

@ManagedBean
@ViewScoped
public class FornecedorBean extends CrudController<Fornecedor> {
	private static final long serialVersionUID = -3169156875226822858L;

	private Fornecedor domain;

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
		if (domain.getNome() == null || domain.getNome().isEmpty()) {
			this.msgWarn("O campo nome é obrigatório.");
		}

		if (domain.getCnpj() == null || domain.getCnpj().isEmpty()) {
			this.msgWarn("O campo CPF/CNPJ é obrigatório.");
		}

	}

}
