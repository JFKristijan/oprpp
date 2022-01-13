package hr.fer.oprpp1.hw05.crypto;

import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static hr.fer.oprpp1.hw05.crypto.Util.hextobyte;


/**Class used for encrypting/decrypting files using AES CBC, and checking SHA-256 digests of files.
 * It uses command line arguments to determine which operation it does and also which files to check
 * @author Fran Kristijan Jelenčić
 *
 */
public class Crypto {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {

		if(args.length==0) {
			System.out.println("No arguments were given, cannot continue");
			System.exit(0);
		}
		
		MessageDigest md=null;
		Cipher cipher=null;
		Scanner sc = new Scanner(System.in);
		String digestGiven="";
		boolean encrypt=false;
		if(args[0].equals("checksha")) {
			
			System.out.print("Please provide expected sha-256 digest for "+args[1]+":\n> ");
			md =  MessageDigest.getInstance("SHA-256");
			digestGiven = sc.nextLine();
			
		}else {
			System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
			String keyText =sc.nextLine(); //"e52217e3ee213ef1ffdee3a192e2ac7e" ;
			
			System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
			String ivText = sc.nextLine();//"000102030405060708090a0b0c0d0e0f" ;
			
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
			
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			encrypt=args[0].equals("encrypt");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		}
		sc.close();
		
		Path input = Paths.get(args[1]);
		Path output = args.length>2?Paths.get(args[2]):null;
		if(!args[0].equals("checksha")&&output==null) {
			System.out.println("Too few arguments given. Output file not specified.\nProgram will exit.");
			System.exit(1);
		}
		try {
			InputStream is = Files.newInputStream(input);
			OutputStream os = output!=null?Files.newOutputStream(output):null;


			int read;
			int write;
			byte[] buffer = new byte[4096];
			byte[] outputBuffer = new byte[4096];
			while((read = is.read(buffer))!= -1) {

				if(md!=null) {
					md.update(buffer, 0, read);
				}else {
					write = cipher.update(buffer, 0, read, outputBuffer, 0);
					os.write(outputBuffer,0,write);
				}

			}
			

			if(md!=null) {
				byte[] digest = md.digest();
				String calculatedDigest = Util.bytetohex(digest);

				if(calculatedDigest.equals(digestGiven)) {

					System.out.println("Digesting completed. Digest of "+args[1]+" matches expected digest.");

				}else {

					System.out.println("Digesting completed. Digest of "+args[1]+" does not match the "
							+ "expected digest.\nDigest was: "+calculatedDigest);

				}
			}else {

				write = cipher.doFinal(outputBuffer, 0);
				os.write(outputBuffer,0,write);

				System.out.println((encrypt?"Encryption":"Decryption")+" completed. Generated file "+args[2]+" based on file "+args[1]+".");
			}
			
			is.close();
			if(os!=null) {
				os.close();
			}

		}catch(IOException | ShortBufferException  | IllegalBlockSizeException  | BadPaddingException e) {
			System.out.println("An error occurred during execution...");
			System.out.println(e);
			System.exit(1);
		}

	}

}
