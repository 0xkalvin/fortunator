package com.fortunator.api.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "transaction_categories")
public class TransactionCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 60)
	private String name;

	@Size(max = 255)
	private String description;

	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne
	private User user;

	private boolean isDefault;

	public TransactionCategory() {
	}

	public TransactionCategory(Long id, String name, String description, User user) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.user = user;
		this.isDefault = false;
	}

	public TransactionCategory(Long id, String name, String description, User user, Boolean isDefault) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.user = user;
		this.isDefault = isDefault;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		TransactionCategory other = (TransactionCategory) obj;

		if (id == null) {
			if (other.id != null)
				return false;

		} else if (!id.equals(other.id))
			return false;

		if (name == null) {
			if (other.name != null)
				return false;

		} else if (!name.equals(other.name))
			return false;

		return true;
	}
}
