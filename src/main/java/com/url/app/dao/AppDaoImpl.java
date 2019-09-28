package com.url.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.url.app.dto.Action;
import com.url.app.dto.FacultySkillsetMng;
import com.url.app.dto.UrlRolesBean;
import com.url.app.dto.User;
import com.url.app.dto.UserMng;
import com.url.app.utility.AppCommon;

/**
 * Dao implementation of application.
 * Database related method implementations.
 * 
 * @author Shekhar Shinde
 */
@Repository(value = "appDaoImpl")
public class AppDaoImpl implements AppDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<UrlRolesBean> fetchUrlRoleIds() {
		//@formatter:off
		final String jpql = "select distinct new com.url.app.dto.UrlRolesBean(a.actionPath, r.roleId) "
				+ "from RolePrivilegeRelation rpr "
				+ "inner join rpr.id.role r "
				+ "inner join rpr.id.privilege p "
				+ "inner join p.actions a "
				+ "where rpr.isActive=1 and r.isActive=1 and p.isActive=1 and a.isSkip=0 and a.isActive=1"
				+ "order by a.actionPath asc";
		//@formatter:on
		final TypedQuery<UrlRolesBean> typedQuery = entityManager.createQuery(jpql, UrlRolesBean.class);

		return typedQuery.getResultList();
	}

	@Override
	public List<Action> fetchActions() {
		//@formatter:off
		final String jpql = "from Action a "
				+ "order by a.isSkip, a.actionPath asc";
		//@formatter:on
		final TypedQuery<Action> typedQuery = entityManager.createQuery(jpql, Action.class);

		return typedQuery.getResultList();
	}

	@Override
	public User fetchUser(final String userName) {
		//@formatter:off
		final String jpql = "from User u "
				+ "join fetch u.userRoleRelations urr "
				+ "where u.userName=:userName "
				+ "order by u.userId asc";
		//@formatter:on
		final TypedQuery<User> typedQuery = entityManager.createQuery(jpql, User.class);
		typedQuery.setParameter("userName", userName);

		User user = null;
		final List<User> queryResult = typedQuery.getResultList();
		if (!queryResult.isEmpty()) {
			user = queryResult.get(0);
		}

		return user;
	}

	@Override
	public int userUpdateLastLoginSuccess(final Integer userId) {
		//@formatter:off
		final String jpql = "update User "
				+ "set failedAttemptCnt=:failedAttemptCnt, "
				+ "lastSuccessfulLoginDate=:lastSuccessfulLoginDate "
				+ "where userId=:userId";
		//@formatter:on
		final Query query = entityManager.createQuery(jpql);
		query.setParameter("failedAttemptCnt", 0);
		query.setParameter("lastSuccessfulLoginDate", AppCommon.currentDateTime());
		query.setParameter("userId", userId);

		return query.executeUpdate();
	}

	@Override
	public int userUpdateLastLoginFailure(final String userName) {
		//@formatter:off
		final String jpql = "update User "
				+ "set failedAttemptCnt=failedAttemptCnt+1, "
				+ "lastFailedLoginDate=:lastFailedLoginDate "
				+ "where userName=:userName";
		//@formatter:on
		final Query query = entityManager.createQuery(jpql);
		query.setParameter("lastFailedLoginDate", AppCommon.currentDateTime());
		query.setParameter("userName", userName);

		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserMng> fetchUsersListing() {
		final StoredProcedureQuery query = entityManager.createStoredProcedureQuery("get_all_users", UserMng.class);

		return query.getResultList();
	}

	@Override
	public User fetchUserWithRoles(final Integer userId) {
		//@formatter:off
		final String jpql = "from User u "
				+ "left join fetch u.userRoleRelations urr "
				+ "where u.userId=:userId "
				+ "order by u.userId asc";
		//@formatter:on
		final TypedQuery<User> typedQuery = entityManager.createQuery(jpql, User.class);
		typedQuery.setParameter("userId", userId);

		User user = null;
		final List<User> queryResult = typedQuery.getResultList();
		if (!queryResult.isEmpty()) {
			user = queryResult.get(0);
		}

		return user;
	}

	@Override
	public User fetchUserWithModules(final Integer userId) {
		//@formatter:off
		final String jpql = "from User u "
				+ "left join fetch u.facultySkillsets fs "
				+ "where u.userId=:userId "
				+ "order by u.userId asc";
		//@formatter:on
		final TypedQuery<User> typedQuery = entityManager.createQuery(jpql, User.class);
		typedQuery.setParameter("userId", userId);

		User user = null;
		final List<User> queryResult = typedQuery.getResultList();
		if (!queryResult.isEmpty()) {
			user = queryResult.get(0);
		}

		return user;
	}

	@Override
	public String generateNewCode(String commonSettingsType) {
		//@formatter:off
		final String jpql = "select cs.value "
				+ "from CommonSetting cs "
				+ "where cs.type=:type "
				+ "order by cs.orderNumber asc";
		//@formatter:on
		final TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);
		typedQuery.setParameter("type", commonSettingsType);

		final String value = typedQuery.getSingleResult();

		//@formatter:off
		final String updateJpql = "update CommonSetting "
				+ "set value=value+1 "
				+ "where type=:type";
		//@formatter:on
		final Query query = entityManager.createQuery(updateJpql);
		query.setParameter("type", commonSettingsType);

		query.executeUpdate();

		return value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FacultySkillsetMng> fetchFacultySkillsetsListing() {
		final StoredProcedureQuery query = entityManager.createStoredProcedureQuery("get_faculty_skillsets", FacultySkillsetMng.class);

		return query.getResultList();
	}
}