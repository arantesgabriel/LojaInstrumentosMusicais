package pxt.etq.domain.business.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.pxt.loja.persistence.dao.PedidoDAO;
import com.pxt.loja.persistence.dao.ProdutoDAO;

import pxt.etq.domain.estoque.Produto;
import pxt.framework.business.TransactionException;

@Stateless
public class PedidoBO {

	@EJB
	private PedidoDAO pedidoDao;

	@EJB
	private ProdutoDAO produtoDAO;

	public void salvarPedido(Produto domain) throws TransactionException {

	}
}