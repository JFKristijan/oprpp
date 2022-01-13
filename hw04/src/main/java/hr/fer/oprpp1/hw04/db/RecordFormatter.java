package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public  class RecordFormatter {
	
	
	/**Takes given list of student records and turns it into a list of strings that can be printed out to the user
	 * @param records the records to enter into the table
	 * @return list of strings that represent the table
	 */
	public static List<String> format(List<StudentRecord> records){
		OptionalInt lenName = records.stream().map(StudentRecord::getFirstName).mapToInt(String::length).max();
		OptionalInt lenSurname = records.stream().map(StudentRecord::getLastName).mapToInt(String::length).max();
		OptionalInt lenJmbag = records.stream().map(StudentRecord::getJmbag).mapToInt(String::length).max();
		//cuvat neki int dok se dodaju stvari????????
		List<String> retval = new ArrayList<String>(records.size());
		if(!lenName.isPresent()||!lenSurname.isPresent()) {
			retval.add("Records selected: 0");
			return retval;
		}
		int lenNamePad =lenName.getAsInt();
		int lenSurnamePad = lenSurname.getAsInt();
		String p = "+============+"+"=".repeat(lenSurnamePad+2)+"+"+"=".repeat(lenNamePad+2)+"+===+";
		retval.add(p);
		for(int i = 0 ; i < records.size() ; i++) {
			StringBuilder sb = new StringBuilder();
			StudentRecord record = records.get(i);
			sb.append("| ")
			.append(record.getJmbag())
			.append(" | ")
			.append(String.format("%1$-"+lenSurnamePad+ "s", record.getLastName()))
			.append(" | ")
			.append(String.format("%1$-"+lenNamePad+ "s", record.getFirstName()))
			.append(" | ")
			.append(record.getFinalGrade())
			.append(" |");
			retval.add(sb.toString());
		}
		retval.add(p);
		
		
		return retval;
	}
}
