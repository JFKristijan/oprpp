package hr.fer.oprpp1.hw04.db;

/**
 * @author Fran Kristijan Jelenčić
 *Class that represents a StudentRecord
 *Contains information about jmbag, first name, last name and grade
 */
public class StudentRecord {
	private String jmbag;
	private String lastName;
	private String firstName;
	private int finalGrade;
	public StudentRecord(String jmbag, String firstName, String lastName, int finalGrade) {
		this.jmbag=jmbag;
		this.lastName=lastName;
		this.firstName=firstName;
		this.finalGrade=finalGrade;
	}
	
	
	/**Getter for jmbag
	 * @return String jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}
	/**Getter for last name
	 * @return String lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**Getter for first name
	 * @return String first name
	 */
	public String getFirstName() {
		return firstName;
	}
	/**Getter for final grade
	 * @return int final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
}
