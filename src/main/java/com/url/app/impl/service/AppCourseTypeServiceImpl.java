package com.url.app.impl.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
import com.url.app.utility.AppResponseKey;

/**
 * Service implementation of application for Course Type.
 * Method implementation of business logic.
 * 
 * @author Shekhar Shinde
 */
@Service(value = "appCourseTypeServiceImpl")
public class AppCourseTypeServiceImpl implements AppCourseTypeService {
	//private static final Logger logger = LoggerFactory.getLogger(AppCourseTypeServiceImpl.class);

	@Autowired
	private AppDao appDao;

	@Autowired
	private AppUserService appUserService;

	@Autowired
	private CourseTypeRepository courseTypeRepository;

	@Autowired
	private AppMessage appMessage;

	@Override
	@Transactional(readOnly = true)
	public List<CourseType> fetchDetailsCourseTypes() {
		return courseTypeRepository.findAllByOrderByCourseTypeId();
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, CourseType> fetchDataCourseType(String courseTypeIdStr) {
		final Map<String, CourseType> json = new ConcurrentHashMap<>();

		if (courseTypeIdStr != null && !AppConstant.BLANK_STRING.equals(courseTypeIdStr)) {
			final Integer courseTypeId = Integer.parseInt(courseTypeIdStr);
			final CourseType courseType = courseTypeRepository.getOne(courseTypeId);
			json.put(AppResponseKey.COURSE_TYPE, courseType);
		}

		return json;
	}

	@Override
	@Transactional
	public Map<String, String> validateSaveCourseType(Map<String, String> allRequestParams) {
		final String hidCourseTypeIdStr = allRequestParams.getOrDefault("hidCourseTypeId", "0");
		final String courseTypeName = allRequestParams.getOrDefault("courseTypeName", AppConstant.BLANK_STRING);
		final String noOfDaysStr = allRequestParams.getOrDefault("noOfDays", AppConstant.BLANK_STRING);

		Integer hidCourseTypeId = 0;
		Integer noOfDays = 0;
		try {
			hidCourseTypeId = Integer.parseInt(hidCourseTypeIdStr);
		} catch (Exception e) {
			hidCourseTypeId = 0;
		}

		String status = AppConstant.BLANK_STRING;
		String msg = AppConstant.BLANK_STRING;
		String courseTypeNameError = AppConstant.BLANK_STRING;
		String noOfDaysError = AppConstant.BLANK_STRING;

		if (AppConstant.BLANK_STRING.equals(courseTypeName)) {
			status = AppConstant.FAIL;
			courseTypeNameError = appMessage.getMessage("mandatory.field.error");
		} else if (AppCommon.hasRestrictedChar3(courseTypeName)) {
			status = AppConstant.FAIL;
			courseTypeNameError = appMessage.getMessage("coursetype.coursetypename.restrictedchar3.error");
		}
		if (AppConstant.BLANK_STRING.equals(noOfDaysStr) && AppCommon.isNotNumber(noOfDaysStr)) {
			status = AppConstant.FAIL;
			noOfDaysError = appMessage.getMessage("only.number.error");
		} else {
			try {
				noOfDays = Integer.parseInt(noOfDaysStr);
			} catch (Exception e) {
				noOfDays = 0;
			}
		}

		if (AppConstant.BLANK_STRING.equals(status)) {
			final Integer loggedInUserId = appUserService.getPrincipalUserUserId();

			CourseType courseType = new CourseType();
			if (hidCourseTypeId > 0) {
				courseType = courseTypeRepository.getOne(hidCourseTypeId);
			} else {
				final String courseTypeCode = AppConstant.COURSE_TYPE_CODE_PREFIX + appDao.generateNewCode(AppConstant.CS_TYPE_COURSE_TYPE_CODE_COUNTER);
				courseType.setCourseTypeCode(courseTypeCode);
				courseType.setIsActive(AppConstant.ACTIVE);
				courseType.setCreatedBy(loggedInUserId);
			}
			courseType.setCourseTypeName(courseTypeName);
			courseType.setNoOfDays(noOfDays);
			courseType.setModifiedBy(loggedInUserId);

			courseTypeRepository.save(courseType);
			final Integer courseTypeId = courseType.getCourseTypeId();

			if (courseTypeId != null && courseTypeId > 0) {
				status = AppConstant.SUCCESS;
				if (hidCourseTypeId > 0) {
					msg = appMessage.getMessage("coursetype.update.success");
				} else {
					msg = appMessage.getMessage("coursetype.add.success");
				}
			}
		}

		final Map<String, String> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, status);
		json.put(AppResponseKey.MSG, msg);
		json.put(AppResponseKey.COURSE_TYPE_NAME_ERROR, courseTypeNameError);
		json.put(AppResponseKey.NO_OF_DAYS_ERROR, noOfDaysError);

		return json;
	}

	@Override
	@Transactional
	public Map<String, String> validateUpdateActivation(Map<String, String> allRequestParams) {
		final String courseTypeIdStr = allRequestParams.getOrDefault("courseTypeId", "0");
		final String isActiveStr = allRequestParams.getOrDefault("isActive", AppConstant.BLANK_STRING);

		Integer courseTypeId = 0;
		Integer isActive = AppConstant.ACTIVE;
		String status = AppConstant.BLANK_STRING;
		String msg = AppConstant.BLANK_STRING;

		try {
			courseTypeId = Integer.parseInt(courseTypeIdStr);
		} catch (Exception e) {
			courseTypeId = 0;
		}

		try {
			isActive = Integer.parseInt(isActiveStr);
		} catch (Exception e) {
			isActive = 0;
		}

		if (courseTypeId == 0 || (!AppConstant.ACTIVE.equals(isActive) && !AppConstant.INACTIVE.equals(isActive))) {
			status = AppConstant.FAIL;
			msg = appMessage.getMessage("update.failed.error");
		}

		if (AppConstant.BLANK_STRING.equals(status)) {
			CourseType courseType = courseTypeRepository.getOne(courseTypeId);
			courseType.setIsActive(isActive);

			courseTypeRepository.save(courseType);
			final Integer courseTypeIdUpdate = courseType.getCourseTypeId();

			if (courseTypeIdUpdate != null && courseTypeIdUpdate > 0) {
				status = AppConstant.SUCCESS;
				if (AppConstant.ACTIVE.equals(isActive)) {
					msg = appMessage.getMessage("coursetype.active.success");
				} else if (AppConstant.INACTIVE.equals(isActive)) {
					msg = appMessage.getMessage("coursetype.inactive.success");
				}
			}
		}

		final Map<String, String> json = new ConcurrentHashMap<>();
		json.put(AppResponseKey.STATUS, status);
		json.put(AppResponseKey.MSG, msg);

		return json;
	}
}