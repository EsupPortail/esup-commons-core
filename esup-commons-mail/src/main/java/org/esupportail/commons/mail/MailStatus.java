package org.esupportail.commons.mail;

import java.io.Serializable;

/**
 * Wrapper for mail events
 *
 * @param <A>
 */
public abstract class MailStatus<A> implements Serializable {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -9000068114464338092L;

	public abstract A notAlreadySent();

	public static <T> MailStatus<T> alreadySent() {
		return new AlreadySent<T>();
	}

	public static <T> MailStatus<T> notAlreadySent(final T t) {
		return new NotAlreadySent<T>(t);
	}
	
	public boolean isNotAlreadySent() {
		return this instanceof NotAlreadySent;
	}
	
	public boolean isAlreadySent() {
		return this instanceof AlreadySent;
	}

	public static <T> MailStatus<T> fromNull(final T t) {
		return t == null ? MailStatus.<T> alreadySent() : notAlreadySent(t);
	}
	
	private static final class AlreadySent<A> extends MailStatus<A> {
		
		/**
		 * The serialization id.
		 */
		private static final long serialVersionUID = -3717902182284863934L;

		public A notAlreadySent() {
			throw new Error("notSent on Sent");
		}
		
	    @Override
	    public int hashCode() {
	       return 31;
	    }

	    @Override
	    public boolean equals(Object obj) {
	       if (this == obj)
	          return true;
	       if (obj == null)
	          return false;
	       if (getClass() != obj.getClass())
	          return false;
	       return true;
	    }
	}

	private static final class NotAlreadySent<A> extends MailStatus<A> {

		/**
		 * The serialization id.
		 */
		private static final long serialVersionUID = 5228926236595148424L;

	    private final A a;

	    NotAlreadySent(final A a) {
	      this.a = a;
	    }
	    
		@Override
		public A notAlreadySent() {
			return a;
		}

		public static <T> MailStatus<T> notAlreadySent(final T t) {
			return new NotAlreadySent<T>(t);
		}
		

	    @Override
	    public int hashCode() {
	       final int prime = 31;
	       int result = 1;
	       result = prime * result + ((a == null) ? 0 : a.hashCode());
	       return result;
	    }

	    @Override
	    public boolean equals(Object obj) {
	       if (this == obj)
	          return true;
	       if (obj == null)
	          return false;
	       if (getClass() != obj.getClass())
	          return false;
	       NotAlreadySent<?> other = (NotAlreadySent<?>) obj;
	       if (a == null) {
	          if (other.a != null)
	             return false;
	       } else if (!a.equals(other.a))
	          return false;
	       return true;
	    }
	}
	
}
