package com.pxt.loja.persistence.dao;

import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import pxt.etq.domain.estoque.Produto;
import pxt.framework.persistence.PersistenceException;

@Stateless
public class ProdutoDAO extends GenericDAO {

	public List<Produto> buscarProduto(Produto produto) throws PersistenceException {

		try {
			Criteria criteria = getSession().createCriteria(Produto.class);
			criteria.createAlias("fornecedor", "fn");
			if (produto != null) {
				criteria.add(Restrictions.like("descricao", produto.getDescricao()));
				criteria.add(Restrictions.like("fn.nome", produto.getFornecedor().getNome()));
			}
			return criteria.list();
		} catch (Exception e) {
			throw new PersistenceException("Erro ao buscar produto", e);
		}
	}

}
