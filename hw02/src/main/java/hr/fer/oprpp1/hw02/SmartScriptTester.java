package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class SmartScriptTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=1;i<11;i++) {
		String docBody = "";
		try {
			docBody = new String(
					Files.readAllBytes(Paths.get("src/test/resources/primjer"+i+".txt")),
					StandardCharsets.UTF_8
					);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		boolean same = document.equals(document2); // ==> "same" must be true
		//System.out.println(docBody+"\n");
		//System.out.println(originalDocumentBody+"\n");
		//System.out.println(document2+"\n");
		System.out.println(i+". "+same);
		}catch(SmartScriptParserException e) {
			System.out.println(i+". "+"Exception "+e.toString());
		}

	}
	}
}
