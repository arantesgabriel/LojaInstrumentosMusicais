package pxt.etq.domain.business.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.pxt.loja.persistence.dao.FornecedorDAO;

import pxt.etq.domain.estoque.Fornecedor;
import pxt.framework.persistence.PersistenceException;

@Stateless
public class FornecedorBO {

	@EJB
	private FornecedorDAO fornecedorDAO;

	public void cadastrarFornecedor(Fornecedor domain) throws PersistenceException {

		try {
			fornecedorDAO.save(domain);
		} catch (PersistenceException e) {
			throw new PersistenceException("Erro ao cadastrar fornecedor.");
		}
	}

}