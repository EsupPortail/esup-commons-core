/**
 * 
 */
package org.esupportail.example.batch;

import java.util.Properties;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author bourges
 *
 */
public class Launcher {

	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(Launcher.class);

	/**
	 * Print the syntax and exit.
	 */
	private static void syntax() {
		throw new IllegalArgumentException(
				"syntax: " + Launcher.class.getSimpleName() + " <options>"
				+ "\nwhere option can be:");
		//TODO
	}

	/**
	 * Dispatch dependaing on the arguments.
	 * @param args
	 */
	protected static void dispatch(final String[] args) {
		switch (args.length) {
		case 0:
			syntax();
			break;
		case 1:
			if ("init-db".equals(args[0])) {
				initDb();
			} else if ("maj-db".equals(args[0])) {

			} else {
				syntax();
			}
			break;
		default:
			syntax();
			break;
		}
	}

	private static void initDb() {
		Properties properties = System.getProperties();
		properties.put("generateDdl", "true");
		System.setProperties(properties);
		ApplicationContext context =
			    new ClassPathXmlApplicationContext("properties/applicationContext.xml");
	}

	/**
	 * The main method, called by ant.
	 * @param args
	 */
	public static void main(final String[] args) {
		dispatch(args);
	}


}
