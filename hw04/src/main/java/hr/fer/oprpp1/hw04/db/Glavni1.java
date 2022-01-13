package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Glavni1 {
	public static void main(String[] args) throws IOException {

		List<String> lines = Files.readAllLines(
				Paths.get("./src/main/resources/database.txt"),
				StandardCharsets.UTF_8
				);
		StudentDatabase db = new StudentDatabase(lines.toArray(new String[lines.size()]));
//		QueryParser parser = new QueryParser("jmbag!=\"123321321\" and firstName LIKE\"*\"");
//		System.out.println(parser.isDirectQuery());
//		
//		db.filter(new QueryFilter(parser.getQuery())).stream().forEach(System.out::println);;
		Scanner sc = new Scanner(System.in);
		String query="";
		while(true) {
			System.out.printf("> ");
			query = sc.nextLine();
			if(query.startsWith("exit"))break;
			if(!query.startsWith("query")) {
				System.out.println("Where is query?");
				continue;
			}
			
			List<StudentRecord> records;
			List<String> output;
			try {
			QueryParser parser = new QueryParser(query.substring("query".length()));
			if(parser.isDirectQuery()) {
				StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
				records = new ArrayList<StudentRecord>(1);
				records.add(r);
				System.out.println("Using index for record retrieval.");
			}else {
				records = db.filter(new QueryFilter(parser.getQuery()));
			}
			output = RecordFormatter.format(records);
			output.forEach(System.out::println);
			}catch(Exception e) {
				System.out.println("An error occured during "+e);
			}
		}
		sc.close();
		System.out.println("Goodbye!");
		
		
		
		
		
		
//		StudentDatabase sd = new StudentDatabase(new String[] {"123,fran,Bossnjak,5","1263,marko,cupic,5"});
//		//System.out.println(sd.forJMBAG("123"));
//		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
//		System.out.println("isDirectQuery(): " + qp1.isDirectQuery()); // true
//		System.out.println("jmbag was: " + qp1.getQueriedJMBAG()); // 0123456789
//		System.out.println("size: " + qp1.getQuery().size()); // 1
//		
//		
//		
//		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
//		System.out.println("isDirectQuery(): " + qp2.isDirectQuery()); // false
//		//System.out.println(qp2.getQueriedJMBAG()); // would throw!
//		System.out.println("size: " + qp2.getQuery().size()); // 2
//		ConditionalExpression expr = new ConditionalExpression(
//		FieldValueGetters.LAST_NAME,
//		"Bos*",
//		ComparisonOperators.LIKE
//		);
//		StudentRecord record = sd.filter(e->true).iterator().next();
//		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
//		expr.getFieldGetter().get(record), // returns lastName from given record
//		expr.getStringLiteral() // returns "Bos*"
//		);
//		System.out.println(recordSatisfies);
//		
	}

}
