
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


class Analyze{
	private static String calculate(int uid, String periodicity){
		PreparedStatement ps = null;
		String csv = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String jdbcUrl = System.getenv("JDBC_URL");  
			if (jdbcUrl == null) {
				jdbcUrl = "jdbc:mysql://localhost:3306/wallet?allowMultiQueries=true&sessionVariables=sql_mode='PIPES_AS_CONCAT'";
			}
			
			String jdbcUser = System.getenv("JDBC_USER");
			if (jdbcUser == null) {
				jdbcUser = "root";
			}

			String jdbcPass = System.getenv("JDBC_PASS");
			if (jdbcPass == null) {
				jdbcPass = "root";
			}

			Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);
			//connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wallet?allowMultiQueries=true&sessionVariables=sql_mode='PIPES_AS_CONCAT'","root", "root");
			
			
			
			String groupby = "";
			// System.out.println(periodicity);
			if (periodicity.equals("yearly")) {
				groupby = "lpad(YEAR(time), 4, 0)";
			}
			else if (periodicity.equals("monthly")) {
				groupby = "concat(lpad(YEAR(time), 4, 0), lpad(MONTH(time),2,0))";
			}
			else if (periodicity.equals("daily")) {
				groupby = "concat(lpad(YEAR(time), 4, 0), lpad(MONTH(time),2,0), lpad(DAY(time),2,0))";
			}
			String query = "SELECT " + groupby + " ,sum(amount) from transactions where user=? group by " + groupby;
			// System.out.println("query: " + query);
			ps = conn.prepareStatement(query);
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				csv = csv + rs.getString(1) + ",";
				csv = csv + rs.getInt(2) + "\n";
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(csv);
		return csv;
	}
	public static void main(String args[]){
		int uid = Integer.parseInt(args[0]);
		String periodicity = args[1];
		// System.out.println("uid: "+args[0]);
		// System.out.println("periodicity: "+args[1]);
		calculate(uid, periodicity);
	}  
} 