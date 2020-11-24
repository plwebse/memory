package se.plweb.memory.domain;

public enum GameObjectState {
	PRESSED_STATE("pressed_state"),
	NORMAL_STATE("normal_state"),
	DISABLED_STATE("disabled_state"),
	MATCHED_STATE("matched_state");

	private final String toString;

	GameObjectState(String toString) {
		this.toString = this.getClass().getName() + ":" + toString;
	}

	public String toString() {
		return toString;
	}
}
