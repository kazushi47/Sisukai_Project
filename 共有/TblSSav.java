package jp.ac.hsc.mainSystem;



/**
 * SSavテーブルの一行分のデータを管理するクラス
 * @author 5192006
 *
 */
public class TblSSav {

	String	employeeId;
	String	date;
	String	ageSalary;
	String	abilitySalary;
	String	jobTitleSalary;
	String	specialWorkSalary;
	String	controlSalary;
	String	commuteSalary;
	String	businessTripSalary;
	String	overWorkSalary;
	String	holidayWorkSalary;
	String	nightWorkingSalary;
	String	specialHolidaySalary;
	String	deduction;
	String	overWorkTime;
	String	holidayWorkTime;
	String	nightWorkTime;

	public TblSSav(String employeeId, String date, String ageSalary, String abilitySalary, String jobTitleSalary,
			String specialWorkSalary, String controlSalary, String commuteSalary, String businessTripSalary,
			String overWorkSalary, String holidayWorkSalary, String nightWorkingSalary, String specialHolidaySalary,
			String deduction, String overWorkTime, String holidayWorkTime, String nightWorkTime,
			String targetspecialHolidays, String notWorkTime, String paidHolidays) {
		super();
		this.employeeId = employeeId;
		this.date = date;
		this.ageSalary = ageSalary;
		this.abilitySalary = abilitySalary;
		this.jobTitleSalary = jobTitleSalary;
		this.specialWorkSalary = specialWorkSalary;
		this.controlSalary = controlSalary;
		this.commuteSalary = commuteSalary;
		this.businessTripSalary = businessTripSalary;
		this.overWorkSalary = overWorkSalary;
		this.holidayWorkSalary = holidayWorkSalary;
		this.nightWorkingSalary = nightWorkingSalary;
		this.specialHolidaySalary = specialHolidaySalary;
		this.deduction = deduction;
		this.overWorkTime = overWorkTime;
		this.holidayWorkTime = holidayWorkTime;
		this.nightWorkTime = nightWorkTime;
		this.targetspecialHolidays = targetspecialHolidays;
		this.notWorkTime = notWorkTime;
		this.paidHolidays = paidHolidays;
	}
	String	targetspecialHolidays;
	String	notWorkTime;
	String	paidHolidays;

}
