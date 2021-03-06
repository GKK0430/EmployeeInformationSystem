package tw.eis.model;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metamodel.model.domain.spi.SetPersistentAttribute;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import tw.eis.model.Department;
import tw.eis.model.DepartmentalAnnualGoals;
import tw.eis.model.Employee;
import tw.eis.model.PersonalQuarterlyTarget;
import tw.eis.model.Title;
import tw.eis.model.WorkProject;

@Repository
public class WorkProjectDAO {
	private SessionFactory sessionFactory;
	private JSONArray j;

	@Autowired
	public WorkProjectDAO(@Qualifier(value = "sessionFactory") SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
		
//	public JSONArray getassignwork(Model m, int pid) {
//		Session session = sessionFactory.getCurrentSession();
//		String hqlstr = "From WorkProject Where pID =:pid";
//		String hqlawork = "From AssignWork Where wID =: wid";
//		String hqlemp = "From Employee Where empID =: empid";
//		Query<WorkProject> query = session.createQuery(hqlstr,WorkProject.class);
//		Query<AssignWork> awquery = session.createQuery(hqlawork,AssignWork.class);
//		Query<Employee> empquery = session.createQuery(hqlemp,Employee.class);
//		query.setParameter("pid", pid);
//		List<WorkProject> wlist = query.list();
//		JSONArray ja = new JSONArray();
//		for(int i=0;i<wlist.size();i++) {
//			List<AssignWork> awlist = awquery.setParameter("wid", wlist.get(i).getwID()).list();
//			JSONArray jay = new JSONArray();
//			for(AssignWork aw:awlist) {
//				Employee e = empquery.setParameter("empid", aw.getEmpID()).uniqueResult();
//				JSONObject jb = new JSONObject();
//				jb.put("pqt", wlist.get(i).getPersonalQuarterlyTarget());
//				jb.put("Work", wlist.get(i).getWork());
//				jb.put("wid", wlist.get(i).getwID());
//				jb.put("empid", aw.getEmpID());
//				jb.put("empname",e.getName());
//				jay.put(jb);
//			}
//			ja.put(jay);
//		}
//		j = ja;
//		return j;
//	}
	public JSONArray getassignwork(Model m, int pid) {
		Session session = sessionFactory.getCurrentSession();
		String hqlstr = "From WorkProject Where pID =:pid";
		String hqlawork = "From AssignWork Where wID =: wid";
		String hqlemp = "From Employee Where empID =: empid";
		Query<WorkProject> query = session.createQuery(hqlstr,WorkProject.class);
		Query<AssignWork> awquery = session.createQuery(hqlawork,AssignWork.class);
		Query<Employee> empquery = session.createQuery(hqlemp,Employee.class);
		query.setParameter("pid", pid);
		List<WorkProject> wlist = query.list();
		JSONArray ja = new JSONArray();
		for(int i=0;i<wlist.size();i++) {
			JSONObject jb = new JSONObject();
			jb.put("pqt", wlist.get(i).getPersonalQuarterlyTarget());
			jb.put("Work", wlist.get(i).getWork());
			jb.put("wid", wlist.get(i).getwID());
			JSONArray jay = new JSONArray();
			List<AssignWork> awlist = awquery.setParameter("wid", wlist.get(i).getwID()).list();
			//System.out.print("awlst長度:"+awlist.size());
			if(awlist.size()==0) {
				jay.put(jb);
			}
			for(AssignWork aw:awlist) {
				JSONObject jb2 = new JSONObject();
				jb2.put("awid", aw.getAwID());
				jb2.put("pqt", wlist.get(i).getPersonalQuarterlyTarget());
				jb2.put("Work", wlist.get(i).getWork());
				jb2.put("wid", wlist.get(i).getwID());
				Employee e = empquery.setParameter("empid", aw.getEmpID()).uniqueResult();
				//System.out.println("empid:"+aw.getEmpID());
				jb2.put("empid", e.getEmpID());
				//System.out.println("empname:"+e.getName());
				jb2.put("empname",e.getName());
				jay.put(jb2);
	
			}
			//System.out.print("=========================="+jay);
			ja.put(jay);
			
		}
		//System.out.print("-------------------------"+ja);
		j = ja;
		return j;
	}
	
	public JSONArray getworkdata1(int deptID, Model m) {
		try {
			Session session = sessionFactory.getCurrentSession();
			String hqlstr = "From PersonalQuarterlyTarget Where deptID =:deptID";
			Query<PersonalQuarterlyTarget> query = session.createQuery(hqlstr, PersonalQuarterlyTarget.class);
			query.setParameter("deptID", deptID);
			List<PersonalQuarterlyTarget> plist = query.list();
			JSONArray ja = new JSONArray();
			for (int i = 0; i < plist.size(); i++) {
				String hqlwork = "From WorkProject Where pID =:pid";
				Query<WorkProject> wquery = session.createQuery(hqlwork, WorkProject.class);
				List<WorkProject> wlist = wquery.setParameter("pid", plist.get(i).getPid()).list();
				JSONArray jay = new JSONArray();
				for (WorkProject w : wlist) {
					JSONObject jb = new JSONObject();
					jb.put("pID", plist.get(i).getPid());
					jb.put("PersonalQuarterlyTarget", plist.get(i).getPersonalQuarterlyTarget());
//					System.out.println(plist.get(i).getPersonalQuarterlyTarget());
					jb.put("Work", w.getWork());
					jb.put("wid", w.getwID());
					jb.put("worksetter",w.getWorkSetter());
					jb.put("time", w.getDate());
					jay.put(jb);
				}
				ja.put(jay);

			}
			j = ja;
			System.out.println("==========="+j);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}
	public void insertwork(PersonalQuarterlyTarget p, WorkProject w,Model m) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(p);
			System.out.print("WorkProject Save Success");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//嚙踝蕭pid嚙緬嚙踝蕭嚙踝蕭嚙磕嚙踝蕭嚙諸數改蕭嚙�?
	public PersonalQuarterlyTarget getpqt(int pid,Model m ) {
		Session session = sessionFactory.getCurrentSession();
		String sqlstr = "From PersonalQuarterlyTarget Where pID =: pid";
		Query<PersonalQuarterlyTarget> query = session.createQuery(sqlstr,PersonalQuarterlyTarget.class);
		query.setParameter("pid", pid);
		PersonalQuarterlyTarget p = query.uniqueResult();
		return p;
	}
	public void changework(Model m,int wid,String work,String worksetter,Timestamp timestamp) {
		Session session = sessionFactory.getCurrentSession();
		String sqlstr = "From WorkProject Where wID =: wid";
		Query<WorkProject> query = session.createQuery(sqlstr,WorkProject.class);
		WorkProject w = query.setParameter("wid", wid).uniqueResult();
		w.setWorkSetter(worksetter);
		w.setWork(work);
		w.setDate(timestamp);
		session.save(w);
	}
	public void deletework(Model m,int wid) {
		Session session = sessionFactory.getCurrentSession();
		String sqlstr = "From WorkProject Where wID =: wid";
		Query<WorkProject> query = session.createQuery(sqlstr,WorkProject.class);
		WorkProject w = query.setParameter("wid", wid).uniqueResult();
		session.delete(w);
	}
	public int getdeptid(Model m,String dept) {
		Session session = sessionFactory.getCurrentSession();
		String sqlstr = "From Department Where deptAbb =: dept";
		Query<Department> query = session.createQuery(sqlstr,Department.class);
		Department d = query.setParameter("dept", dept).uniqueResult();
		return d.getDeptID();
	}
	public int getlevel(Model m,String title) {
		Session session = sessionFactory.getCurrentSession();
		String sqlstr = "From Title Where titleName =:title";
		Query<Title> query = session.createQuery(sqlstr,Title.class);
		Title t = query.setParameter("title", title).uniqueResult();
		return t.getLevel();
	}
	public String getdag(Model m,int deptid) {
		Session session = sessionFactory.getCurrentSession();
		String sqlstr = "From DepartmentalAnnualGoals Where deptID =: deptid";
		Query<DepartmentalAnnualGoals> query = session.createQuery(sqlstr,DepartmentalAnnualGoals.class);
		DepartmentalAnnualGoals dag = query.setParameter("deptid", deptid).uniqueResult();
		return dag.getDepartmentAnnualGoal();
	}
	public String getusername(Model m,int empid) {
		Session session = sessionFactory.getCurrentSession();
		String sqlstr = "From Employee Where empID =: empid";
		Query<Employee> query = session.createQuery(sqlstr,Employee.class);
		Employee e = query.setParameter("empid", empid).uniqueResult();
		return e.getName();
	}
	public JSONArray getdeptusername(Model m,String dept) {
		Session session = sessionFactory.getCurrentSession();
		String sqlstr = "From Employee Where department =: dept and level = 1";
		Query<Employee> query = session.createQuery(sqlstr,Employee.class);
		List<Employee> elist = query.setParameter("dept", dept).list();
		JSONArray ja = new JSONArray();
		for(Employee e:elist) {
			JSONObject jb = new JSONObject();
			jb.put("name", e.getName());
			jb.put("empid", e.getEmpID());
			ja.put(jb);
		}
		j = ja;
		return j;
	}
}
