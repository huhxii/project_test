package mire;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBConnection {
	private Connection connection = null;
	private ResultSet rs = null;

	// connection
	public void connect() {
		// properties db.properies load
		// 1. properties
		Properties properties = new Properties();
		// 2. properties file load
		try {
			FileInputStream fis = new FileInputStream(
					"C:\\java_test\\studentProjectGit\\src\\mire\\db.properties");
			properties.load(fis);
		} catch (FileNotFoundException e) {
			System.out.println("FileInputStream error " + e.getStackTrace());
		} catch (IOException e) {
			System.out.println("Properties.load error " + e.getStackTrace());
		}
		try {
			Class.forName(properties.getProperty("driver"));
			connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("userid"),
					properties.getProperty("password"));
		} catch (ClassNotFoundException e) {
			System.out.println("class.forname load error " + e.getStackTrace());
		} catch (SQLException e) {
			System.out.println("connection error " + e.getStackTrace());
		}
	}

	// insert statement
	public int insert(Student student) {
		PreparedStatement ps = null;
		int insertReturnValue = -1;
		String insertQuery = "insert into student(no, name, kor, eng, math, total, avr, grade)"
				+ "values(?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			ps = connection.prepareStatement(insertQuery);
			ps.setString(1, student.getNo());
			ps.setString(2, student.getName());
			ps.setInt(3, student.getKor());
			ps.setInt(4, student.getEng());
			ps.setInt(5, student.getMath());
			ps.setInt(6, student.getTotal());
			ps.setDouble(7, student.getAvr());
			ps.setString(8, student.getGrade());
			// success -> return 1
			insertReturnValue = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("insert error " + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("PreparedStatement close error " + e.getMessage());
			}
		}
		return insertReturnValue;
	}

	// delete statement
	public int delete(String no) {
		PreparedStatement ps = null;
		int deleteReturnValue = -1;
		String deleteQuery = "delete from student where no = ?";
		try {
			ps = connection.prepareStatement(deleteQuery);
			ps.setString(1, no);
			// success -> return 1 ps.executeUpdate()
			deleteReturnValue = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("delete error " + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("PreparedStatement close error " + e.getMessage());
			}
		}
		return deleteReturnValue;
	}

	// select statement
	public List<Student> select() {
		List<Student> list = new ArrayList<Student>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String selectQuery = "select * from student";
		try {
			ps = connection.prepareStatement(selectQuery);
			// success -> return ResultSet, fail null ps.executeQuery()
			rs = ps.executeQuery(selectQuery);
			// 결과값이 없을때를 체크
			if (!(rs != null || rs.isBeforeFirst())) {
				return list;
			}
			// re.next() : 현재 커서에 있는 레코드 위치로 움직인다.
			while (rs.next()) {
				String no = rs.getString("no");
				String name = rs.getString("name");
				int kor = rs.getInt("kor");
				int eng = rs.getInt("eng");
				int math = rs.getInt("math");
				int total = rs.getInt("total");
				double avr = rs.getDouble("avr");
				String grade = rs.getString("grade");
				int rate = rs.getInt("rate");

				list.add(new Student(no, name, kor, eng, math, total, avr, grade, rate));
			}
		} catch (Exception e) {
			System.out.println("select error " + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("PreparedStatement close error " + e.getMessage());
			}
		}
		return list;
	}

	// select no or name search statement
	public List<Student> selectSearch(String data, int type) {
		List<Student> list = new ArrayList<Student>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String selectSearchQuery = "select * from student where ";
		try {
			switch (type) {
			case 1:
				selectSearchQuery += "no like ?";
				break;
			case 2:
				selectSearchQuery += "name like ?";
				break;
			default:
				System.out.println("잘못된 입력 방식입니다.");
				return list;
			}
			ps = connection.prepareStatement(selectSearchQuery);
			// success -> return ResultSet, fail null ps.executeQuery()
			ps.setString(1, "%" + data + "%");
			rs = ps.executeQuery();
			// 결과값이 없을때를 체크
			if (!(rs != null || rs.isBeforeFirst())) {
				return list;
			}
			// re.next() : 현재 커서에 있는 레코드 위치로 움직인다.
			while (rs.next()) {
				String no = rs.getString("no");
				String name = rs.getString("name");
				int kor = rs.getInt("kor");
				int eng = rs.getInt("eng");
				int math = rs.getInt("math");
				int total = rs.getInt("total");
				double avr = rs.getDouble("avr");
				String grade = rs.getString("grade");
				int rate = rs.getInt("rate");

				list.add(new Student(no, name, kor, eng, math, total, avr, grade, rate));
			}
		} catch (Exception e) {
			System.out.println("select error " + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("PreparedStatement close error " + e.getMessage());
			}
		}
		return list;
	}

	// update statement
	public int update(Student updateStudent) {
		PreparedStatement ps = null;
		int updateReturnValue = -1;
		String updateQuery = "update student set kor = ?, eng = ?, math = ?,"
				+ " total = ?, avr = ?, grade = ? where no = ?";
		try {
			ps = connection.prepareStatement(updateQuery);
			ps.setInt(1, updateStudent.getKor());
			ps.setInt(2, updateStudent.getEng());
			ps.setInt(3, updateStudent.getMath());
			ps.setInt(4, updateStudent.getTotal());
			ps.setDouble(5, updateStudent.getAvr());
			ps.setString(6, updateStudent.getGrade());
			ps.setString(7, updateStudent.getNo());
			// success -> return 1
			updateReturnValue = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("insert error " + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("PreparedStatement close error " + e.getMessage());
			}
		}
		return updateReturnValue;
	}

	// selectOrderBy statement
	public List<Student> selectOrderBy(int type) {
		List<Student> list = new ArrayList<Student>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String selectOrderByQuery = "select * from student order by ";
		try {
			switch (type) {
			case 1:
				selectOrderByQuery += "no asc";
				break;
			case 2:
				selectOrderByQuery += "name asc";
				break;
			case 3:
				selectOrderByQuery += "total desc";
				break;
			default:
				System.out.println("정렬 타입 오류");
				return list;
			}
			ps = connection.prepareStatement(selectOrderByQuery);
			// success -> return ResultSet, fail null ps.executeQuery()
			rs = ps.executeQuery();
			// 결과값이 없을때를 체크
			if (!(rs != null || rs.isBeforeFirst())) {
				return list;
			}
			// re.next() : 현재 커서에 있는 레코드 위치로 움직인다.
			int rank = 0;
			while (rs.next()) {
				String no = rs.getString("no");
				String name = rs.getString("name");
				int kor = rs.getInt("kor");
				int eng = rs.getInt("eng");
				int math = rs.getInt("math");
				int total = rs.getInt("total");
				double avr = rs.getDouble("avr");
				String grade = rs.getString("grade");
				int rate = rs.getInt("rate");
				if (type == 3) {
					rate = ++rank;
					// later update rate
				}
				list.add(new Student(no, name, kor, eng, math, total, avr, grade, rate));
			}
		} catch (Exception e) {
			System.out.println("select sort error " + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("PreparedStatement close error " + e.getMessage());
			}
		}
		return list;
	}

	// selectMaxMin statement
	public List<Student> selectMaxMin(int type) {
		List<Student> list = new ArrayList<Student>();
		Statement statement = null;
		ResultSet rs = null;
		String selectMaxMinQuery = "select * from student where total = ";
		try {
			switch (type) {
			case 1:
				selectMaxMinQuery += "(select max(total) from student)";
				break;
			case 2:
				selectMaxMinQuery += "(select min(total) from student)";
				break;
			default:
				System.out.println("입력값 오류");
				return list;
			}
			statement = connection.createStatement();
			// success -> return 1
			rs = statement.executeQuery(selectMaxMinQuery);
			if (!(rs != null || rs.isBeforeFirst())) {
				return list;
			}
			int rank = 0;
			while (rs.next()) {
				String no = rs.getString("no");
				String name = rs.getString("name");
				int kor = rs.getInt("kor");
				int eng = rs.getInt("eng");
				int math = rs.getInt("math");
				int total = rs.getInt("total");
				double avr = rs.getDouble("avr");
				String grade = rs.getString("grade");
				int rate = rs.getInt("rate");
				if (type == 3) {
					rate = ++rank;
					// later update rate
				}
				list.add(new Student(no, name, kor, eng, math, total, avr, grade, rate));
			}
		} catch (Exception e) {
			System.out.println("select sort error " + e.getMessage());
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.out.println("PreparedStatement close error " + e.getMessage());
			}
		}
		return list;
	}

	// connection close
	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println("connection close error" + e.getMessage());
		}
	}
}
