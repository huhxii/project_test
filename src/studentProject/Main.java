package studentProject;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
	public static Scanner sc = new Scanner(System.in);
	public static final int INPUT = 1, UPDATE = 2, DELETE = 3, SEARCH = 4, PRINT = 5;
	public static final int SORT = 6, STATS = 7, EXIT = 8;
	public static final int NUM = 1, NAME = 2, SCORE = 3, THREE = 4, TWO = 5;

	public static void main(String[] args) {
		DBConnection dbConn = new DBConnection();
		// database connection
		dbConn.connect();
		boolean flag = false;
		while (!flag) {
			int task = displayMenu();
			switch (task) {
			case INPUT:
				studentInput();
				break;
			case UPDATE:
				studentUpdate();
				break;
			case DELETE:
				studentDelete();
				break;
			case SEARCH:
				studentSearch();
				break;
			case PRINT:
				studentPrint();
				break;
			case SORT:
				studentSort();
				break;
			case STATS:
				studentStats();
				break;
			case EXIT:
				System.out.println("Á¾·á.");
				break;
			default:
				System.out.println("1~8¹ø °ªÀ» ÀÔ·ÂÇØÁÖ¼¼¿ä.");
				break;
			}// end of switch-case
		} // end of while
		System.out.println("Á¾·á");

	}

	// displayMenu
	private static int displayMenu() {
		int num = -1;
		try {
			System.out.println("¦£¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¨¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¨¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¨¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¨¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¨¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¨¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¨¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¤");
			System.out.println("|  1. ÀÔ·Â  |  2. ¼öÁ¤  |  3. »èÁ¦  |  4. °Ë»ö  |  5. Ãâ·Â  |  6. Á¤·Ä  |  7. Åë°è  |  8. Åë°è  |");
			System.out.println("¦¦¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦ª¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦ª¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦ª¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦ª¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦ª¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦ª¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦ª¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¡¦¥");
			System.out.print("ÀÔ·Â >> ");
			num = sc.nextInt();
			// Á¤¼öÆÐÅÏ°Ë»ö
			String pattern = "^[1-8]$"; // ¼ýÀÚ¸¸
			String data = null;
			boolean regex = Pattern.matches(pattern, String.valueOf(num));
			if (regex == false) {
				data = "¹üÀ§¸¦ ¹þ¾î³­ ¼ýÀÚÀÔ´Ï´Ù.";
				System.out.println(num + "Àº/´Â " + data);
			}
		} catch (InputMismatchException e) {
			System.out.println("¼ýÀÚ¸¦ ÀÔ·ÂÇÏÁö ¾Ê¾Ò½À´Ï´Ù.");
			num = -1;
		} finally {
			sc.nextLine();
		}
		return num;
	}

	// studentInput -> connect -> database (insert)
	private static void studentInput() {
		String pattern = null;
		String no = null;
		try {
			// no
			System.out.print("ÇÐ»ý¹øÈ£¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä. ¿¹)10101 >> ");
			no = sc.nextLine();
			// ¹®ÀÚ¿­ÆÐÅÏ°Ë»ö
			boolean value = checkInputPattern(no, NUM);
			pattern = "^[1-3][0-9]+{1,2}[0-3][0-9]$";
			value = Pattern.matches(pattern, String.valueOf(no));
			if (!value) {
				System.out.println(no + "Àº/´Â ¹üÀ§¸¦ ¹þ¾î³­ ¼ýÀÚÀÔ´Ï´Ù.");
			} else {
				System.out.println("ÀÔ·Â ¿Ï·á");
			}
			// name
			System.out.print("ÀÌ¸§À» ÀÔ·ÂÇØÁÖ¼¼¿ä >> ");
			String name = sc.nextLine();
			// ¹®ÀÚ¿­ÆÐÅÏ°Ë»ö
			value = checkInputPattern(name, NAME);
			// score
			System.out.print("±¹¾îÁ¡¼ö ÀÔ·Â >> ");
			int kor = sc.nextInt();
			// kor pattern
			value = checkInputPattern(String.valueOf(kor), SCORE);
			if (!value)
				return;
			System.out.print("¿µ¾îÁ¡¼ö ÀÔ·Â >> ");
			int eng = sc.nextInt();
			// eng pattern
			value = checkInputPattern(String.valueOf(eng), SCORE);
			if (!value)
				return;
			System.out.print("¼öÇÐÁ¡¼ö ÀÔ·Â >> ");
			int math = sc.nextInt();
			// math pattern
			value = checkInputPattern(String.valueOf(math), SCORE);
			if (!value)
				return;
			// Student object
			Student student = new Student(no, name, kor, eng, math);
			student.calTotal();
			student.calAvr();
			student.calGrade();
			// insert database
			DBConnection dbConn = new DBConnection();
			// database connection
			dbConn.connect();
			// article table data
			int insertReturnValue = dbConn.insert(student);
			if (insertReturnValue == -1) {
				System.out.println("»ðÀÔ ½ÇÆÐ");
			} else {
				System.out.println("»ðÀÔ ¼º°ø");
			}
			dbConn.close();
		} catch (InputMismatchException e) {
			System.out.println("ÀÔ·Â°ªÀÌ ¸ÂÁö ¾Ê½À´Ï´Ù.");
			return;
		} finally {
			sc.nextLine();
		}
	}

	// studentUpdate
	private static void studentUpdate() {
		List<Student> list = new ArrayList<Student>();
		System.out.print("°Ë»öÇÒ ÇÐ»ý¹øÈ£ ÀÔ·Â >> ");
		String no = sc.nextLine();
		// pattern
		boolean value = checkInputPattern(no, 1);
		if (!value) {
			return;
		}
		// update
		// insert database
		DBConnection dbConn = new DBConnection();
		// database connection
		dbConn.connect();
		// student table data
		list = dbConn.selectSearch(no, 1);
		if (list.size() <= 0) {
			System.out.println("°Ë»öÇÑ ÇÐ»ýÁ¤º¸°¡ ¾ø½À´Ï´Ù." + list.size());
			return;
		}
		for (Student student : list) {
			System.out.println(student);
		}
		// search list
		Student updateStudent = list.get(0);
		System.out.print("±¹¾îÁ¡¼ö " + updateStudent.getKor() + ">> ");
		int kor = sc.nextInt();
		value = checkInputPattern(String.valueOf(kor), 3);
		if (!value)
			return;
		updateStudent.setKor(kor);
		System.out.print("¿µ¾îÁ¡¼ö " + updateStudent.getEng() + ">> ");
		int eng = sc.nextInt();
		value = checkInputPattern(String.valueOf(eng), 3);
		if (!value)
			return;
		updateStudent.setKor(eng);
		System.out.print("¼öÇÐÁ¡¼ö " + updateStudent.getMath() + ">> ");
		int math = sc.nextInt();
		value = checkInputPattern(String.valueOf(math), 3);
		if (!value)
			return;
		updateStudent.setKor(math);
		//
		updateStudent.calTotal();
		updateStudent.calAvr();
		updateStudent.calGrade();
		// database update
		int returnUpdateValue = dbConn.update(updateStudent);
		if (returnUpdateValue == -1) {
			System.out.println("¼öÁ¤ ºÒ°¡ " + returnUpdateValue);
			return;
		} else {
			System.out.println("¼öÁ¤ ¿Ï·á");
		}
		// article table data
		dbConn.close();
	}

	// studentDelete
	private static void studentDelete() {
		try {
			// »èÁ¦ÇÒ ÇÐ»ý¹øÈ£ ÀÔ·Â
			System.out.print("ÇÐ»ý¹øÈ£¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä. ¿¹)10101 >> ");
			String no = sc.nextLine();
			// ¹®ÀÚ¿­ÆÐÅÏ°Ë»ö
			boolean value = checkInputPattern(no, 1);
			if (!value) {
				return;
			}
			// delete
			// insert database
			DBConnection dbConn = new DBConnection();
			// database connection
			dbConn.connect();
			// article table data
			int deleteReturnValue = dbConn.delete(no);
			if (deleteReturnValue == -1) {
				System.out.println("»èÁ¦ ½ÇÆÐ" + deleteReturnValue);
			} else if (deleteReturnValue == 0) {
				System.out.println("»èÁ¦ÇÒ ¹øÈ£°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù." + deleteReturnValue);
			} else {
				System.out.println("»èÁ¦ ¼º°ø" + deleteReturnValue);
			}
			dbConn.close();
		} catch (Exception e) {
			System.out.println("µ¥ÀÌÅ¸ º£ÀÌ½º ÀÔ·Â ¿¡·¯" + e.getMessage());
		} finally {
			sc.nextLine();
		}
	}

	// studentSearch
	private static void studentSearch() {
		List<Student> list = new ArrayList<Student>();
		try {
			// buffer
			System.out.print("°Ë»öÇÒ ÀÌ¸§ ÀÔ·Â >> ");
			String name = sc.nextLine();
			// pattern
			boolean value = checkInputPattern(name, 2);
			if (!value) {
				return;
			}
			// search
			// insert database
			DBConnection dbConn = new DBConnection();
			// database connection
			dbConn.connect();
			// article table data
			list = dbConn.selectSearch(name, 2);
			if (list.size() <= 0) {
				System.out.println("°Ë»öÇÑ ÇÐ»ýÁ¤º¸°¡ ¾ø½À´Ï´Ù." + list.size());
				return;
			}
			for (Student student : list) {
				System.out.println(student);
			}
			// article table data
			dbConn.close();
		} catch (Exception e) {
			System.out.println("µ¥ÀÌÅ¸ º£ÀÌ½º ÀÔ·Â ¿¡·¯" + e.getMessage());
		}
	}

	// studentPrint
	private static void studentPrint() {
		List<Student> list = new ArrayList<Student>();
		try {
			// insert database
			DBConnection dbConn = new DBConnection();
			// database connection
			dbConn.connect();
			list = dbConn.select();
			if (list.size() <= 0) {
				System.out.println("º¸¿©ÁÙ ¸®½ºÆ®°¡ ¾ø½À´Ï´Ù." + list.size());
				return;
			}
			for (Student student : list) {
				System.out.println(student);
			}
			// article table data
			dbConn.close();
		} catch (Exception e) {
			System.out.println("µ¥ÀÌÅ¸ º£ÀÌ½º º¸¿©ÁÖ±â ¿¡·¯" + e.getMessage());
		}
	}

	// studentSort: no, name, total
	private static void studentSort() {
		List<Student> list = new ArrayList<Student>();
		try {
			// insert database
			DBConnection dbConn = new DBConnection();
			// database connection
			dbConn.connect();
			// choice
			System.out.print("Á¤·Ä ¹æ½Ä ¼±ÅÃ 1.¹øÈ£ 2.ÀÌ¸§ 3.ÇÕ°è >> ");
			int type = sc.nextInt();
			// pattern
			boolean value = checkInputPattern(String.valueOf(type), 4);
			if (!value)
				return;

			list = dbConn.selectOrderBy(type);

			if (list.size() <= 0) {
				System.out.println("º¸¿©ÁÙ ¸®½ºÆ®°¡ ¾ø½À´Ï´Ù." + list.size());
				return;
			}
			for (Student student : list) {
				System.out.println(student);
			}
			// article table data
			dbConn.close();
		} catch (Exception e) {
			System.out.println("µ¥ÀÌÅ¸ º£ÀÌ½º Á¤·Ä ¿¡·¯" + e.getMessage());
		}
	}

	// studentStats
	private static void studentStats() {
		List<Student> list = new ArrayList<Student>();
		try {
			System.out.println("1.ÃÖ°íÁ¡¼ö 2.ÃÖÀúÁ¡¼ö >> ");
			int type = sc.nextInt();
			// pattern
			boolean value = checkInputPattern(String.valueOf(type), 5);
			if (!value)
				return;
			// search
			// insert database
			DBConnection dbConn = new DBConnection();
			// database connection
			dbConn.connect();
			// article table data
			list = dbConn.selectMaxMin(type);
			if (list.size() <= 0) {
				System.out.println("°Ë»öÇÑ ÇÐ»ýÁ¤º¸°¡ ¾ø½À´Ï´Ù." + list.size());
				return;
			}
			for (Student student : list) {
				System.out.println(student);
			}
			// article table data
			dbConn.close();
		} catch (Exception e) {
			System.out.println("µ¥ÀÌÅ¸ º£ÀÌ½º Åë°è ¿¡·¯" + e.getMessage());
		}
	}

	// pattern
	private static boolean checkInputPattern(String data, int patternType) {
		String pattern = null;
		boolean regex = false;
		String message = null;
		switch (patternType) {
		case NUM:
			pattern = "^[1-3][0-1][0-9][0-3][0-9]$";
			message = "¹üÀ§¸¦ ¹þ¾î³­ ¼ýÀÚÀÔ´Ï´Ù.";
			break;
		case NAME:
			pattern = "^[°¡-ÆR]{2,4}$";
			message = "ÀÌ¸§À» ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.";
			break;
		case SCORE:
			pattern = "^[0-9]{1,3}$";
			message = "Á¡¼ö¸¦ ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.";
			break;
		case THREE:
			pattern = "^[1-3]$";
			message = "´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.";
			break;
		case TWO:
			pattern = "^[1-2]$";
			message = "´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.";
			break;
		}
		regex = Pattern.matches(pattern, data);
		if (patternType == 3) {
			if (!regex || Integer.parseInt(data) < 0 || Integer.parseInt(data) > 100) {
				System.out.println(message);
				return false;
			}
		} else {
			if (!regex) {
				System.out.println(message);
				return false;
			}
		}
		return regex;
	}
}