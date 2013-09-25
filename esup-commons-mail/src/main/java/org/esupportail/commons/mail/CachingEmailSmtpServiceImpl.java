package org.esupportail.commons.mail;

import java.util.concurrent.Future;

import javax.mail.event.TransportEvent;
import javax.mail.internet.InternetAddress;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;

public class CachingEmailSmtpServiceImpl extends SimpleSmtpServiceImpl {

	/**
	 * Serialization id.
	 */
	private static final long serialVersionUID = -257030451029752426L;

	private Cache cache;
	
	private CachingEmailSmtpServiceImpl(
			final InternetAddress fromAddress,
			final InternetAddress interceptAddress,
			final InternetAddress testAddress, 
			final boolean interceptAll,
			final String notInterceptAdresses, 
			final String charset,
			final SmtpServer server,
			final Cache cache) {
		super(fromAddress, interceptAddress, testAddress, interceptAll, charset, server);
		setNotInterceptedAddresses(notInterceptAdresses);
		this.cache = cache;
	}
	
	public CachingEmailSmtpServiceImpl createInstance(
			final InternetAddress fromAddress,
			final InternetAddress interceptAddress,
			final InternetAddress testAddress, final boolean interceptAll,
			final String notInterceptAdresses, final String charset,
			final SmtpServer server,
			final Cache cache) {
		return new CachingEmailSmtpServiceImpl(fromAddress, interceptAddress,
				testAddress, interceptAll, notInterceptAdresses, charset,
				server, cache);
	}
		
	public final static CachingEmailSmtpServiceImpl createInstance(
			final InternetAddress fromAddress,
			final InternetAddress interceptAddress,
			final InternetAddress testAddress, 
			final boolean interceptAll,
			final String notInterceptAdresses,
			final Cache cache) {
		return new CachingEmailSmtpServiceImpl(fromAddress, interceptAddress,
				testAddress, interceptAll, notInterceptAdresses, null,
				null, cache);
	}

	/**
	 * @param msg
	 * @return the cache key that corresponds to a {@link MessageTemplate}  
	 */
	private String getCacheKey(final MessageTemplate msg) {
		return String.format("%s", msg.withMessageId(null).toString());
	}
	

	@Override
	public MailStatus<Future<TransportEvent>> send(final MessageTemplate msg) {
		final String cacheKey = getCacheKey(msg);
		final ValueWrapper value = cache.get(cacheKey);
		if (value == null) {
			MailStatus<Future<TransportEvent>> newEvent = super.send(msg);
			cache.put(cacheKey, msg);
			return newEvent;
		}
		return MailStatus.alreadySent();
	}

	@Override
	public MailStatus<Future<TransportEvent>> sendDoNotIntercept(final MessageTemplate msg) {
		final String cacheKey = getCacheKey(msg);
		final ValueWrapper value = cache.get(cacheKey);
		if (value == null) {
			MailStatus<Future<TransportEvent>> newEvent = super.sendDoNotIntercept(msg);
			cache.put(cacheKey, msg);
			return newEvent;
		}
		return MailStatus.alreadySent();
	}
	
	

}
