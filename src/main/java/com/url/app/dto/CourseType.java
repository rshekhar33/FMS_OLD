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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.url.app.utility.AppConstant;
import com.url.app.validation.BasicActivateGroup;
import com.url.app.validation.BasicCreateGroup;
import com.url.app.validation.BasicUpdateGroup;

/**
 * The persistent class for the course_type database table.
 */
@Entity
@Table(name = "course_type")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@NamedQuery(name = "CourseType.findAll", query = "SELECT c FROM CourseType c")
public class CourseType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_type_id", unique = true, nullable = false)
	@NotNull(groups = BasicActivateGroup.class, message = "{update.failed.error}")
	@Positive(groups = BasicActivateGroup.class, message = "{update.failed.error}")
	private Integer courseTypeId;

	@Column(name = "course_type_code", unique = true, nullable = false, length = 50)
	private String courseTypeCode;

	@Column(name = "course_type_name", nullable = false, length = 500)
	@NotBlank(groups = { BasicCreateGroup.class, BasicUpdateGroup.class }, message = "{mandatory.field.error}")
	@Pattern(groups = { BasicCreateGroup.class,
			BasicUpdateGroup.class }, regexp = AppConstant.REGEX_RESTRICTED_CHAR_3, message = "{coursetype.coursetypename.restrictedchar3.error}")
	@Size(groups = { BasicCreateGroup.class, BasicUpdateGroup.class }, max = 500, message = "{length.error}")
	private String courseTypeName;

	@Column(name = "created_by", updatable = false, nullable = false)
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", updatable = false, nullable = false)
	@CreatedDate
	private Date createdDate;

	@Column(name = "is_active", nullable = false)
	@NotNull(groups = BasicActivateGroup.class, message = "{update.failed.error}")
	@Min(groups = BasicActivateGroup.class, value = 0, message = "{update.failed.error}")
	@Max(groups = BasicActivateGroup.class, value = 1, message = "{update.failed.error}")
	private Integer isActive;

	@Column(name = "modified_by", nullable = false)
	private Integer modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date", nullable = false)
	@LastModifiedDate
	private Date modifiedDate;

	@Column(name = "no_of_days")
	@NotBlank(groups = { BasicCreateGroup.class, BasicUpdateGroup.class }, message = "{mandatory.field.error}")
	@Pattern(groups = { BasicCreateGroup.class, BasicUpdateGroup.class }, regexp = AppConstant.REGEX_NUMERIC_ONLY, message = "{only.number.error}")
	private Integer noOfDays;

	//bi-directional many-to-one association to Course
	@OneToMany(mappedBy = "courseType", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonBackReference(value = "courseType_course")
	private Set<Course> courses = new HashSet<>(0);

	public CourseType() {
	}

	public Integer getCourseTypeId() {
		return this.courseTypeId;
	}

	public void setCourseTypeId(Integer courseTypeId) {
		this.courseTypeId = courseTypeId;
	}

	public String getCourseTypeCode() {
		return this.courseTypeCode;
	}

	public void setCourseTypeCode(String courseTypeCode) {
		this.courseTypeCode = courseTypeCode;
	}

	public String getCourseTypeName() {
		return this.courseTypeName;
	}

	public void setCourseTypeName(String courseTypeName) {
		this.courseTypeName = courseTypeName;
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

	public Integer getNoOfDays() {
		return this.noOfDays;
	}

	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}

	public Set<Course> getCourses() {
		return this.courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public boolean addCourse(Course course) {
		course.setCourseType(this);

		return getCourses().add(course);
	}

	public boolean removeCourse(Course course) {
		return getCourses().remove(course);
	}

	@Override
	public int hashCode() {
		return Objects.hash(courseTypeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CourseType)) {
			return false;
		}
		CourseType other = (CourseType) obj;

		return Objects.equals(this.getCourseTypeId(), other.getCourseTypeId());
	}
}