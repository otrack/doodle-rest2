package ch.noisette.doodle.entity;

import info.archinnov.achilles.annotations.Column;
import info.archinnov.achilles.annotations.Entity;
import info.archinnov.achilles.annotations.Id;

import java.util.List;
import java.util.UUID;

@Entity(table="users")
public class Poll {

	@Id
	private UUID id;

	@Column
	private String label;

	@Column
	private List<String> choices;

	@Column
	private String email;

	@Column
	private Integer maxChoices;

	@Column
	private List<Subscriber> subscribers;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getMaxChoices() {
		return maxChoices;
	}

	public void setMaxChoices(Integer maxChoices) {
		this.maxChoices = maxChoices;
	}

	public List<Subscriber> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(List<Subscriber> subscribers) {
		this.subscribers = subscribers;
	}

	@Override
	public String toString() {
		return "Poll [id=" + id + ", label="
				+ label + ", choices=" + choices + ", email="
				+ email + ", subscribers=" + subscribers + "]";
	}
}
