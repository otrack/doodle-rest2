package ch.noisette.doodle.entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Subscriber implements Serializable {

	@NotNull
	private UUID id;

	@NotNull
	private String label;

	private List<String> choices;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public List<String> getChoices() {
		return choices;
	}
	
	public void setChoices(List<String> choices) {
		this.choices = choices;
	}
	
	@Override
	public String toString() {
		return "Subscriber [label=" + label + ", choices=" + choices + "]";
	}

}
