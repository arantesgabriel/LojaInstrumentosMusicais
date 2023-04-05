package com.pxt.loja.persistence.dao;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import pxt.etq.domain.estoque.Fornecedor;
import pxt.framework.persistence.PersistenceException;

@Stateless
public class FornecedorDAO extends GenericDAO {

	public Fornecedor buscarPorNomeFornecedor(String nome) throws PersistenceException {

		try {

			Criteria criteria = getSession().createCriteria(Fornecedor.class);

			if (nome != null) {
				criteria.add(Restrictions.eq("nome", nome));
			}

			return (Fornecedor) criteria.uniqueResult();

		} catch (Exception e) {
			throw new PersistenceException("Erro ao buscar fornecedor", e);

		}
	}

	public Fornecedor buscarPorCnpjFornecedor(String cnpj) throws PersistenceException {

		try {

			Criteria criteria = getSession().createCriteria(Fornecedor.class);

			if (cnpj != null) {
				criteria.add(Restrictions.eq("cnpj", cnpj));
			}

			return (Fornecedor) criteria.uniqueResult();

		} catch (Exception e) {
			throw new PersistenceException("Erro ao buscar fornecedor", e);

		}
	}
}
