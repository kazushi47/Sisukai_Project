package jp.ac.hsc.mainSystem;

import java.sql.Timestamp;


/**
 * SAvテーブルの一行分のデータを管理するクラス
 * @author 5192006
 *
 */
public class TblSAv {

	String id;
	Timestamp date;
	Timestamp attendanceTime;
	Timestamp leavingTime;
	String breakTime;
	String directBounce;
	String absence;
	String lateness;
	String leaveEarly;
	String paidHoliday;
	String businessTripType;
	String specialHolidayType;

}
