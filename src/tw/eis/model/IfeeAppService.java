package tw.eis.model;


import java.util.List;

import tw.eis.model.feeAppMember;

public interface IfeeAppService {
	public boolean addFeeApp(String department,int employeeID,String appItem,String appTime,
			String invoiceTime,String invoiceNb,String editor,String remark,int appMoney,String signerTime,String signerStatus,int signerID);
	public List<feeAppMember> qFeeApp(int employeeID,String searchA,String searchB);
	public List<feeAppMember> qfeeSingerApp(String department, String signerStatus, int level);
	public List<feeAppMember> qapplyId(int feeAppID);
	public boolean EditFeeApp(int feeAppID, String signerStatus,String singerTimeint,int signerID);
	public List<feeAppMember> qfeeAppByID(int employeeID, String signerStatus);
	public boolean ReturnEditFee(int feeAppID, String appTime, String invoiceTime, String invoiceNb, String editor, int appMoney,
			String remark, String signerStatus);
}
