package pxt.etq.domain.business.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.pxt.loja.persistence.dao.EstoqueDAO;

import pxt.etq.domain.estoque.Estoque;
import pxt.framework.business.TransactionException;
import pxt.framework.persistence.PersistenceException;
import pxt.framework.validation.ValidationException;

@Stateless
public class EstoqueBO {

	@EJB
	private EstoqueDAO estoqueDao;

	public void salvarEstoqueRecebimento(Estoque domain)
			throws ValidationException, TransactionException, PersistenceException {

		// Busca o estoque atual utilizando o Criteria.
		Estoque estoqueAtual = estoqueDao.buscarEstoqueAtual(domain.getProduto().getCodigo());

		// Valida se a quantidade é menor ou igual a zero.
		if (domain.getQuantidadeRecebimento() <= 0) {
			throw new ValidationException("A quantidade a ser recebida deve ser maior que zero.");
		}

		// Insere a quantidade recebida no recebimento de estoque.
		estoqueAtual.setQuantidadeRecebimento(domain.getQuantidadeRecebimento() + estoqueAtual.getQuantidadeRecebimento());
		estoqueDao.saveOrUpdate(estoqueAtual);

	}

	public void salvarEstoqueLiberacao(Estoque domain)
			throws PersistenceException, ValidationException, TransactionException {
		
		// Busca o estoque atual utilizando o Criteria.
		Estoque estoqueAtual = estoqueDao.buscarEstoqueAtual(domain.getProduto().getCodigo());

		// Validação se a quantidade digitada para cadastro do produto no estoque é válida.
		if (domain.getQuantidade() <= 0) {
			throw new ValidationException("A quantidade a ser liberada ao estoque deve ser maior que zero.");
		} else if (domain.getQuantidade() > estoqueAtual.getQuantidadeRecebimento()) {
			throw new ValidationException("A quantidade digitada é maior que o estoque em recebimento.");
		} else {
			
			// Atualiza o estoque removendo a quantidade digitada do estoque recebimento e inserindo no estoque real.
			estoqueAtual.setQuantidade(domain.getQuantidade() + estoqueAtual.getQuantidade());
			estoqueAtual.setQuantidadeRecebimento(estoqueAtual.getQuantidadeRecebimento() - domain.getQuantidade());
			estoqueDao.saveOrUpdate(estoqueAtual);
		}

	}

}
