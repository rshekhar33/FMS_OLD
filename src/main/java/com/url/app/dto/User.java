package com.url.app.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.url.app.validation.BasicActivateGroup;
import com.url.app.validation.BasicCreateGroup;
import com.url.app.validation.BasicUpdateGroup;
import com.url.app.validation.DBCreateGroup;
import com.url.app.validation.DBUpdateGroup;
import com.url.app.validation.EmailIdNotExists;
import com.url.app.validation.EmailIdNotExistsUpdate;
import com.url.app.validation.UserNameNotExists;

/**
 * The persistent class for the user database table.
 */
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "password", "hibernateLazyInitializer", "handler" })
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
@EmailIdNotExistsUpdate(groups = DBUpdateGroup.class, message = "{user.email.exists.error}")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	@NotNull(groups = BasicActivateGroup.class, message = "{update.failed.error}")
	@Positive(groups = BasicActivateGroup.class, message = "{update.failed.error}")
	private Integer userId;

	@Column(name = "created_by", updatable = false, nullable = false)
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", updatable = false, nullable = false)
	@CreatedDate
	private Date createdDate;

	@Column(name = "email_id", nullable = false, length = 100)
	@NotBlank(groups = { BasicCreateGroup.class, BasicUpdateGroup.class }, message = "{mandatory.field.error}")
	@Email(groups = { BasicCreateGroup.class, BasicUpdateGroup.class }, message = "{invalid.email.error}")
	@Size(groups = { BasicCreateGroup.class, BasicUpdateGroup.class }, max = 100, message = "{length.error}")
	@EmailIdNotExists(groups = DBCreateGroup.class, message = "{user.email.exists.error}")
	private String emailId;

	@Column(name = "failed_attempt_cnt", nullable = false)
	private Integer failedAttemptCnt;

	@Column(name = "first_name", nullable = false, length = 100)
	@NotBlank(groups = { BasicCreateGroup.class, BasicUpdateGroup.class }, message = "{mandatory.field.error}")
	@Pattern(groups = { BasicCreateGroup.class, BasicUpdateGroup.class }, regexp = "[a-zA-Z]+", message = "{user.firstname.onlyalphabets.error}")
	@Size(groups = { BasicCreateGroup.class, BasicUpdateGroup.class }, max = 100, message = "{length.error}")
	private String firstName;

	@Column(name = "is_active", nullable = false)
	@NotNull(groups = BasicActivateGroup.class, message = "{update.failed.error}")
	@Min(groups = BasicActivateGroup.class, value = 0, message = "{update.failed.error}")
	@Max(groups = BasicActivateGroup.class, value = 1, message = "{update.failed.error}")
	private Integer isActive;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_failed_login_date")
	private Date lastFailedLoginDate;

	@Column(name = "last_name", length = 100)
	@Pattern(groups = { BasicCreateGroup.class, BasicUpdateGroup.class }, regexp = "[a-zA-Z]*", message = "{user.lastname.onlyalphabets.error}")
	@Size(groups = { BasicCreateGroup.class, BasicUpdateGroup.class }, max = 100, message = "{length.error}")
	private String lastName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_successful_login_date")
	private Date lastSuccessfulLoginDate;

	@Column(name = "middle_name", length = 100)
	@Pattern(groups = { BasicCreateGroup.class, BasicUpdateGroup.class }, regexp = "[a-zA-Z]*", message = "{user.middlename.onlyalphabets.error}")
	@Size(groups = { BasicCreateGroup.class, BasicUpdateGroup.class }, max = 100, message = "{length.error}")
	private String middleName;

	@Column(name = "mobile_no", nullable = false, length = 20)
	@NotBlank(groups = { BasicCreateGroup.class, BasicUpdateGroup.class }, message = "{mandatory.field.error}")
	@Pattern(groups = { BasicCreateGroup.class, BasicUpdateGroup.class }, regexp = "\\d+", message = "{only.number.error}")
	@Size(groups = { BasicCreateGroup.class, BasicUpdateGroup.class }, min = 10, max = 10, message = "{user.mobile.length.error}")
	private String mobileNo;

	@Column(name = "modified_by", nullable = false)
	private Integer modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date", nullable = false)
	@LastModifiedDate
	private Date modifiedDate;

	@Column(nullable = false, length = 100)
	private String password;

	@Column(name = "user_name", nullable = false, length = 50)
	@NotBlank(groups = BasicCreateGroup.class, message = "{mandatory.field.error}")
	@Pattern(groups = BasicCreateGroup.class, regexp = "^[\\w\\.@]+$", message = "{user.username.restrictedchar2.error}")
	@Size(groups = BasicCreateGroup.class, max = 50, message = "{length.error}")
	@UserNameNotExists(groups = DBCreateGroup.class, message = "{user.username.exists.error}")
	private String userName;

	//bi-directional many-to-one association to FacultySkillset
	@OneToMany(mappedBy = "id.user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonBackReference(value = "user_facultySkillset")
	private Set<FacultySkillset> facultySkillsets = new HashSet<>(0);

	//bi-directional many-to-one association to UserRoleRelation
	@OneToMany(mappedBy = "id.user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonBackReference(value = "user_userRoleRelation")
	private Set<UserRoleRelation> userRoleRelations = new HashSet<>(0);

	@Transient
	@NotEmpty(groups = { BasicCreateGroup.class, BasicUpdateGroup.class }, message = "{mandatory.field.error}")
	private List<@Positive Integer> roles;

	public User() {
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Integer getFailedAttemptCnt() {
		return failedAttemptCnt;
	}

	public void setFailedAttemptCnt(Integer failedAttemptCnt) {
		this.failedAttemptCnt = failedAttemptCnt;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Integer getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Date getLastFailedLoginDate() {
		return lastFailedLoginDate;
	}

	public void setLastFailedLoginDate(Date lastFailedLoginDate) {
		this.lastFailedLoginDate = lastFailedLoginDate;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getLastSuccessfulLoginDate() {
		return lastSuccessfulLoginDate;
	}

	public void setLastSuccessfulLoginDate(Date lastSuccessfulLoginDate) {
		this.lastSuccessfulLoginDate = lastSuccessfulLoginDate;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getMobileNo() {
		return this.mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Set<FacultySkillset> getFacultySkillsets() {
		return this.facultySkillsets;
	}

	public void setFacultySkillsets(Set<FacultySkillset> facultySkillsets) {
		this.facultySkillsets = facultySkillsets;
	}

	public boolean addFacultySkillset(FacultySkillset facultySkillset) {
		facultySkillset.setUser(this);

		return getFacultySkillsets().add(facultySkillset);
	}

	public boolean removeFacultySkillset(FacultySkillset facultySkillset) {
		boolean isRemoved = getFacultySkillsets().remove(facultySkillset);

		return isRemoved;
	}

	public Set<UserRoleRelation> getUserRoleRelations() {
		return this.userRoleRelations;
	}

	public void setUserRoleRelations(Set<UserRoleRelation> userRoleRelations) {
		this.userRoleRelations = userRoleRelations;
	}

	public boolean addUserRoleRelation(UserRoleRelation userRoleRelation) {
		userRoleRelation.setUser(this);

		return getUserRoleRelations().add(userRoleRelation);
	}

	public boolean removeUserRoleRelation(UserRoleRelation userRoleRelation) {
		boolean isRemoved = getUserRoleRelations().remove(userRoleRelation);

		return isRemoved;
	}

	public List<Integer> getRoles() {
		return roles;
	}

	public void setRoles(List<Integer> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;

		return Objects.equals(this.getUserId(), other.getUserId());
	}
}