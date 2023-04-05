package pxt.etq.domain.business.impl;

import java.math.BigDecimal;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.pxt.loja.persistence.dao.EstoqueDAO;
import com.pxt.loja.persistence.dao.ProdutoDAO;

import pxt.etq.domain.estoque.Estoque;
import pxt.etq.domain.estoque.Produto;
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

	public void salvarProduto(Produto domain) throws ValidationException, PersistenceException {

		// Valida se a descri��o � nula ou vazia.
		if (domain.getDescricao() == null || domain.getDescricao().isEmpty()) {
			throw new ValidationException("O campo descri��o � obrigat�rio.");
		} 
		// Valida quantidade de caracteres no campo nome.
		else if (domain.getDescricao().length() < 5) {
			throw new ValidationException("� necess�rio ter no m�nimo 5 caracteres no campo descri��o");
		} 
		// Valida se o valor digitado � valido.
		else if (domain.getValor().compareTo(BigDecimal.ZERO) == 0) {
			throw new ValidationException("O valor digitado deve ser maior que zero.");
		} else {
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

}