package pxt.etq.domain.business.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.pxt.loja.persistence.dao.EstoqueDAO;
import com.pxt.loja.persistence.dao.ProdutoDAO;

import pxt.etq.domain.estoque.Estoque;
import pxt.etq.domain.estoque.Produto;
import pxt.framework.business.TransactionException;

@Stateless
public class ProdutoBO {

	@EJB
	private EstoqueDAO estoqueDao;

	@EJB
	private EstoqueBO estoqueBO;

	@EJB
	private ProdutoDAO produtoDAO;

	public void salvarProduto(Produto domain) throws TransactionException {

		try {
			
			Estoque estoque = new Estoque();
			estoque.setProduto(domain);
			estoque.setQuantidade(0);
			estoque.setQuantidadeRecebimento(0);

			produtoDAO.save(domain);
			estoqueDao.save(estoque);

		} catch (Exception e) {
			throw new TransactionException("Erro ao criar produto");
		}

	}

}