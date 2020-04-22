package tw.eis.model.dao;

import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import tw.eis.model.Department;
import tw.eis.model.Employee;
import tw.eis.model.Title;
import tw.eis.model.Users;

@Repository("employeeDao")
public class EmployeeDao implements IEmployeeDao {

	private SessionFactory sessionFactory;

	public EmployeeDao() {

	}

	@Autowired
	public EmployeeDao(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<?> allEmpData() {
		DetachedCriteria mainQuery = DetachedCriteria.forClass(Users.class);
		List<?> list = mainQuery.getExecutableCriteria(sessionFactory.getCurrentSession()).list();
		return list;
	}

	@Override
	public List<?> queryEmp(int id, String Name, String Department) {
		DetachedCriteria mainQuery = DetachedCriteria.forClass(Employee.class);
		if (id != 0) {
			mainQuery.add(Restrictions.eq("empID", id));
		}
		if (!Name.equals("na")) {
			mainQuery.add(Restrictions.eq("name", Name));
		}
		if (!Department.equals("na")) {
			mainQuery.add(Restrictions.eq("department", Department));
		}
		List<?> list = mainQuery.getExecutableCriteria(sessionFactory.getCurrentSession()).list();
		return list;
	}

	@Override
	public Employee empData(int id) {
		return sessionFactory.getCurrentSession().get(Employee.class, id);
	}

	@Override
	public boolean editEmp(int id, String Title, int Level, String Department, Employee Manager, String Name,
			String Gender, Date BirthDay, String Address, String ExtensionNum, String PhoneNum, String Email,
			byte[] Photo, int Salary, Date HireDay, Date LastWorkDay, Department EmpDept, Title EmpTitle) {

		try {
			Session session = sessionFactory.getCurrentSession();
			Employee myEmp = session.get(Employee.class, id);
			Users myUser = session.get(Users.class, id);

			myUser.setDepartment(Department);
			myUser.setTitle(Title);

			myEmp.setTitle(Title);
			myEmp.setDepartment(Department);
			myEmp.setEmpTitle(EmpTitle);
			myEmp.setLevel(Level);

			myEmp.setName(Name);
			if (Manager != null) {
				myEmp.setManager(Manager);
			} else {
				myEmp.setManager(null);
			}
			if (Gender != null) {
				myEmp.setGender(Gender);
			}
			if (BirthDay != null) {
				myEmp.setBirthDay(BirthDay);
			}
			if (Address != null) {
				myEmp.setAddress(Address);
			}
			if (ExtensionNum != null) {
				myEmp.setExtensionNum(ExtensionNum);
			}
			if (PhoneNum != null) {
				myEmp.setPhoneNum(PhoneNum);
			}
			if (Email != null) {
				myEmp.setEmail(Email);
			}
			if (Photo != null) {
				myEmp.setPhoto(Photo);
			}
			if (Salary != 0) {
				myEmp.setSalary(Salary);
			}
			if (HireDay != null) {
				myEmp.setHireDay(HireDay);
			}
			if (LastWorkDay != null) {
				myEmp.setLastWorkDay(LastWorkDay);
			}
			if (EmpDept != null) {
				myEmp.setEmpDept(EmpDept);
			}

			session.update(myUser);
			session.update(myEmp);
			return true;
		} catch (Exception e) {
			System.out.println("類別EmployeeDao,editEmp方法發生例外：" + e);
			return false;
		}

	}

	@Override
	public List<?> managerDataByDeptId(int deptId) {
		List<?> list = null;
		if (deptId != 0) {
			DetachedCriteria mainQuery = DetachedCriteria.forClass(Employee.class);
			DetachedCriteria subQuery = DetachedCriteria.forClass(Title.class);
			Property level = Property.forName("level");
			subQuery.setProjection(Property.forName("titleID"))
					.add(Restrictions.disjunction().add(level.eq(2)).add(level.eq(3)));
			list = mainQuery.add(Property.forName("empTitle").in(subQuery))
					.add(Property.forName("empDept.deptID").eq(deptId)).getExecutableCriteria(sessionFactory.getCurrentSession()).list();
		}
		return list;
	}

	public List<Employee> findEmployeeByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		Query<Employee> query = session.createQuery("from Employee where email = :email", Employee.class);
		query.setParameter("email", email);
		List<Employee> list = query.list();
		return list;
	}

	@Override
	public List<?> empDataByManagerId(int managerId) {
		DetachedCriteria mainQuery = DetachedCriteria.forClass(Employee.class);
		List<?> list = mainQuery.add(Restrictions.eq("manager.empID", managerId))
				.getExecutableCriteria(sessionFactory.getCurrentSession()).list();
		return list;
	}

	@Override
	public List<?> empDataByDeptId(int deptId) {
		DetachedCriteria mainQuery = DetachedCriteria.forClass(Employee.class);
		List<?> list = mainQuery.add(Property.forName("empDept.deptID").eq(deptId))
				.getExecutableCriteria(sessionFactory.getCurrentSession()).list();
		return list;
	}

	@Override
	public List<?> empDataByTitleId(int titleId) {
		DetachedCriteria mainQuery = DetachedCriteria.forClass(Employee.class);
		List<?> list = mainQuery.add(Property.forName("empTitle.titleID").eq(titleId))
				.getExecutableCriteria(sessionFactory.getCurrentSession()).list();
		return list;
	}

	public void test() {
	}
}