package tw.eis.model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class ApplyForLeaveDao implements IApplyForLeaveDao {

	private SessionFactory sessionFactory;

	@Autowired
	public ApplyForLeaveDao(@Qualifier(value = "sessionFactory") SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	@Override
	public void addApply(ApplyForLeave applyForLeave) {
		getSession().save(applyForLeave);
	}

	@Override
	public void signOffApply(int applyId, ApplyForLeave applyForLeave) {
		ApplyForLeave result = getSession().get(ApplyForLeave.class, applyId);

		result.setSigningProgress(applyForLeave.getSigningProgress());
		result.setConfirmTime(applyForLeave.getConfirmTime());
		result.setComment(applyForLeave.getComment());
		getSession().update(result);
	}

	@Override
	public void deleteApply(int applyId) {
		ApplyForLeave result = getSession().get(ApplyForLeave.class, applyId);

		if (result != null && result.getSigningProgress().equals("未簽核")) {
			getSession().delete(result);
		}
	}

	@Override
	public List<ApplyForLeave> queryAllApply() {
		Query<ApplyForLeave> query = getSession().createQuery("From ApplyForLeave", ApplyForLeave.class);
		List<ApplyForLeave> list = query.list();
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				ApplyForLeave aBean = list.get(i);
				aBean.setCreateTime(aBean.getCreateTime().substring(0, 16));
				aBean.setStartTime(aBean.getStartTime().substring(0, 16));
				aBean.setEndTime(aBean.getEndTime().substring(0, 16));
			}
			return list;
		}
		return null;
	}

	@Override
	public List<ApplyForLeave> queryApplyByEID(int employeeId) {
		Query<ApplyForLeave> query = getSession().createQuery("From ApplyForLeave Where EmployeeID=?0",
				ApplyForLeave.class);
		query.setParameter(0, employeeId);
		List<ApplyForLeave> list = query.list();
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				ApplyForLeave aBean = list.get(i);
				aBean.setCreateTime(aBean.getCreateTime().substring(0, 16));
				aBean.setStartTime(aBean.getStartTime().substring(0, 16));
				aBean.setEndTime(aBean.getEndTime().substring(0, 16));
			}
			return list;
		}
		return null;
	}

	@Override
	public List<ApplyForLeave> queryUnsignedApplyBySID(int signerId) {
		Query<ApplyForLeave> query = getSession()
				.createQuery("From ApplyForLeave Where SignerID=?0 and SigningProgress=?1", ApplyForLeave.class);
		query.setParameter(0, signerId);
		query.setParameter(1, "未簽核");
		List<ApplyForLeave> list = query.list();
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				ApplyForLeave aBean = list.get(i);
				aBean.setCreateTime(aBean.getCreateTime().substring(0, 16));
				aBean.setStartTime(aBean.getStartTime().substring(0, 16));
				aBean.setEndTime(aBean.getEndTime().substring(0, 16));
			}
			return list;
		}
		return null;
	}

	@Override
	public List<ApplyForLeave> querySignedApplyBySID(int signerId) {
		Query<ApplyForLeave> query = getSession()
				.createQuery("From ApplyForLeave Where SignerID=?0 and SigningProgress<>?1", ApplyForLeave.class);
		query.setParameter(0, signerId);
		query.setParameter(1, "未簽核");
		List<ApplyForLeave> list = query.list();
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				ApplyForLeave aBean = list.get(i);
				aBean.setCreateTime(aBean.getCreateTime().substring(0, 16));
				aBean.setStartTime(aBean.getStartTime().substring(0, 16));
				aBean.setEndTime(aBean.getEndTime().substring(0, 16));
			}
			return list;
		}
		return null;
	}

	@Override
	public ApplyForLeave queryApplyByAID(int applyId) {
		ApplyForLeave result = getSession().get(ApplyForLeave.class, applyId);
		result.setCreateTime(result.getCreateTime().substring(0, 16));
		result.setStartTime(result.getStartTime().substring(0, 16));
		result.setEndTime(result.getEndTime().substring(0, 16));

		if (result.getConfirmTime() != null) {
			result.setConfirmTime(result.getConfirmTime().substring(0, 16));
		}
		return result;
	}

	@Override
	public List<ApplyForLeave> checkApplyTime(Date startTime, Date endTime, int employeeId) {
		String hql = "From ApplyForLeave where EmployeeID=?0 and StartTime<?1 and EndTime>?2 and SigningProgress<>?3";
		Query<ApplyForLeave> Query = getSession().createQuery(hql, ApplyForLeave.class);
		Query.setParameter(0, employeeId);
		Query.setParameter(1, endTime);
		Query.setParameter(2, startTime);
		Query.setParameter(3, "不同意");
		List<ApplyForLeave> list = Query.list();

		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				ApplyForLeave aBean = list.get(i);
				aBean.setCreateTime(aBean.getCreateTime().substring(0, 16));
			}
			return list;
		}
		return null;
	}

	@Override
	public String getStartHoursTag() {
		String ans = "";

		for (int i = 8; i <= 16; i++) {
			ans += "<option value='" + i + "'>" + i + "</option>";
		}
		return ans;
	}

	@Override
	public String getEndHoursTag() {
		String ans = "";

		for (int i = 8; i <= 17; i++) {
			ans += "<option value='" + i + "'>" + i + "</option>";
		}
		return ans;
	}

	@Override
	public BigDecimal countHoursSTtoET(String startD, String endD, String startH, String endH, String startM,
			String endM) throws ParseException {
		// 起始時間-結束時間換算成總時數
		Calendar aCalendar = Calendar.getInstance();
		Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startD);
		Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endD);

		aCalendar.setTime(startDate);
		int yearS = aCalendar.get(Calendar.YEAR);

		aCalendar.setTime(endDate);
		int yearE = aCalendar.get(Calendar.YEAR);

		// 天-轉時數 1.先將日期轉成數字
		aCalendar.setTime(startDate);
		int dayS = aCalendar.get(Calendar.DAY_OF_YEAR);

		aCalendar.setTime(endDate);
		int dayE = aCalendar.get(Calendar.DAY_OF_YEAR);

		int leaveDay = 0;
		if (yearS == yearE) {
			leaveDay = dayE - dayS;
		} else {
			String lastDateOfYear = yearS + "-12-31";
			Date lastDate = new SimpleDateFormat("yyyy-MM-dd").parse(lastDateOfYear);
			aCalendar.setTime(lastDate);
			int dayL = aCalendar.get(Calendar.DAY_OF_YEAR);

			leaveDay = dayL - dayS + dayE;
		}

		// 天-轉時數 2.天數轉時數
		int dSumH = 0;
		if (leaveDay > 0) {
			dSumH = (leaveDay - 1) * 8;
		}

		// 時-轉時數 1.字串轉整數
		int startHours = Integer.valueOf(startH);
		int endHours = Integer.valueOf(endH);

		// 時-轉時數 2.判斷：若天數>1天(排除休息時間1小時)
		int hSumH = 0;
		if (leaveDay > 0) {
			int sSumH, eSumH;

			if (startHours <= 12) {
				sSumH = 17 - startHours - 1;
			} else {
				sSumH = 17 - startHours;
			}

			if (endHours >= 13) {
				eSumH = endHours - 8 - 1;
			} else {
				eSumH = endHours - 8;
			}
			hSumH = sSumH + eSumH;

			// 時-轉時數 3.判斷：若天數<1天(排除休息時間1小時)
		} else {
			if (endHours > 12) {
				hSumH = endHours - startHours - 1;
			} else {
				hSumH = endHours - startHours;
			}
		}

		// 分-轉時數
		int sM = Integer.valueOf(startM);
		int eM = Integer.valueOf(endM);
		double firstM = 0.0, lastM = 0.0;

		if (sM == 30) {
			firstM = -0.5;
		}

		if (eM == 30) {
			lastM = 0.5;
		}

		// 加總時數-轉成BigDecimal型別，設定SumHours
		double sum = dSumH + hSumH + firstM + lastM;
		BigDecimal sumHours = new BigDecimal(sum);
		return sumHours;
	}

	//搜尋請假資料表
	public List<ApplyForLeave> getTodayLeaveforTask(java.util.Date Time) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String hqlstr = "from ApplyForLeave where (:Time BETWEEN StartTime AND EndTime) and SigningProgress='同意'";
		Query<ApplyForLeave> query = session.createQuery(hqlstr, ApplyForLeave.class);
		query.setParameter("Time", Time);
		List<ApplyForLeave> list = query.list();
		session.getTransaction().commit();
		session.close();
		return list;
	}

//	主管登入查看有無新申請請需要簽核 by GK
	@Override
	public int queryNewApply(int ID) {
		Session session = sessionFactory.getCurrentSession();
		System.out.println("id:" + ID);
		Query Query = session.createQuery("from ApplyForLeave where signerID=:signerID and SigningProgress='未簽核'");
		Query.setInteger("signerID", ID);
		return Query.list().size();
	}

//	員工查詢是否主管以簽核或退件
	@Override
	public int querysucessApplyForLeave(int ID, String oldDate, String newDate) {
		Session session = sessionFactory.getCurrentSession();
		System.out.println("id:" + ID);
		Query Query = session.createQuery(
				"from ApplyForLeave where employeeID=:employeeID and SigningProgress<>'未簽核' and (ConfirmTime>:oldDate and ConfirmTime<:newDate)");
		Query.setInteger("employeeID", ID);
		Query.setString("oldDate", oldDate);
		Query.setString("newDate", newDate);
		return Query.list().size();
	}
//  End		

}
