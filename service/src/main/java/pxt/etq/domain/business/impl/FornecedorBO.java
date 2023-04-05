package pxt.etq.domain.business.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.pxt.loja.persistence.dao.FornecedorDAO;

import pxt.etq.domain.estoque.Fornecedor;
import pxt.framework.persistence.PersistenceException;
import pxt.framework.validation.ValidationException;

@Stateless
public class FornecedorBO {

	@EJB
	private FornecedorDAO fornecedorDAO;

	public void validarNome(Fornecedor domain) throws ValidationException, PersistenceException {

		// Busca o fornecedor utilizando o Criteria.
		Fornecedor fornecedor = fornecedorDAO.buscarPorNomeFornecedor(domain.getNome());

		// Valida se o nome digitado � valido.
		if (domain.getNome() == null || domain.getNome().isEmpty()) {
			throw new ValidationException("O campo nome � obrigat�rio.");
		} 
		// Valida quantidade de caracteres no campo nome.
		else if (domain.getNome().length() < 3) {
			throw new ValidationException("� necess�rio ter no m�nimo tr� caracteres no campo nome");
		}
		// Valida se j� existe um fornecedor com o nome digitado.
		else if (domain.getNome().equals(fornecedor.getNome())) {
			throw new ValidationException("O fornecedor j� est� cadastrado no sistema.");
		} 
		// Efetua o cadastro do fornecedor.
		else
			try {
				fornecedorDAO.save(domain);
			} catch (PersistenceException e) {
				throw new PersistenceException("Erro ao cadastrar fornecedor.");
			}
	}

	public void validarCnpj(Fornecedor domain) throws ValidationException, PersistenceException {

		// Busca o fornecedor utilizando o Criteria.
		Fornecedor fornecedor = fornecedorDAO.buscarPorCnpjFornecedor(domain.getCnpj());

		// Valida se o CNPJ digitado � valido.
		if (domain.getCnpj() == null || domain.getCnpj().isEmpty()) {
			throw new ValidationException("O campo CPF/CNPJ � obrigat�rio.");
		} 
		// Valida se todos os d�gitos do CNPJ foram digitados.
		else if (domain.getCnpj().length() < 14) {
			throw new ValidationException("O campo CNPJ precisa ter 14 d�gitos.");
		} 
		// Valida se j� existe um CNPJ id�ntico ao digitado.
		else if (domain.getCnpj().equals(fornecedor.getCnpj())) {
			throw new ValidationException("O fornecedor j� est� cadastrado no sistema.");
		} 
		// Efetua o cadastro do fornecedor.
		else
			try {
				fornecedorDAO.save(domain);
			} catch (PersistenceException e) {
				throw new PersistenceException("Erro ao cadastrar fornecedor.");
			}
	}

}