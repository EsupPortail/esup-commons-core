/**
 * ESUP-Portail Example Application - Copyright (c) 2011 ESUP-Portail consortium.
 */
package org.esupportail.example.web.jsf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.FSEntityResolver;

import com.lowagie.text.DocumentException;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2011
 * 
 */
public class RendererFilter implements Filter {

	/**
	 * For logging.
	 */
	private final Logger logger = new LoggerImpl(getClass());

	/**
	 * the document builder.
	 */
	private DocumentBuilder documentBuilder;

	@Override
	public void destroy() {
		// nothing to do yet.
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("------------------- Flyingsaucer Filter Started ----------------- ");
		}
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		// Capture the content for this request
		ContentCaptureServletResponse capContent = new ContentCaptureServletResponse(
				response);
		// The name page
		String referer = request.getHeader("Referer");
		if (referer == null || "".equals(referer)) {
			referer = "unknow.htm";
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Referer: " + referer);
		}
		int pageIndex = referer.lastIndexOf("%2F") + 3;
		if (pageIndex == 2) {
			pageIndex = referer.lastIndexOf("/") + 1;
		}
		int endPageIndex = referer.lastIndexOf(".xhtml");
		String page = referer.substring(pageIndex, endPageIndex);
		if (logger.isDebugEnabled()) {
			logger.debug("Page: " + page);
		}
		capContent.setHeader("Content-Disposition", "attachment; filename=\""
				+ page + ".pdf\"");
		capContent.setHeader("Pragma", "public");
		capContent.setHeader("Cache-Control", "max-age=0");
		capContent.setHeader("pre-filter", "true");
		// Update chain filter
			filterChain.doFilter(request, capContent);
		StringReader contentReader = new StringReader(capContent.getContent());
		FSEntityResolver er = FSEntityResolver.instance();
		documentBuilder.setEntityResolver(er);
		if (logger.isDebugEnabled()) {
			logger.debug("------------ Flyingsaucer Filter Capture available -------------- ");
		}
		Tidy tidy = new Tidy();
		Document xhtmlContent = tidy.parseDOM(contentReader, null);
		removeAll(xhtmlContent, Node.ELEMENT_NODE, "script");
		removeAll(xhtmlContent, Node.ELEMENT_NODE, "noscript");
		ITextRenderer renderer = new ITextRenderer();
		String baseURL = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath()
				+ "/";
		String self = baseURL.substring(0, baseURL.lastIndexOf("/") + 1);
		renderer.setDocument(xhtmlContent,  self);
		renderer.layout();
		OutputStream browserStream;
		// Change the nature of the response
		response.setHeader("Content-Type", "application/pdf");
		browserStream = response.getOutputStream();
		try {
			renderer.createPDF(browserStream);
		} catch (DocumentException e) {
			throw new ServletException(e);
		}
			browserStream.flush();
			browserStream.close();
		if (logger.isDebugEnabled()) {
			logger.debug("------------------- Flyingsaucer Filter Stopped ----------------- ");
		}
	}

	/**
	 * @param node
	 * @param nodeType
	 * @param name
	 *            This method walks the document and removes all nodes of the
	 *            specified type and specified name. If name is null, then the
	 *            node is removed if the type matches.
	 */
	private static void removeAll(Node node, short nodeType, String name) {
		if (node.getNodeType() == nodeType
				&& (name == null || node.getNodeName().equals(name))) {
			node.getParentNode().removeChild(node);
		} else { // Visit the children
			NodeList list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				removeAll(list.item(i), nodeType, name);
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			documentBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new ServletException(e);
		}
	}

}
