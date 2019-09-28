package com.url.app.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * The primary key class for the user_role_relation database table.
 */
@Embeddable
public class UserRoleRelationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private Role role;

	public UserRoleRelationPK() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(role, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof UserRoleRelationPK)) {
			return false;
		}
		UserRoleRelationPK other = (UserRoleRelationPK) obj;
		return Objects.equals(role, other.role) && Objects.equals(user, other.user);
	}
}