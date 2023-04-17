package pxt.etq.domain.business.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.pxt.loja.persistence.dao.EstoqueDAO;

import pxt.etq.domain.estoque.Estoque;
import pxt.etq.domain.estoque.Produto;
import pxt.framework.business.TransactionException;
import pxt.framework.persistence.PersistenceException;
import pxt.framework.validation.ValidationException;

@Stateless
public class EstoqueBO {

	@EJB
	private EstoqueDAO estoqueDao;

	public void salvarEstoqueRecebimento(Estoque domain) throws PersistenceException, ValidationException {

		if (domain.getQuantidadeRecebimento() <= 0) {
			throw new ValidationException("Quantidade do produto não pode ser menor ou igual a zero");
		}

		// Busca o estoque atual utilizando o Criteria.
		Estoque estoqueAtual = estoqueDao.buscarEstoqueAtual(domain.getProduto().getCodigo());

		// Insere a quantidade recebida no recebimento de estoque.
		estoqueAtual.setQuantidadeRecebimento(domain.getQuantidadeRecebimento() + estoqueAtual.getQuantidadeRecebimento());
		estoqueDao.saveOrUpdate(estoqueAtual);

	}

	public void salvarEstoqueLiberacao(Estoque domain) throws PersistenceException, ValidationException {

		if (domain.getQuantidade() <= 0) {
			throw new ValidationException("Quantidade do produto não pode ser menor ou igual a zero");
		}

		// Busca o estoque atual utilizando o Criteria.
		Estoque estoqueAtual = estoqueDao.buscarEstoqueAtual(domain.getProduto().getCodigo());

		// Atualiza o estoque removendo a quantidade digitada do estoque recebimento e
		// inserindo no estoque real.
		estoqueAtual.setQuantidade(domain.getQuantidade() + estoqueAtual.getQuantidade());
		estoqueAtual.setQuantidadeRecebimento(estoqueAtual.getQuantidadeRecebimento() - domain.getQuantidade());
		estoqueDao.saveOrUpdate(estoqueAtual);
	}
	
	public void criarEstoque(Produto domain) throws TransactionException {
		try {
			Estoque estoque = new Estoque();
			estoque.setProduto(domain);
			estoque.setQuantidade(0);
			estoque.setQuantidadeRecebimento(0);
			estoqueDao.save(estoque);
		} catch (Exception e) {
			throw new TransactionException("Erro ao criar estoque", e);
		}
	}
	
}
