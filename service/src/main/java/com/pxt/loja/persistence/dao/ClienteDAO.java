package com.pxt.loja.persistence.dao;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import pxt.etq.domain.estoque.Cliente;
import pxt.framework.persistence.PersistenceException;

@Stateless
public class ClienteDAO extends GenericDAO {

	public Cliente buscarClientePorCpfCnpj(String cpfCnpj) throws PersistenceException {

		try {
			Criteria criteria = getSession().createCriteria(Cliente.class);
			if (cpfCnpj != null) {
				criteria.add(Restrictions.eq("cliente.cpfCnpj", cpfCnpj));
			}
			return (Cliente) criteria.uniqueResult();
		} catch (Exception e) {
			throw new PersistenceException("Erro ao buscar cliente", e);
		}
	}
}
