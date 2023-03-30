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

	public void salvarEstoqueRecebimento(Estoque domain) throws TransactionException {
		try {
			Estoque estoqueAtual = estoqueDao.buscarEstoqueAtual(domain.getProduto().getCodigo());
			estoqueAtual.setQuantidadeRecebimento(
					domain.getQuantidadeRecebimento() + estoqueAtual.getQuantidadeRecebimento());
			estoqueDao.saveOrUpdate(estoqueAtual);
		} catch (Exception e) {
			throw new TransactionException("Erro ao efetuar recebimento");
		}

	}

	public void salvarEstoqueLiberacao(Estoque domain) throws PersistenceException, ValidationException, TransactionException {

		try {

			Estoque estoqueAtual = estoqueDao.buscarEstoqueAtual(domain.getProduto().getCodigo());
			
			if (domain.getQuantidade() > estoqueAtual.getQuantidadeRecebimento()) {
				throw new ValidationException();
			} else {
				estoqueAtual.setQuantidade(domain.getQuantidade() + estoqueAtual.getQuantidade());
				estoqueAtual.setQuantidadeRecebimento(estoqueAtual.getQuantidadeRecebimento() - domain.getQuantidade());
				estoqueDao.saveOrUpdate(estoqueAtual);
			}

		} catch (Exception e) {
			throw new ValidationException("Quantidade digitada maior que o estoque em recebimento.", e);

		}
	}

}
