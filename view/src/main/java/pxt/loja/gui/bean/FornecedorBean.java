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
import pxt.framework.persistence.PersistenceException;
import pxt.framework.validation.ValidationException;

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
	public void salvar(ActionEvent arg0) {
		try {
			this.addToList(getDomain());
			getEstadoCrud();
			this.configuraEstado(CrudState.ST_DEFAULT);
			fornecedorBO.validarNome(domain);
			fornecedorBO.validarCnpj(domain);
			msgInfo("Fornecedor cadastrado com sucesso!");
		} catch (ValidationException e) {
			this.msgWarn(e.getMessage());
			e.printStackTrace();
		} catch (PersistenceException e) {
			this.msgWarn(e.getMessage());
			e.printStackTrace();
		}
	}

}
