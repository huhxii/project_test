package mire;

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
				System.out.println("종료.");
				break;
			default:
				System.out.println("1~8번 값을 입력해주세요.");
				break;
			}// end of switch-case
		} // end of while
		System.out.println("종료");

	}

	// displayMenu
	private static int displayMenu() {
		int num = -1;
		try {
			System.out.println(
					"┌──────────┬──────────┬──────────┬──────────┬──────────┬──────────┬──────────┬──────────┐");
			System.out.println("|  1. 입력  |  2. 수정  |  3. 삭제  |  4. 검색  |  5. 출력  |  6. 정렬  |  7. 통계  |  8. 통계  |");
			System.out.println(
					"└──────────┴──────────┴──────────┴──────────┴──────────┴──────────┴──────────┴──────────┘");
			System.out.print("입력 >> ");
			num = sc.nextInt();
			// 정수패턴검색
			String pattern = "^[1-8]$"; // 숫자만
			String data = null;
			boolean regex = Pattern.matches(pattern, String.valueOf(num));
			if (regex == false) {
				data = "범위를 벗어난 숫자입니다.";
				System.out.println(num + "은/는 " + data);
			}
		} catch (InputMismatchException e) {
			System.out.println("숫자를 입력하지 않았습니다.");
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
			System.out.print("학생번호를 입력해주세요. 예)10101 >> ");
			no = sc.nextLine();
			// 문자열패턴검색
			boolean value = checkInputPattern(no, NUM);
			pattern = "^[1-3][0-9]+{1,2}[0-3][0-9]$";
			value = Pattern.matches(pattern, String.valueOf(no));
			if (!value) {
				System.out.println(no + "은/는 범위를 벗어난 숫자입니다.");
			} else {
				System.out.println("입력 완료");
			}
			// name
			System.out.print("이름을 입력해주세요 >> ");
			String name = sc.nextLine();
			// 문자열패턴검색
			value = checkInputPattern(name, NAME);
			// score
			System.out.print("국어점수 입력 >> ");
			int kor = sc.nextInt();
			// kor pattern
			value = checkInputPattern(String.valueOf(kor), SCORE);
			if (!value)
				return;
			System.out.print("영어점수 입력 >> ");
			int eng = sc.nextInt();
			// eng pattern
			value = checkInputPattern(String.valueOf(eng), SCORE);
			if (!value)
				return;
			System.out.print("수학점수 입력 >> ");
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
				System.out.println("삽입 실패");
			} else {
				System.out.println("삽입 성공");
			}
			dbConn.close();
		} catch (InputMismatchException e) {
			System.out.println("입력값이 맞지 않습니다.");
			return;
		} finally {
			sc.nextLine();
		}
	}

	// studentUpdate
	private static void studentUpdate() {
		List<Student> list = new ArrayList<Student>();
		System.out.print("검색할 학생번호 입력 >> ");
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
			System.out.println("검색한 학생정보가 없습니다." + list.size());
			return;
		}
		for (Student student : list) {
			System.out.println(student);
		}
		// search list
		Student updateStudent = list.get(0);
		System.out.print("국어점수 " + updateStudent.getKor() + ">> ");
		int kor = sc.nextInt();
		value = checkInputPattern(String.valueOf(kor), 3);
		if (!value)
			return;
		updateStudent.setKor(kor);
		System.out.print("영어점수 " + updateStudent.getEng() + ">> ");
		int eng = sc.nextInt();
		value = checkInputPattern(String.valueOf(eng), 3);
		if (!value)
			return;
		updateStudent.setKor(eng);
		System.out.print("수학점수 " + updateStudent.getMath() + ">> ");
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
			System.out.println("수정 불가 " + returnUpdateValue);
			return;
		} else {
			System.out.println("수정 완료");
		}
		// article table data
		dbConn.close();
	}

	// studentDelete
	private static void studentDelete() {
		try {
			// 삭제할 학생번호 입력
			System.out.print("학생번호를 입력해주세요. 예)10101 >> ");
			String no = sc.nextLine();
			// 문자열패턴검색
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
				System.out.println("삭제 실패" + deleteReturnValue);
			} else if (deleteReturnValue == 0) {
				System.out.println("삭제할 번호가 존재하지 않습니다." + deleteReturnValue);
			} else {
				System.out.println("삭제 성공" + deleteReturnValue);
			}
			dbConn.close();
		} catch (Exception e) {
			System.out.println("데이타 베이스 입력 에러" + e.getMessage());
		} finally {
			sc.nextLine();
		}
	}

	// studentSearch
	private static void studentSearch() {
		List<Student> list = new ArrayList<Student>();
		try {
			// buffer
			System.out.print("검색할 이름 입력 >> ");
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
				System.out.println("검색한 학생정보가 없습니다." + list.size());
				return;
			}
			for (Student student : list) {
				System.out.println(student);
			}
			// article table data
			dbConn.close();
		} catch (Exception e) {
			System.out.println("데이타 베이스 입력 에러" + e.getMessage());
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
				System.out.println("보여줄 리스트가 없습니다." + list.size());
				return;
			}
			for (Student student : list) {
				System.out.println(student);
			}
			// article table data
			dbConn.close();
		} catch (Exception e) {
			System.out.println("데이타 베이스 보여주기 에러" + e.getMessage());
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
			System.out.print("정렬 방식 선택 1.번호 2.이름 3.합계 >> ");
			int type = sc.nextInt();
			// pattern
			boolean value = checkInputPattern(String.valueOf(type), 4);
			if (!value)
				return;

			list = dbConn.selectOrderBy(type);

			if (list.size() <= 0) {
				System.out.println("보여줄 리스트가 없습니다." + list.size());
				return;
			}
			for (Student student : list) {
				System.out.println(student);
			}
			// article table data
			dbConn.close();
		} catch (Exception e) {
			System.out.println("데이타 베이스 정렬 에러" + e.getMessage());
		}
	}

	// studentStats
	private static void studentStats() {
		List<Student> list = new ArrayList<Student>();
		try {
			System.out.println("1.최고점수 2.최저점수 >> ");
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
				System.out.println("검색한 학생정보가 없습니다." + list.size());
				return;
			}
			for (Student student : list) {
				System.out.println(student);
			}
			// article table data
			dbConn.close();
		} catch (Exception e) {
			System.out.println("데이타 베이스 통계 에러" + e.getMessage());
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
			message = "범위를 벗어난 숫자입니다.";
			break;
		case NAME:
			pattern = "^[가-힣]{2,4}$";
			message = "이름을 다시 입력해주세요.";
			break;
		case SCORE:
			pattern = "^[0-9]{1,3}$";
			message = "점수를 다시 입력해주세요.";
			break;
		case THREE:
			pattern = "^[1-3]$";
			message = "다시 입력해주세요.";
			break;
		case TWO:
			pattern = "^[1-2]$";
			message = "다시 입력해주세요.";
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
