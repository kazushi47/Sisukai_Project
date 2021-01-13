
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * 認証時にEmpを作成する
 * @author 5192006
 *
 */

public class Auth {

	private static final int		ONE 				= 1;/*汎用*/
	private static final int		TWO 				= 2;/*汎用*/
	private static final int[]	MANAGERID 			= {3,4};/*管理職ID*/
	private static final String	DIVNAME 			= "経理部";/*経理部認証*/
	private static final String	SQL_EMPID 			= "empId";/*SQLで使用*/
	private static final String	SQL_NAME 			= "name";/*SQLで使用*/
	private static final String 	SQL_DIVNAME 		= "divName";/*SQLで使用*/
	private static final String 	SQL_JOBTITLEGRADE	= "jobTitleGrade ";/*SQLで使用*/
	private static final String	A 					= "A";/*出退勤サブシステム*/
	private static final String	S 					= "S";/*給与計算サブシステム*/
	private static final String	SQL 				= "SELECT " + SQL_EMPID + "," + SQL_NAME /*オペレータ情報の抽出SQL*/
																+ ",e.divId," + SQL_DIVNAME
																+ " FROM employees e "
																+ "JOIN divisions d ON e.divId = d.divId "
																+ "WHERE empId = ? AND pw = ?";
	private static final String SQL_CHECKJOB = "SELECT * FROM employees e JOIN jobTitleSalarys j ON e." + SQL_JOBTITLEGRADE +  " = j." + SQL_JOBTITLEGRADE;

	/**
	 * 認証に失敗した場合、nullを返却します<br>
	 *authTypeは"A"または"S"を指定してください
	 * @param id
	 * @param pw
	 * @param authType
	 * @return
	 */
	static Emp authUser(String id,String pw,String authType){
		Emp op = null;
		try(Connection con = DBconnect.getConnection()){
			PreparedStatement stmt = con.prepareStatement(SQL);
			stmt.setInt(ONE, Integer.parseInt(id));
			stmt.setString(TWO, pw);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) //認証確認
				switch(authType) {
					case A :
						op = new Emp(rs.getString(SQL_EMPID),rs.getString(SQL_NAME),rs.getString(SQL_DIVNAME));
						break;
					case S :
						if(rs.getString(SQL_DIVNAME).equals(DIVNAME))//経理部認証
							op = new Emp(rs.getString(SQL_EMPID),rs.getString(SQL_NAME),rs.getString(SQL_DIVNAME));
						stmt = con.prepareStatement(SQL_CHECKJOB);
						rs = stmt.executeQuery();
						if(rs.next()) {
							int tmpPosId = rs.getInt(SQL_JOBTITLEGRADE.strip());
							if(Arrays.stream(MANAGERID).anyMatch(mgId -> mgId == tmpPosId))op.setMgFlg(true);
						}
						break;
				}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(NumberFormatException e) {
			return null;
		}
		return op;
	}



}
