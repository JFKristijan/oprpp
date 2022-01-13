package hr.fer.oprpp1.custom.collections;

import java.util.Iterator;

public class Glavni {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// create collection:
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
        // fill data:
        examMarks.put("Ivana", 2);
        //examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5); // overwrites old grade for Ivana
        // query collection:
        Integer kristinaGrade = examMarks.get("Kristina");
        System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5
        // What is collection's size? Must be four!
        System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4
        examMarks.put("Dezinjo", 5);
        Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
        while(iter.hasNext()) {
        SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
        System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
        iter.remove();
        }
        System.out.printf("Veličina: %d%n", examMarks.size());
	}

}
