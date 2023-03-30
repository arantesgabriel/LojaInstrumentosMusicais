package pxt.etq.domain.business.impl;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.Clustered;

import pxt.framework.business.BusinessObject;
import pxt.framework.business.PersistenceService;
import pxt.framework.persistence.DataAccessObject;

@Stateless(name = "loja." + PersistenceService.SERVICE_NAME)
@Clustered
@Local(PersistenceService.class)
public class GenericBO extends BusinessObject {

	@EJB(beanName = "loja." + DataAccessObject.SERVICE_NAME)
	@SuppressWarnings("rawtypes")
	private DataAccessObject genericDao;

	@Override
	@SuppressWarnings("unchecked")
	public <T> DataAccessObject<T, ? extends Serializable> getPersistence() {
		return this.genericDao;
	}

	@Override
	public <T> void setPersistence(DataAccessObject<T, ? extends Serializable> persistence) {
		this.genericDao = persistence;
	}

}