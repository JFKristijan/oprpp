package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**Class that represents a StudentDatabase
 * @author Fran Kristijan Jelenčić
 *
 */
public class StudentDatabase {
	private List<StudentRecord> listStudents;
	private Map<String,StudentRecord> mapStudents;
	
	/**Constructor for StudentDatabase, initalises a databes from given input
	 * @param input to parse and add students into this database
	 */
	public StudentDatabase(String[] input) {
		listStudents = new ArrayList<StudentRecord>(input.length);
		mapStudents = new HashMap<String, StudentRecord>(input.length);
		
		for(int i = 0 ; i < input.length ; i++) {
			
			String[] entry = input[i].replaceAll("\\s+"," ").split(" ");
			
			if(mapStudents.containsKey(entry[0])) {
				System.out.println("Duplicate JMBAG \""+entry[0]+"\" found in given file. Ensure there are no duplicates.");
				System.exit(-1);
			}
			
			if(entry.length==5) {
				String[] temp = new String[4];
				temp[0]=entry[0];
				temp[1]=entry[1]+" "+entry[2];
				temp[2]=entry[3];
				temp[3]=entry[4];
				entry=temp;
			}
			
			StudentRecord student;
			
			try {
				
				int ocjena = Integer.parseInt(entry[3]);
				if(ocjena<1||ocjena>5)throw new NumberFormatException();
				student = new StudentRecord(entry[0],entry[2],entry[1],ocjena);
				mapStudents.put(entry[0], student);
				listStudents.add(student);
				
			}catch(NumberFormatException e) {
				System.out.println("Error parsing grade for entry"+input[i]);
				System.exit(-1);
			}
		}
	}
	
	/**Gets a StudentRecord from given jmbag
	 * @param jmbag the jmbag to get the record from
	 * @return StudentRecord with given jmbag, null if jmbag is not in database
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return mapStudents.get(jmbag);
	}
	
	/**Method that returns a List of StudentRecords that satisfy given filter
	 * @param filter that is used to filter StudentRecords
	 * @return List of filtered students
	 */
	public List<StudentRecord> filter(IFilter filter){
		return listStudents.stream().filter(filter::accepts).collect(Collectors.toList());
	}
	
	
	
	
	
}
