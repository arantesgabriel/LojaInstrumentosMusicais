package pxt.etq.domain.business.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.pxt.loja.persistence.dao.ClienteDAO;

import pxt.etq.domain.estoque.Cliente;
import pxt.framework.persistence.PersistenceException;

@Stateless
public class ClienteBO {

	@EJB
	private ClienteDAO clienteDAO;

	public void cadastrarCliente(Cliente domain) throws PersistenceException {

		// Cadastra o cliente.
		try {
			clienteDAO.save(domain);
		} catch (PersistenceException e) {
			throw new PersistenceException("Erro ao cadastrar cliente.");
		}
	}
}