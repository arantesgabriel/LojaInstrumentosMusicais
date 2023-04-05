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
import pxt.framework.persistence.PersistenceException;
import pxt.framework.validation.ValidationException;

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
	public void salvar(ActionEvent arg0) {
		try {
			this.addToList(getDomain());
			getEstadoCrud();
			this.configuraEstado(CrudState.ST_DEFAULT);
			clienteBO.validarCliente(domain);
			msgInfo("Cliente cadastrado com sucesso!");
		} catch (ValidationException e) {
			msgWarn(e.getMessage());
			e.printStackTrace();
		} catch (PersistenceException e) {
			msgWarn(e.getMessage());
			e.printStackTrace();
		}
	}

}
