package pxt.etq.domain.business.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.pxt.loja.persistence.dao.EstoqueDAO;
import com.pxt.loja.persistence.dao.ProdutoDAO;

import pxt.etq.domain.estoque.Produto;
import pxt.framework.business.TransactionException;
import pxt.framework.persistence.PersistenceException;
import pxt.framework.validation.ValidationException;

@Stateless
public class ProdutoBO {

	@EJB
	private EstoqueDAO estoqueDao;

	@EJB
	private EstoqueBO estoqueBO;

	@EJB
	private ProdutoDAO produtoDAO;

	public void salvarProduto(Produto domain) throws TransactionException, ValidationException, PersistenceException {

		// Cria o produto e já o cadastra com o estoque zerado.

		if (produtoDAO.buscarProduto(domain).isEmpty()) {
			produtoDAO.saveOrUpdate(domain);
			estoqueBO.criarEstoque(domain);
		} else {
			throw new ValidationException("Produto já cadastrado");
		}

	}

}
