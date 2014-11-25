package ch.noisette.doodle.entity;

import info.archinnov.achilles.annotations.*;
import info.archinnov.achilles.type.InsertStrategy;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/*
 In this proposed data model there is only one table.
 Subscriber is completely embedded into that table.
 Pros: one single query to fetch the entire Poll
 Cons: If there's zillion of subscribers, your application
 might end up in OOM...

 Use that if you're not expecting more than say 1000 of
 subscribers per Poll.

 Otherwise you might want to store the subscribers in a separate
 table, with a composite key built with Poll.id and a time UUID.
 */
@Entity(table="poll")
@Strategy(insert = InsertStrategy.NOT_NULL_FIELDS)
public class Poll {

	@Id
	@NotNull
	private UUID id;

	@Column
	@NotNull
	private String label;

	@Column
	@EmptyCollectionIfNull
	private List<String> choices;

	@Column
	private String email;

	@Column
	private Integer maxChoices;

	@Column
	@EmptyCollectionIfNull
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
