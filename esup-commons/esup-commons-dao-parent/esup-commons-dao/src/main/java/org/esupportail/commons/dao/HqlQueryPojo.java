/**
 * CRI - Université de Rennes 1 - <nom du projet> - <année>
 * <url de gestion du projet>
 * Version de la norme de développement : <version de ce document> 
 */
/**
 * 
 */
package org.esupportail.commons.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.util.StringUtils;

/**
 * @author cleprous HqlQueryPojo : object to build the hql query for hibernate.
 */
public class HqlQueryPojo implements Serializable {

	/*
	 * ************************** PROPERTIES ********************************
	 */

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3823260726297359266L;

	/**
	 * A logger.
	 */
	private static final Logger log = new LoggerImpl(HqlQueryPojo.class);

	/**
	 * The statement for select part in hqlQuery. default value : empty
	 */
	private List<String> select;

	/**
	 * The statement for from part in hqlQuery. default value : empty
	 */
	private List<String> from;

	/**
	 * The statement for where part in hqlQuery. default value : empty
	 */
	private List<String> where;

	/*
	 * ************************** INIT **************************************
	 */

	/**
	 * Constructor.
	 */
	public HqlQueryPojo() {
		super();
		select = new ArrayList<String>();
		from = new ArrayList<String>();
		where = new ArrayList<String>();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HqlQueryPojo#" + hashCode() + "[select =" + select 
						+ "],[form =" + from + "], [where =" + where + "]";
	}

	/*
	 * ************************** METHODS ***********************************
	 */

	
	/**
	 * @return true is not hql.
	 */
	public boolean isEmpty() {
		return !StringUtils.hasText(buildHql());
	}
	
	/**
	 * @param hql
	 * @param l
	 * @param clause
	 * @param separator
	 */
	private StringBuilder appendHql(final List<String> l,
			final String clause, final String separator) {
		if (log.isDebugEnabled()) {
			log.debug("entering HqlQueryPojo.appendHql() with :");
			log.debug("l = " + l.toString());
			log.debug("clause = " + clause);
			log.debug("separator = " + separator);
		}
		StringBuilder hql = new StringBuilder();
		if (!l.isEmpty()) {
			hql.append(clause);
			int cpt = 0; 
			for (String s : l) {
				if (log.isDebugEnabled()) {
					log.debug(cpt + " : s = " + s);
				}
				if (cpt > 0) {
					hql.append(separator);
				}
				hql.append(s);
				cpt++;
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("exiting HqlQueryPojo.appendHql() with hql = " + hql.toString());
		}
		return hql;
	}

	/**
	 * @return String clause FROM
	 */
	public String geteClauseFrom() {
		return appendHql(from, " FROM ", ", ").toString();
	}
	
	/**
	 * @return String clause FROM
	 */
	public String geteClauseWhere() {
		return appendHql(where, " WHERE ", " AND ").toString();
	}
	
	/**
	 * @return the hql request.
	 */
	public String buildHql() {
		if (log.isDebugEnabled()) {
			log.debug("entering HqlQueryPojo.buildHql()");
		}

		StringBuilder hql = new StringBuilder();
		hql.append(appendHql(select, "SELECT ", ", "));
		hql.append(appendHql(from, " FROM ", ", "));
		hql.append(appendHql(where, " WHERE ", " AND "));

		log.info("exiting HqlQueryPojo.buildHql() with hql = " + hql.toString());
		return hql.toString();
	}

	
	/**
	 * @param s
	 */
	public void addSelect(final String s) {
		if (log.isDebugEnabled()) {
			log.debug("entering HqlQueryPojo.addSelect() with s = " + s);
		}
		if (StringUtils.hasText(s)) {
			select.add(s.trim());
		}
	}

	/**
	 * @param f
	 */
	public void addFrom(final String f) {
		if (log.isDebugEnabled()) {
			log.debug("entering HqlQueryPojo.addFrom() with f = " + f);
		}
		if (StringUtils.hasText(f)) {
			from.add(f.trim());
		}
	}

	/**
	 * @param w
	 */
	public void addWhere(final String w) {
		if (log.isDebugEnabled()) {
			log.debug("entering HqlQueryPojo.addWhere() with w = " + w);
		}
		if (StringUtils.hasText(w)) {
			where.add(w.trim());
		}
	}

	/**
	 * @param w
	 * @param v
	 * @param isInt
	 * @return String
	 */
	public static String formatWhere(final String w, final String v, final Boolean isInt) {
		if (log.isDebugEnabled()) {
			log.debug("entering HqlQueryPojo.formatWhere() with :");
			log.debug("where = " + w);
			log.debug("value = " + v);
			log.debug("isInt = " + isInt);
		}
		String result = w;
		String value = v;

		if (result.contains("=")) {
			if (value.startsWith("<") || value.startsWith(">")) {
				result = result.replace("=", value.substring(0, 1));
				value = value.replace(value.substring(0, 1), "");
			}

			if (isInt) {
				if (org.esupportail.commons.utils.strings.StringUtils.isInteger(value)) {
					result += value;
				}
				else {
					return null; // la valeur n'est pas un Integer
				}
			}
			else {
				result += "'" + value + "'";
			}
		}
		else if (result.contains("LIKE")) {
			result += "'%" + value + "%'";
		}

		if (log.isDebugEnabled()) {
			log.debug("exiting HqlQueryPojo.formatWhere() with result = " + result);
		}
		return result;
	}

	/**
	 * @param w
	 * @param values
	 * @param isInt
	 * @return String
	 */
	public static String formatWhere(final String w, final List<String> values, final Boolean isInt) {
		if (log.isDebugEnabled()) {
			log.debug("entering HqlQueryPojo.formatWhere() with :");
			log.debug("w = " + w);
			log.debug("values = " + values);
			log.debug("isInt = " + isInt);
		}
		String result = w;

		if (result.contains("IN")) {
			result += "(";
			for (int i = 0; i < values.size(); i++) {
				String value = values.get(i);
				if (isInt) {
					if (org.esupportail.commons.utils.strings.StringUtils.isInteger(value)) {
						if (i > 0) {
							result += ", ";
						}
						result += value;
					}
					else {
						return null; // la valeur n'est pas un Integer
					}
				}
				else {
					if (i > 0) {
						result += ", ";
					}
					result += "'" + value + "'";
				}
			}
			result += ")";
		}
		else {
			for (int i = 0; i < values.size(); i++) {
				String value = values.get(i);

				if (result.contains("=")) {
					if (isInt) {
						if (org.esupportail.commons.utils.strings.StringUtils.isInteger(value)) {
							result += value;
						}
						else {
							return null; // la valeur n'est pas un Integer
						}
					}
					else {
						result += "'" + value + "'";
					}
				}
				else if (result.contains("LIKE")) {
					result += "'%" + value + "%'";
				}

				if (i > 0 && i < values.size()-1) {
					result += " AND " + w;
				}
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("exiting HqlQueryPojo.formatWhere() with result = " + result);
		}
		return result;
	}

	/**
	 * Create 'where' with a symbol wich allows user to search with a = or LIKE.
	 * @param s
	 * @param value
	 * @param symbol
	 * @return String
	 */
	public static String createWhereSymbol(final String s, final String value, final String symbol) {
		String result = "";

		if (value.contains(symbol)) {
			if (value.startsWith(symbol) && !value.endsWith(symbol)) {
				result = s + " LIKE '%" + value + "'";
			}
			else if (value.endsWith(symbol) && !value.startsWith(symbol)) {
				result = s + " LIKE '" + value + "%'";
			}
			else if (value.endsWith(symbol) && value.startsWith(symbol)) {
				result = s + " LIKE '%" + value + "%'";
			}
			else {
				result = s + " = '" + value + "'";
			}
			result = result.replace(symbol, "");
		}
		else {
			result = s + " = '" + value + "'";
		}

		return result;
	}

	/*
	 * ************************** ACCESSORS *********************************
	 */

	/**
	 * @return null if not select.
	 */
	public String getSelect() {
		if (!select.isEmpty()) {
			return select.get(0);
		}
		return null;
	}
	
	
	/**
	 * @return the from
	 */
	public List<String> getFrom() {
		return from;
	}

	/**
	 * @return the where
	 */
	public List<String> getWhere() {
		return where;
	}
}
