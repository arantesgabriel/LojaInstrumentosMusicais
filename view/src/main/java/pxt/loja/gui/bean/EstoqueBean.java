package pxt.loja.gui.bean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pxt.etq.domain.business.impl.EstoqueBO;
import pxt.etq.domain.estoque.Estoque;
import pxt.framework.business.PersistenceService;
import pxt.framework.faces.controller.CrudController;
import pxt.framework.faces.exception.CrudException;

@ManagedBean
@ViewScoped
public class EstoqueBean extends CrudController<Estoque> {
	private static final long serialVersionUID = 1L;

	private Estoque domain;

	@EJB
	private EstoqueBO estoqueBO;

	@EJB
	private PersistenceService persistenceService;

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

	@Override
	protected void salvar() throws CrudException {
		try {
			estoqueBO.salvarEstoqueRecebimento(domain);
		} catch (Exception e) {
			this.msgWarn(e.getMessage());
			e.printStackTrace();
		}
	}

}
