/**
 * ESUP-Portail Example Application - Copyright (c) 2011 ESUP-Portail consortium.
 */
package org.esupportail.example.web.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.esupportail.commons.utils.BeanUtils;
import org.esupportail.example.domain.DomainService;

import com.thoughtworks.xstream.XStream;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2011
 * 
 */
public class JSONServlet extends HttpServlet {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = -7668753452311942420L;

	/**
	 * The domain service.
	 */
	private DomainService domainService = (DomainService) BeanUtils
			.getBean("domainService");

	/**
	 * The object mapper.
	 */
	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * The writer.
	 */
	private PrintWriter writer;

	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse res) {
		// nothing to do yet
	}
}
