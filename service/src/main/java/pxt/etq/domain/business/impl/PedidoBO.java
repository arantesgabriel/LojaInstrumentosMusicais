package pxt.etq.domain.business.impl;

import java.math.BigDecimal;
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
import pxt.framework.persistence.PersistenceException;
import pxt.framework.validation.ValidationException;

@Stateless
public class PedidoBO {

	@EJB
	private PedidoDAO pedidoDao;
	@EJB
	private ItemPedidoDAO itemPedidoDao;
	@EJB
	private ProdutoDAO produtoDAO;
	@EJB
	private ClienteDAO clienteDAO;
	@EJB
	private EstoqueDAO estoqueDAO;
	@EJB
	private MovimentacaoBO movimentacaoBO;
	private BigDecimal total = BigDecimal.ZERO;
	List<ItemPedido> itemPedido = new ArrayList<ItemPedido>();

	public void efetuarPedido(Pedido pedido, List<ItemPedido> listaDeItens)
			throws PersistenceException, ValidationException {

		Date data = new Date();
		pedido.setData(data);
		pedidoDao.saveOrUpdate(pedido);

		for (ItemPedido itemPedido : listaDeItens) {
			itemPedido.setPedido(pedido);
			alterarEstoque(itemPedido.getProduto(), itemPedido.getQuantidade());
			itemPedidoDao.save(itemPedido);
			EstoqueMovimentacao novaMovimentacao = new EstoqueMovimentacao();
			novaMovimentacao.setProduto(itemPedido.getProduto());
			novaMovimentacao.setData(data);
			novaMovimentacao.setQuantidadeMovimentada(itemPedido.getQuantidade());
			novaMovimentacao.setTipoOperacao(TipoOperacao.VENDA);
			movimentacaoBO.criarMovimentacao(novaMovimentacao);
		}

	}

	public void alterarEstoque(Produto produto, Integer quantidade) throws ValidationException {

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

	public BigDecimal calcularTotalPedido(BigDecimal preco, Integer quantidade) {

		total = total.add(preco.multiply(new BigDecimal(quantidade)));
		return total;
	}

}