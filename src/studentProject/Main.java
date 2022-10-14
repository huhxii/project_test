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
				System.out.println("����.");
				break;
			default:
				System.out.println("1~8�� ���� �Է����ּ���.");
				break;
			}// end of switch-case
		} // end of while
		System.out.println("����");

	}

	// displayMenu
	private static int displayMenu() {
		int num = -1;
		try {
			System.out.println("����������������������������������������������������������������������������������������������������������������������������������������������������������������������������������");
			System.out.println("|  1. �Է�  |  2. ����  |  3. ����  |  4. �˻�  |  5. ���  |  6. ����  |  7. ���  |  8. ���  |");
			System.out.println("����������������������������������������������������������������������������������������������������������������������������������������������������������������������������������");
			System.out.print("�Է� >> ");
			num = sc.nextInt();
			// �������ϰ˻�
			String pattern = "^[1-8]$"; // ���ڸ�
			String data = null;
			boolean regex = Pattern.matches(pattern, String.valueOf(num));
			if (regex == false) {
				data = "������ ��� �����Դϴ�.";
				System.out.println(num + "��/�� " + data);
			}
		} catch (InputMismatchException e) {
			System.out.println("���ڸ� �Է����� �ʾҽ��ϴ�.");
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
			System.out.print("�л���ȣ�� �Է����ּ���. ��)10101 >> ");
			no = sc.nextLine();
			// ���ڿ����ϰ˻�
			boolean value = checkInputPattern(no, NUM);
			pattern = "^[1-3][0-9]+{1,2}[0-3][0-9]$";
			value = Pattern.matches(pattern, String.valueOf(no));
			if (!value) {
				System.out.println(no + "��/�� ������ ��� �����Դϴ�.");
			} else {
				System.out.println("�Է� �Ϸ�");
			}
			// name
			System.out.print("�̸��� �Է����ּ��� >> ");
			String name = sc.nextLine();
			// ���ڿ����ϰ˻�
			value = checkInputPattern(name, NAME);
			// score
			System.out.print("�������� �Է� >> ");
			int kor = sc.nextInt();
			// kor pattern
			value = checkInputPattern(String.valueOf(kor), SCORE);
			if (!value)
				return;
			System.out.print("�������� �Է� >> ");
			int eng = sc.nextInt();
			// eng pattern
			value = checkInputPattern(String.valueOf(eng), SCORE);
			if (!value)
				return;
			System.out.print("�������� �Է� >> ");
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
				System.out.println("���� ����");
			} else {
				System.out.println("���� ����");
			}
			dbConn.close();
		} catch (InputMismatchException e) {
			System.out.println("�Է°��� ���� �ʽ��ϴ�.");
			return;
		} finally {
			sc.nextLine();
		}
	}

	// studentUpdate
	private static void studentUpdate() {
		List<Student> list = new ArrayList<Student>();
		System.out.print("�˻��� �л���ȣ �Է� >> ");
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
			System.out.println("�˻��� �л������� �����ϴ�." + list.size());
			return;
		}
		for (Student student : list) {
			System.out.println(student);
		}
		// search list
		Student updateStudent = list.get(0);
		System.out.print("�������� " + updateStudent.getKor() + ">> ");
		int kor = sc.nextInt();
		value = checkInputPattern(String.valueOf(kor), 3);
		if (!value)
			return;
		updateStudent.setKor(kor);
		System.out.print("�������� " + updateStudent.getEng() + ">> ");
		int eng = sc.nextInt();
		value = checkInputPattern(String.valueOf(eng), 3);
		if (!value)
			return;
		updateStudent.setKor(eng);
		System.out.print("�������� " + updateStudent.getMath() + ">> ");
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
			System.out.println("���� �Ұ� " + returnUpdateValue);
			return;
		} else {
			System.out.println("���� �Ϸ�");
		}
		// article table data
		dbConn.close();
	}

	// studentDelete
	private static void studentDelete() {
		try {
			// ������ �л���ȣ �Է�
			System.out.print("�л���ȣ�� �Է����ּ���. ��)10101 >> ");
			String no = sc.nextLine();
			// ���ڿ����ϰ˻�
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
				System.out.println("���� ����" + deleteReturnValue);
			} else if (deleteReturnValue == 0) {
				System.out.println("������ ��ȣ�� �������� �ʽ��ϴ�." + deleteReturnValue);
			} else {
				System.out.println("���� ����" + deleteReturnValue);
			}
			dbConn.close();
		} catch (Exception e) {
			System.out.println("����Ÿ ���̽� �Է� ����" + e.getMessage());
		} finally {
			sc.nextLine();
		}
	}

	// studentSearch
	private static void studentSearch() {
		List<Student> list = new ArrayList<Student>();
		try {
			// buffer
			System.out.print("�˻��� �̸� �Է� >> ");
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
				System.out.println("�˻��� �л������� �����ϴ�." + list.size());
				return;
			}
			for (Student student : list) {
				System.out.println(student);
			}
			// article table data
			dbConn.close();
		} catch (Exception e) {
			System.out.println("����Ÿ ���̽� �Է� ����" + e.getMessage());
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
				System.out.println("������ ����Ʈ�� �����ϴ�." + list.size());
				return;
			}
			for (Student student : list) {
				System.out.println(student);
			}
			// article table data
			dbConn.close();
		} catch (Exception e) {
			System.out.println("����Ÿ ���̽� �����ֱ� ����" + e.getMessage());
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
			System.out.print("���� ��� ���� 1.��ȣ 2.�̸� 3.�հ� >> ");
			int type = sc.nextInt();
			// pattern
			boolean value = checkInputPattern(String.valueOf(type), 4);
			if (!value)
				return;

			list = dbConn.selectOrderBy(type);

			if (list.size() <= 0) {
				System.out.println("������ ����Ʈ�� �����ϴ�." + list.size());
				return;
			}
			for (Student student : list) {
				System.out.println(student);
			}
			// article table data
			dbConn.close();
		} catch (Exception e) {
			System.out.println("����Ÿ ���̽� ���� ����" + e.getMessage());
		}
	}

	// studentStats
	private static void studentStats() {
		List<Student> list = new ArrayList<Student>();
		try {
			System.out.println("1.�ְ����� 2.�������� >> ");
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
				System.out.println("�˻��� �л������� �����ϴ�." + list.size());
				return;
			}
			for (Student student : list) {
				System.out.println(student);
			}
			// article table data
			dbConn.close();
		} catch (Exception e) {
			System.out.println("����Ÿ ���̽� ��� ����" + e.getMessage());
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
			message = "������ ��� �����Դϴ�.";
			break;
		case NAME:
			pattern = "^[��-�R]{2,4}$";
			message = "�̸��� �ٽ� �Է����ּ���.";
			break;
		case SCORE:
			pattern = "^[0-9]{1,3}$";
			message = "������ �ٽ� �Է����ּ���.";
			break;
		case THREE:
			pattern = "^[1-3]$";
			message = "�ٽ� �Է����ּ���.";
			break;
		case TWO:
			pattern = "^[1-2]$";
			message = "�ٽ� �Է����ּ���.";
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