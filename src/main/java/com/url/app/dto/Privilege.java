package com.url.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.url.app.utility.AppSQL;

/**
 * The persistent class for the privilege database table.
 */
@Entity
@Table(name = "privilege")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@NamedQuery(name = "Privilege.findAll", query = AppSQL.QRY_FIND_ALL_PRIVILEGE)
public class Privilege implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "privilege_id", unique = true, nullable = false)
	private Integer privilegeId;

	@Column(name = "created_by", updatable = false, nullable = false)
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", updatable = false, nullable = false)
	@CreatedDate
	private Date createdDate;

	@Column(length = 500)
	private String description;

	@Column(name = "is_active", nullable = false)
	private Integer isActive;

	@Column(name = "privilege_name", unique = true, nullable = false, length = 100)
	private String privilegeName;

	//bi-directional many-to-one association to Action
	@OneToMany(mappedBy = "privilege", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonBackReference(value = "privilege_action")
	private Set<Action> actions = new HashSet<>(0);

	//bi-directional many-to-one association to RolePrivilegeRelation
	@OneToMany(mappedBy = "id.privilege", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonBackReference(value = "privilege_rolePrivilegeRelation")
	private Set<RolePrivilegeRelation> rolePrivilegeRelations = new HashSet<>(0);

	public Privilege() {
		super();
	}

	public Integer getPrivilegeId() {
		return this.privilegeId;
	}

	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getPrivilegeName() {
		return this.privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public Set<Action> getActions() {
		return this.actions;
	}

	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}

	public boolean addAction(Action action) {
		action.setPrivilege(this);

		return getActions().add(action);
	}

	public boolean removeAction(Action action) {
		return getActions().remove(action);
	}

	public Set<RolePrivilegeRelation> getRolePrivilegeRelations() {
		return this.rolePrivilegeRelations;
	}

	public void setRolePrivilegeRelations(Set<RolePrivilegeRelation> rolePrivilegeRelations) {
		this.rolePrivilegeRelations = rolePrivilegeRelations;
	}

	public boolean addRolePrivilegeRelation(RolePrivilegeRelation rolePrivilegeRelation) {
		rolePrivilegeRelation.setPrivilege(this);

		return getRolePrivilegeRelations().add(rolePrivilegeRelation);
	}

	public boolean removeRolePrivilegeRelation(RolePrivilegeRelation rolePrivilegeRelation) {
		return getRolePrivilegeRelations().remove(rolePrivilegeRelation);
	}

	@Override
	public int hashCode() {
		return Objects.hash(privilegeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Privilege)) {
			return false;
		}
		Privilege other = (Privilege) obj;

		return Objects.equals(this.getPrivilegeId(), other.getPrivilegeId());
	}
}