package jp.ac.hsc.mainSystem;

public class Emp {
	private String empId;
	private String name;
	private String divName;
	private boolean mgFlg = false;//managerFlag

	public Emp(String empId, String empName, String divName) {
		this.empId = empId;
		this.name = empName;
		this.divName = divName;
	}

	public String getEmpId() {
		return empId;
	}

	public String getName() {
		return name;
	}

	public String getDivName() {
		return divName;
	}

	public String[] getAllData() {
		return new String[]{empId,name,divName};
	}

	public boolean getMgFlg() {
		return mgFlg;
	}

	public void setMgFlg(boolean flg) {
		mgFlg = flg;
	}
}
