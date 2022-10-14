package mire;

public class Student implements Comparable<Student> {
	// field
	private final int SUJECT_NO = 3;
	private String no;
	private String name;
	private int kor;
	private int eng;
	private int math;
	private int total;
	private double avr;
	private String grade;
	private int rank;

	// constructor
	public Student(String no, String name, int kor, int eng, int math) {
		this(no, name, kor, eng, math, 0, 0.0, null, 0);
	}

	public Student(String no, String name, int kor, int eng, int math, int total, double avr, String grade, int rank) {
		super();
		this.no = no;
		this.name = name;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
		this.total = total;
		this.avr = avr;
		this.grade = grade;
		this.rank = rank;
	}

	// method(setter, getter)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKor() {
		return kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getMath() {
		return math;
	}

	public void setMath(int math) {
		this.math = math;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public double getAvr() {
		return avr;
	}

	public void setAvr(double avr) {
		this.avr = avr;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	// method(calculate)
	public void calTotal() {
		this.total = this.kor + this.eng + this.math;
	}

	public void calAvr() {
		this.avr = (double) this.total / (double) SUJECT_NO;
	}

	public void calGrade() {
		switch ((int) (this.avr) / 10) {
		case 10:
		case 9:
			this.grade = "A";
			break;
		case 8:
		case 7:
			this.grade = "B";
			break;
		case 6:
		case 5:
			this.grade = "C";
			break;
		case 4:
		case 3:
			this.grade = "D";
			break;
		default:
			this.grade = "F";
			break;
		}
	}

	// method(override: hashCode, equals, compareTo)
	@Override
	public int hashCode() {
		return this.no.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Student))
			return false;
		return this.no.equals(((Student) obj).no);
	}

	@Override
	public int compareTo(Student student) {
		return this.no.compareToIgnoreCase(student.no);
	}

	// method(override: toString)
	@Override
	public String toString() {
		return no + "\t" + name + "\t" + kor + "\t" + eng + "\t" + math + "\t" + total + "\t" + avr + "\t" + grade
				+ "\t" + rank;
	}
}
