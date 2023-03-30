package pxt.loja.gui.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pxt.etq.domain.estoque.Cliente;
import pxt.framework.business.PersistenceService;
import pxt.framework.faces.controller.CrudController;

@ManagedBean
@ViewScoped
public class ClienteBean extends CrudController<Cliente> {
	private static final long serialVersionUID = 1L;

	private Cliente domain;

	@EJB
	private PersistenceService persistenceService;

	@Override
	public Cliente getDomain() {

		if (domain == null) {
			domain = new Cliente();
		}
		return domain;

	}

	@Override
	public void setDomain(Cliente domain) {
		this.domain = domain;
	}

	@Override
	public PersistenceService getPersistenceService() {
		return persistenceService;
	}

	@Override
	protected void antesSalvar() {
		if (domain.getNome() == null || domain.getNome().isEmpty()) {
			this.msgWarn("O campo nome é obrigatório.");
		}

		if (domain.getCpfCnpj() == null || domain.getCpfCnpj().isEmpty()) {
			this.msgWarn("O campo CPF/CNPJ é obrigatório.");
		}

	}
	
//	private void validarNome() {
//		
//	}
	
}
