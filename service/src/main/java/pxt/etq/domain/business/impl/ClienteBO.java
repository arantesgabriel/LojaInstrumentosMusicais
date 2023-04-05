package pxt.etq.domain.business.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.pxt.loja.persistence.dao.ClienteDAO;

import pxt.etq.domain.estoque.Cliente;
import pxt.framework.persistence.PersistenceException;
import pxt.framework.validation.ValidationException;

@Stateless
public class ClienteBO {

	@EJB
	private ClienteDAO clienteDAO;

	public void validarCliente(Cliente domain) throws ValidationException, PersistenceException {

		// Busca o cliente utilizando Criteria.
		Cliente cliente = clienteDAO.buscarClientePorCpfCnpj(domain.getCpfCnpj());
		
		// Efetua as valida��es de nome e CPF/CNPJ para cadastro do cliente.
		if (domain.getNome() == null || domain.getNome().isEmpty()) {
			throw new ValidationException("O campo nome � obrigat�rio.");
		} else if (domain.getNome().length() < 3){
			throw new ValidationException("O campo nome precisa ter no m�nimo tr�s caracteres");
		} else if (domain.getCpfCnpj() == null || domain.getCpfCnpj().isEmpty()) {
			throw new ValidationException("O campo CPF/CNPJ � obrigat�rio.");
		} else if (domain.getCpfCnpj().length() < 11) {
			throw new ValidationException("O campo CPF/CNPJ precisa ter no m�nimo 11 d�gitos.");
		} else if (domain.getCpfCnpj().equals(cliente.getCpfCnpj())) {
			throw new ValidationException("O CPF/CNPJ digitado j� est� cadastrado no sistema.");
		} else
			// Cadastra o cliente.
			try {
				clienteDAO.save(domain);
			} catch (PersistenceException e) {
				throw new PersistenceException("Erro ao cadastrar cliente.");
			}
	}
}