package org.esupportail.commons.mail;

import org.esupportail.commons.mail.model.MailStatus;
import org.esupportail.commons.mail.model.MessageTemplate;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;

import javax.mail.MessagingException;
import java.util.concurrent.*;

public final class CachingEmailSmtpService implements SmtpService {

    private final SmtpService smtpService;

	private final Cache cache;

    private static enum AlreadySent implements Future<MailStatus> {_;
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }
        public boolean isCancelled() { return false; }
        public boolean isDone() { return true; }
        public MailStatus get() throws InterruptedException, ExecutionException {
            return MailStatus.AlreadySent;
        }
        @SuppressWarnings("NullableProblems")
        public MailStatus get(long timeout, TimeUnit unit)
                throws InterruptedException, ExecutionException, TimeoutException {
            return get();
        }
    }

    private CachingEmailSmtpService(SmtpService smtpService, Cache cache) {
        this.smtpService = smtpService;
        this.cache = cache;
    }

    public static CachingEmailSmtpService create(SmtpService smtpService, Cache cache) {
        return new CachingEmailSmtpService(smtpService, cache);
    }

    private static abstract class SendAction implements Callable<Future<MailStatus>> {
        public abstract Future<MailStatus> call() throws MessagingException;
    }

    @Override
	public Future<MailStatus> send(final MessageTemplate msg) throws MessagingException {
        return innerSend(msg, new SendAction() {
            public Future<MailStatus> call() throws MessagingException {
                return smtpService.send(msg);
            }
        });
	}

	@Override
	public Future<MailStatus> send(final MessageTemplate msg, final Interception interception) throws MessagingException {
		return innerSend(msg, new SendAction() {
            public Future<MailStatus> call() throws MessagingException {
                return smtpService.send(msg, interception);
            }
        });
	}

    private Future<MailStatus> innerSend(MessageTemplate msg, SendAction sendAction) throws MessagingException {
        final String cacheKey = getCacheKey(msg);
        final ValueWrapper value = cache.get(cacheKey);
        if (value == null) {
            Future<MailStatus> newEvent = sendAction.call();
            cache.put(cacheKey, msg);
            return newEvent;
        }
        return AlreadySent._;
    }

    @Override
    public boolean supportsTest() { return false; }

    @Override
    public Future<MailStatus> test() {
        throw new UnsupportedOperationException(getClass().getName() + " doesn't support test");
    }

    @Override
    public void shutdown() {
        smtpService.shutdown();
    }

    /**
     * @return the cache key that corresponds to a {@link MessageTemplate}
     */
    private String getCacheKey(final MessageTemplate msg) {
        return String.format("%s", msg.withMessageId(null).toString());
    }
}
