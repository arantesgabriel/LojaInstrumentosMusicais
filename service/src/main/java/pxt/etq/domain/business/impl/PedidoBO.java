package pxt.etq.domain.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.pxt.loja.persistence.dao.ClienteDAO;
import com.pxt.loja.persistence.dao.EstoqueDAO;
import com.pxt.loja.persistence.dao.ItemPedidoDAO;
import com.pxt.loja.persistence.dao.PedidoDAO;
import com.pxt.loja.persistence.dao.ProdutoDAO;

import pxt.etq.domain.estoque.Estoque;
import pxt.etq.domain.estoque.EstoqueMovimentacao;
import pxt.etq.domain.estoque.ItemPedido;
import pxt.etq.domain.estoque.Pedido;
import pxt.etq.domain.estoque.Produto;
import pxt.etq.domain.estoque.TipoOperacao;
import pxt.framework.business.TransactionException;
import pxt.framework.persistence.PersistenceException;
import pxt.framework.validation.ValidationException;

@Stateless
public class PedidoBO {

	@EJB private PedidoDAO pedidoDao;
	@EJB private ItemPedidoDAO itemPedidoDao;
	@EJB private ProdutoDAO produtoDAO;
	@EJB private ClienteDAO clienteDAO;
	@EJB private EstoqueDAO estoqueDAO;

	List<ItemPedido> itemPedido = new ArrayList<ItemPedido>();

	public void efetuarPedido(ItemPedido domain, List<ItemPedido> listaDeItens) throws TransactionException, PersistenceException, ValidationException {

		Pedido pedido = new Pedido();
		Date data = new Date();
		pedido.setCliente(domain.getPedido().getCliente());
		pedido.setData(data);
		pedidoDao.saveOrUpdate(pedido);

		for (ItemPedido itemPedido : listaDeItens) {
			itemPedido.setPedido(pedido);
			itemPedidoDao.save(itemPedido);
		}

		EstoqueMovimentacao estoqueMovimentacao = new EstoqueMovimentacao();
		estoqueMovimentacao.setProduto(domain.getProduto());
		estoqueMovimentacao.setData(data);
		estoqueMovimentacao.setQuantidadeMovimentada(domain.getQuantidade());
		estoqueMovimentacao.setTipoOperacao(TipoOperacao.VENDA);
		decrementarEstoque(domain.getProduto(), domain.getQuantidade());

	}

	public void decrementarEstoque(Produto produto, Integer quantidade) throws ValidationException {

		try {
			
			Estoque estoqueAtual = estoqueDAO.buscarEstoqueAtual(produto.getCodigo());

			if (estoqueAtual.getQuantidade() < quantidade) {
				throw new ValidationException("A quantidade informada é maior que o estoque");
			} else {
				estoqueAtual.setQuantidade(estoqueAtual.getQuantidade() - quantidade);
				estoqueDAO.saveOrUpdate(estoqueAtual);
			}

		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}

}