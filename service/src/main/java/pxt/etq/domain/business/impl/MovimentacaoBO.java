package pxt.etq.domain.business.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.pxt.loja.persistence.dao.MovimentacaoDAO;

import pxt.etq.domain.estoque.EstoqueMovimentacao;
import pxt.framework.persistence.PersistenceException;

@Stateless
public class MovimentacaoBO {

	@EJB
	private MovimentacaoDAO movimentacaoDAO;

	public void criarMovimentacao(EstoqueMovimentacao novaMovimentacao) {
		
		try {
			EstoqueMovimentacao movimentacaoEstoque = new EstoqueMovimentacao();
			movimentacaoEstoque.setData(novaMovimentacao.getData());
			movimentacaoEstoque.setProduto(novaMovimentacao.getProduto());
			movimentacaoEstoque.setQuantidadeMovimentada(novaMovimentacao.getQuantidadeMovimentada());
			movimentacaoEstoque.setTipoOperacao(novaMovimentacao.getTipoOperacao());
			movimentacaoDAO.save(movimentacaoEstoque);
		} catch (PersistenceException e) {
			e.printStackTrace();
		}

	}

}
