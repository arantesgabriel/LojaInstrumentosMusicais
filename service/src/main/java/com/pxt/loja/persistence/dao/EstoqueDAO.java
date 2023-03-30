package com.pxt.loja.persistence.dao;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import pxt.etq.domain.estoque.Estoque;
import pxt.framework.persistence.PersistenceException;

@Stateless
public class EstoqueDAO extends GenericDAO {

	public Estoque buscarEstoqueAtual(Integer codigoProduto) throws PersistenceException {

		try {

			Criteria criteria = getSession().createCriteria(Estoque.class);

			if (codigoProduto != null) {
				criteria.add(Restrictions.eq("produto.codigo", codigoProduto));
			}
			return (Estoque) criteria.uniqueResult();
		} catch (Exception e) {
			throw new PersistenceException("Erro ao buscar estoque atual", e);
		}
	}
}
