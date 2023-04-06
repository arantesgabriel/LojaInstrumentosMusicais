package pxt.etq.domain.business.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.pxt.loja.persistence.dao.EstoqueDAO;
import com.pxt.loja.persistence.dao.ProdutoDAO;

import pxt.etq.domain.estoque.Estoque;
import pxt.etq.domain.estoque.Produto;
import pxt.framework.persistence.PersistenceException;

@Stateless
public class ProdutoBO {

	@EJB
	private EstoqueDAO estoqueDao;

	@EJB
	private EstoqueBO estoqueBO;

	@EJB
	private ProdutoDAO produtoDAO;

	public void salvarProduto(Produto domain) throws PersistenceException {

		// Cria o produto e j� o cadastra com o estoque zerado.
		try {
			Estoque estoque = new Estoque();
			estoque.setProduto(domain);
			estoque.setQuantidade(0);
			estoque.setQuantidadeRecebimento(0);
			produtoDAO.save(domain);
			estoqueDao.save(estoque);
		} catch (PersistenceException e) {
			e.printStackTrace();
		}

	}

}
