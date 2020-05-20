package com.url.app.impl.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.url.app.config.AppMessage;
import com.url.app.dto.CourseType;
import com.url.app.interf.dao.AppDao;
import com.url.app.interf.dao.CourseTypeRepository;
import com.url.app.interf.service.AppCourseTypeService;
import com.url.app.interf.service.AppUserService;
import com.url.app.utility.AppCommon;
import com.url.app.utility.AppConstant;
import com.url.app.utility.AppLogMessage;
import com.url.app.utility.AppResponseKey;
import com.url.app.validation.AppCourseTypeValidationService;

/**
 * Service implementation of application for Course Type.
 * Method implementation of business logic.
 * 
 * @author Shekhar Shinde
 */
@Service(value = "appCourseTypeServiceImpl")
public class AppCourseTypeServiceImpl implements AppCourseTypeService {
	private static final Logger logger = LoggerFactory.getLogger(AppCourseTypeServiceImpl.class);

	@Autowired
	private AppDao appDao;

	@Autowired
	private AppUserService appUserService;

	@Autowired
	private CourseTypeRepository courseTypeRepository;

	@Autowired
	private AppMessage appMessage;

	@Autowired
	private AppCourseTypeValidationService appCourseTypeValidationService;

	@Override
	@Transactional(readOnly = true)
	public List<CourseType> fetchDetailsCourseTypes() {
		return courseTypeRepository.findAllByOrderByCourseTypeId();
	}

	@Override
	@Transactional(readOnly = true)
	public CourseType fetchDataCourseType(final CourseType formCourseType) {
		CourseType courseType = null;

		if (AppCommon.isPositiveInteger(formCourseType.getCourseTypeId())) {
			courseType = courseTypeRepository.getOne(formCourseType.getCourseTypeId());
		}

		return courseType;
	}

	@Override
	@Transactional
	public Map<String, String> validateSaveCourseType(final CourseType formCourseType) {
		logger.info(AppLogMessage.COURSE_TYPE_MSG, formCourseType);

		if (AppCommon.isPositiveInteger(formCourseType.getCourseTypeId())) {
			appCourseTypeValidationService.validateForUpdate(formCourseType);
		} else {
			appCourseTypeValidationService.validateForCreate(formCourseType);
		}

		String status = AppConstant.BLANK_STRING;
		String msg = AppConstant.BLANK_STRING;

		final Integer loggedInUserId = appUserService.getPrincipalUserUserId();

		CourseType courseType = new CourseType();
		if (AppCommon.isPositiveInteger(formCourseType.getCourseTypeId())) {
			courseType = courseTypeRepository.getOne(formCourseType.getCourseTypeId());
		} else {
			final String courseTypeCode = AppConstant.COURSE_TYPE_CODE_PREFIX + appDao.generateNewCode(AppConstant.CS_TYPE_COURSE_TYPE_CODE_COUNTER);
			courseType.setCourseTypeCode(courseTypeCode);
			courseType.setIsActive(AppConstant.ACTIVE);
			courseType.setCreatedBy(loggedInUserId);
		}
		courseType.setCourseTypeName(formCourseType.getCourseTypeName());
		courseType.setNoOfDays(formCourseType.getNoOfDays());
		courseType.setModifiedBy(loggedInUserId);

		courseTypeRepository.save(courseType);
		final Integer courseTypeId = courseType.getCourseTypeId();

		if (AppCommon.isPositiveInteger(courseTypeId)) {
			status = AppConstant.SUCCESS;
			if (AppCommon.isPositiveInteger(formCourseType.getCourseTypeId())) {
				msg = appMessage.coursetypeUpdateSuccess;
			} else {
				msg = appMessage.coursetypeAddSuccess;
			}
		}

		final Map<String, String> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, status);
		json.put(AppResponseKey.MSG, msg);

		return json;
	}

	@Override
	@Transactional
	public Map<String, String> validateUpdateActivation(final CourseType formCourseType) {
		appCourseTypeValidationService.validateForActivate(formCourseType);

		String status = AppConstant.BLANK_STRING;
		String msg = AppConstant.BLANK_STRING;

		final CourseType courseType = courseTypeRepository.getOne(formCourseType.getCourseTypeId());
		courseType.setIsActive(formCourseType.getIsActive());

		courseTypeRepository.save(courseType);
		final Integer courseTypeIdUpdate = courseType.getCourseTypeId();

		if (AppCommon.isPositiveInteger(courseTypeIdUpdate)) {
			status = AppConstant.SUCCESS;
			if (AppConstant.ACTIVE.equals(formCourseType.getIsActive())) {
				msg = appMessage.coursetypeActiveSuccess;
			} else if (AppConstant.INACTIVE.equals(formCourseType.getIsActive())) {
				msg = appMessage.coursetypeInactiveSuccess;
			}
		}

		final Map<String, String> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, status);
		json.put(AppResponseKey.MSG, msg);

		return json;
	}
}