package pxt.loja.gui.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import pxt.etq.domain.business.impl.ClienteBO;
import pxt.etq.domain.estoque.Cliente;
import pxt.framework.business.PersistenceService;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.controller.CrudState;
import pxt.framework.faces.exception.CrudException;
import pxt.framework.persistence.PersistenceException;

@ManagedBean
@ViewScoped
public class ClienteBean extends CrudController<Cliente> {
	private static final long serialVersionUID = 1L;

	private Cliente domain;

	@EJB
	private ClienteBO clienteBO;

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
	protected void antesSalvar() throws CrudException {

		if (domain.getNome() == null || domain.getNome().isEmpty()) {
			throw new CrudException(CrudException.WARN_EXCEPTION_TYPE, "O campo nome é obrigatório.");
		}
		if (domain.getNome().length() < 3) {
			throw new CrudException(CrudException.WARN_EXCEPTION_TYPE, "O campo nome precisa ter no mínimo três caracteres");
		}
		if (domain.getCpfCnpj() == null || domain.getCpfCnpj().isEmpty()) {
			throw new CrudException(CrudException.WARN_EXCEPTION_TYPE, "O campo CPF/CNPJ é obrigatório.");
		}
		if (domain.getCpfCnpj().length() < 11) {
			throw new CrudException(CrudException.WARN_EXCEPTION_TYPE, "O CPF informado precisa ter 11 dígitos");
		}
		if (domain.getCpfCnpj().length() > 11 && domain.getCpfCnpj().length() < 14) {
			throw new CrudException(CrudException.WARN_EXCEPTION_TYPE, "O CPNJ informado precisa ter 14 dígitos");
		}
		
		super.antesSalvar();

	}

	@Override
	public void salvar(ActionEvent arg0) {
		try {

			if (getEstadoCrud() == CrudState.ST_INSERT) {
				this.antesSalvar();
				clienteBO.cadastrarCliente(domain);
				msgInfo("Cliente cadastrado com sucesso!");
				this.addToList(getDomain());
			}

			if (getEstadoCrud() == CrudState.ST_EDIT) {
				this.antesSalvar();
				clienteBO.cadastrarCliente(domain);
				msgInfo("Cliente cadastrado com sucesso!");
				getListagem().clear();
				this.addToList(getDomain());
			}

			this.configuraEstado(CrudState.ST_DEFAULT);

		} catch (CrudException | PersistenceException e) {
			msgWarn(e.getMessage());
			e.printStackTrace();
		}
	}

}
