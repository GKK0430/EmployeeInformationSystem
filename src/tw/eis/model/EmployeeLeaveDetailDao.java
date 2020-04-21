package tw.eis.model;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeLeaveDetailDao implements IEmployeeLeaveDetailDao {

	private SessionFactory sessionFactory;

	@Autowired
	public EmployeeLeaveDetailDao(@Qualifier(value = "sessionFactory") SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	@Override
	public void addDetail(EmployeeLeaveDetail employeeLeaveDetail) {
		getSession().save(employeeLeaveDetail);
	}

	@Override
	public void updateDetail(int eldId, EmployeeLeaveDetail employeeLeaveDetail) {
		EmployeeLeaveDetail result = getSession().get(EmployeeLeaveDetail.class, eldId);

		result.setUsedHours(employeeLeaveDetail.getUsedHours());
		result.setSurplusHours(employeeLeaveDetail.getSurplusHours());
		getSession().update(result);
	}

	@Override
	public void deleteDetail(int eldId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<EmployeeLeaveDetail> queryAllDetail() {
		Query<EmployeeLeaveDetail> query = getSession().createQuery("From EmployeeLeaveDetail",
				EmployeeLeaveDetail.class);
		List<EmployeeLeaveDetail> list = query.list();
		if (list.size() != 0) {
			return list;
		}
		return null;
	}

	@Override
	public List<EmployeeLeaveDetail> queryValidLTByEID(int employeeId) {
		Query<EmployeeLeaveDetail> query = getSession().createQuery(
				"From EmployeeLeaveDetail Where EmployeeID=?0 and EndDate>getdate()", EmployeeLeaveDetail.class);
		query.setParameter(0, employeeId);
		List<EmployeeLeaveDetail> list = query.list();
		if (list.size() != 0) {
			return list;
		}
		return null;
	}

	@Override
	public EmployeeLeaveDetail queryValidLTByEIDandLT(int employeeId, String leaveType) {
		Query<EmployeeLeaveDetail> query = getSession().createQuery(
				"From EmployeeLeaveDetail Where EmployeeID=?0 and EndDate>getdate() and LeaveType=?1",
				EmployeeLeaveDetail.class);
		query.setParameter(0, employeeId);
		query.setParameter(1, leaveType);
		List<EmployeeLeaveDetail> list = query.list();
		EmployeeLeaveDetail result = list.get(0);
		if (result != null) {
			return result;
		}
		return null;
	}

	@Override
	public String getLeaveTypeTag(int employeeId) {
		String hqlStr = "From EmployeeLeaveDetail Where EmployeeID=?0 and EndDate>getdate()";
		Query<EmployeeLeaveDetail> query = getSession().createQuery(hqlStr, EmployeeLeaveDetail.class);
		query.setParameter(0, employeeId);
		List<EmployeeLeaveDetail> list = query.list();
		Iterator<EmployeeLeaveDetail> iterator = list.iterator();

		String ans = "";

		while (iterator.hasNext()) {
			String iELD = iterator.next().getLeaveType();
			ans += "<option value='" + iELD + "'>" + iELD + "</option>";
		}
		return ans;
	}

}