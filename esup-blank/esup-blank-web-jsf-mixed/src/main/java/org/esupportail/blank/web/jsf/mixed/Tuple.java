package org.esupportail.blank.web.jsf.mixed;

public class Tuple<L, R> {
	public L lefty;
	public R righty;

	public Tuple(L lefty, R righty) {
		this.lefty = lefty;
		this.righty = righty;
	}

	public L getLefty() {
		return this.lefty;
	}

	public R getRighty() {
		return this.righty;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = (31 * result) + ((lefty == null) ? 0 : lefty.hashCode());
		result = (31 * result) + ((righty == null) ? 0 : righty.hashCode());
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if ((obj == null) || (!(obj instanceof Tuple))) {
			return false;
		}

		final Tuple<L, R> other = (Tuple<L, R>) obj;

		if (lefty == null) {
			if (other.lefty != null) {
				return false;
			}
		} else if (!lefty.equals(other.lefty)) {
			return false;
		}

		if (righty == null) {
			if (other.righty != null) {
				return false;
			}
		} else if (!righty.equals(other.righty)) {
			return false;
		}

		return true;
	}
}
