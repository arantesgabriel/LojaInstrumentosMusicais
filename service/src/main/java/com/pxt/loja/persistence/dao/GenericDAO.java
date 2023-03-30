package com.pxt.loja.persistence.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.Clustered;

import pxt.framework.persistence.DataAccessObject;

@SuppressWarnings("rawtypes")
@Stateless(name = "loja." + DataAccessObject.SERVICE_NAME)
@Clustered
@Local(DataAccessObject.class)
public class GenericDAO extends LOJAHibernateDAO {}