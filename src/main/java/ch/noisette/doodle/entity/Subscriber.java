package ch.noisette.doodle.entity;

import info.archinnov.achilles.annotations.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
public class Subscriber implements Serializable {

	@EmbeddedId
	@NotNull
	private SubscriberKey id;

	@Column
	@NotNull
	private String label;

	@Column
	private List<String> choices;

	public SubscriberKey getId() {
		return id;
	}

	public void setId(SubscriberKey id) {
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

	public static class SubscriberKey {

		@Order(1)
		UUID pollId;

		@Order(2)
		UUID subscriberId;


		public UUID getSubscriberId() {
			return subscriberId;
		}

		public void setSubscriberId(UUID subscriberId) {
			this.subscriberId = subscriberId;
		}

		public UUID getPollId() {
			return pollId;
		}

		public void setPollId(UUID pollId) {
			this.pollId = pollId;
		}
	}
}
