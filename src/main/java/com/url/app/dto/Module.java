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
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the module database table.
 */
@Entity
@Table(name = "module")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@NamedQuery(name = "Module.findAll", query = "SELECT m FROM Module m")
public class Module implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "module_id", unique = true, nullable = false)
	private Integer moduleId;

	@Column(name = "created_by", updatable = false, nullable = false)
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", updatable = false, nullable = false)
	@CreatedDate
	private Date createdDate;

	@Column(name = "is_active", nullable = false)
	private Integer isActive;

	@Column(name = "modified_by", nullable = false)
	private Integer modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date", nullable = false)
	@LastModifiedDate
	private Date modifiedDate;

	@Column(name = "module_name", unique = true, nullable = false, length = 100)
	private String moduleName;

	//bi-directional many-to-one association to FacultySkillset
	@OneToMany(mappedBy = "id.module", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonBackReference
	private Set<FacultySkillset> facultySkillsets = new HashSet<>(0);

	public Module() {
	}

	public Integer getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
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

	public Integer getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Set<FacultySkillset> getFacultySkillsets() {
		return this.facultySkillsets;
	}

	public void setFacultySkillsets(Set<FacultySkillset> facultySkillsets) {
		this.facultySkillsets = facultySkillsets;
	}

	public boolean addFacultySkillset(FacultySkillset facultySkillset) {
		facultySkillset.setModule(this);

		return getFacultySkillsets().add(facultySkillset);
	}

	public boolean removeFacultySkillset(FacultySkillset facultySkillset) {
		boolean isRemoved = getFacultySkillsets().remove(facultySkillset);

		return isRemoved;
	}

	@Override
	public int hashCode() {
		return Objects.hash(moduleId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Module)) {
			return false;
		}
		Module other = (Module) obj;
		return Objects.equals(this.getModuleId(), other.getModuleId());
	}
}