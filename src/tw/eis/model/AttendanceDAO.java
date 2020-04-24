package tw.eis.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tw.eis.model.Attendance;
import tw.eis.util.GlobalService;

@Repository
public class AttendanceDAO {

	private SessionFactory sessionFacotry;
	//private UsersService uService;

	@Autowired
	public AttendanceDAO(@Qualifier(value = "sessionFactory") SessionFactory sessionFacotry) {
		this.sessionFacotry = sessionFacotry;
	}
	
//	@Autowired
//	public AttendanceDAO(UsersService uService) {
//		this.uService = uService;
//	}

	public List<Attendance> InquiryToday(Map<String, String> usersResultMap) {
		try {
			Session session = sessionFacotry.getCurrentSession();
			SimpleDateFormat nowdate = new SimpleDateFormat("yyyy-MM-dd");
			nowdate.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			String today = nowdate.format(new Date());
			String hqlstr = "from Attendance where EmployeeID=:EmployeeID and Date =:Date";
			Query<Attendance> query = session.createQuery(hqlstr, Attendance.class);
			query.setParameter("EmployeeID", usersResultMap.get("EmployeeID"));
			query.setParameter("Date", today);
			List<Attendance> myPunch = query.list();
			return myPunch;
		} catch (Exception e) {
			System.out.println("e:" + e);
		}
		return null;
	}

	public List<Attendance> InquiryAttendance(String Id, String month) {
		try {
			Session session = sessionFacotry.getCurrentSession();
			String hqlstr = "from Attendance where EmployeeID=:id and Date like :Month";
			Query<Attendance> query = session.createQuery(hqlstr, Attendance.class);
			query.setParameter("id", Id);
			query.setParameter("Month", month + "%");

			List<Attendance> attlist = query.list();

			return attlist;
		} catch (Exception e) {
			System.out.println("e:" + e);
		}
		return null;
	}

	public boolean InsertStartTime(Map<String, String> usersResultMap, java.sql.Date Date, java.sql.Time Time) {
		try {
			Session session = sessionFacotry.getCurrentSession();
			Attendance attendance = new Attendance();
			//attendance.setUsers(uService.userData(Integer.parseInt(usersResultMap.get("EmployeeID"))));
			attendance.setDate(Date);
			attendance.setStartTime(Time);
			session.save(attendance);
		} catch (Exception e) {
			System.out.println("e:" + e);
		}
		return true;
	}

	public boolean InsertEndTime(Map<String, String> usersResultMap, java.sql.Date Date, java.sql.Time Time) {
		try {
			Session session = sessionFacotry.getCurrentSession();
			Attendance attendance = new Attendance();
			//attendance.setUsers(uService.userData(Integer.parseInt(usersResultMap.get("EmployeeID"))));
			attendance.setDate(Date);
			attendance.setEndTime(Time);
			session.save(attendance);
		} catch (Exception e) {
			System.out.println("e:" + e);
		}
		return true;
	}

	public boolean UpdateEndTime(Map<String, String> usersResultMap, java.sql.Date Date, java.sql.Time Time) {
		try {
			Session session = sessionFacotry.getCurrentSession();
			String hqlstr = "Update Attendance SET EndTime=:Time where Date=:Date and EmployeeID=:EmployeeID";
			Query query = session.createQuery(hqlstr);
			query.setParameter("Time", Time);
			query.setParameter("Date", Date);
			query.setParameter("EmployeeID", usersResultMap.get("EmployeeID"));
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println("e:" + e);
		}
		return true;
	}
	

	public boolean UpdateStatus(Map<String, String> usersResultMap, java.sql.Date Date, String Status) {
		try {
			Session session = sessionFacotry.getCurrentSession();
			String hqlstr = "Update Attendance SET Status=:Status where Date=:Date and EmployeeID=:EmployeeID";
			Query query = session.createQuery(hqlstr);
			query.setParameter("Status", Status);
			query.setParameter("Date", Date);
			query.setParameter("EmployeeID", usersResultMap.get("EmployeeID"));
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println("e:" + e);
		}
		return true;
	}
		public List<Attendance> InquiryAllToday() {
		Session session = sessionFacotry.getCurrentSession();
		session.beginTransaction();
		SimpleDateFormat nowdate = new SimpleDateFormat("yyyy-MM-dd");
		nowdate.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		String today = nowdate.format(new Date());
		String hqlstr = "from Attendance where Date like:today";
		Query<Attendance> query = session.createQuery(hqlstr, Attendance.class);
		query.setParameter("today", today);
		List<Attendance> AllToday = query.list();
		session.getTransaction().commit();
		session.close();
		return AllToday;
	}

	public boolean UpdateAttendanceStatus(java.sql.Date Date, int Id, String Status) {
		Session session = sessionFacotry.getCurrentSession();
		session.beginTransaction();
		String hqlstr = "Update Attendance SET Status=:Status where Date=:Date and EmployeeID=:EmployeeID";
		Query query = session.createQuery(hqlstr);
		query.setParameter("Status", Status);
		query.setParameter("Date", Date);
		query.setParameter("EmployeeID", Id);
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
		return true;
	}
	
	public boolean NewAttendance(Employee Emp,java.sql.Date Date) {
		Session session = sessionFacotry.getCurrentSession();
		session.beginTransaction();
		Attendance attendance = new Attendance();
		attendance.setEmployee(Emp);
		attendance.setDate(Date);
		session.save(attendance);
		session.getTransaction().commit();
		session.close();		
		return true;
	}
	
	public List<?> StatusErrorTimes(String Id, String month) {
		Session session = sessionFacotry.getCurrentSession();
		session.beginTransaction();
		String hqlstr = "from Attendance where EmployeeID=:id and Date like :Month";
		Query<Attendance> query = session.createQuery(hqlstr, Attendance.class);
		query.setParameter("id", Id);
		query.setParameter("Month", month + "%");
		List<Attendance> attlist = query.list();
		session.getTransaction().commit();
		session.close();
		return attlist;
	}

	public List<?> queryEmpAttendanceData(int empId, String Name, String Department,java.sql.Date StartDate,java.sql.Date EndDate) {
		DetachedCriteria mainQuery = DetachedCriteria.forClass(Attendance.class);
		DetachedCriteria subQuery = DetachedCriteria.forClass(Employee.class);
		subQuery.add(Restrictions.or(Restrictions.gt("lastWorkDay", GlobalService.dateOfToday()),
				Restrictions.isNull("lastWorkDay")));
		subQuery.setProjection(Property.forName("empID"));
		if(StartDate!=null && EndDate!=null) {
			mainQuery.add(Restrictions.between("date", StartDate, EndDate));
		}
		
		if(StartDate!=null) {
			mainQuery.add(Restrictions.ge("date", StartDate));
		}
		
		if(EndDate!=null) {
			mainQuery.add(Restrictions.le("date", EndDate));
		}
		
		if (empId != 0) {
			subQuery.add(Restrictions.eq("empID", empId));
		}
		if (!Name.equals("na")) {
			subQuery.add(Restrictions.eq("name", Name));
		}
		if (!Department.equals("na")) {
			subQuery.add(Restrictions.eq("department", Department));
		}
		List<?> list = mainQuery.add(Property.forName("employee").in(subQuery))
				.getExecutableCriteria(sessionFacotry.getCurrentSession()).list();
		return list;
	}

}
