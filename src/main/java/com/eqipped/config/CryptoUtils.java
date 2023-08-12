package com.eqipped.config;

/**
 * @author Wipro InfoTech Maneesh Baid
 * @version 0.1, 25/05/2011
 * @since 0.1
 */

// TODO: Auto-generated Javadoc
/**
 * The Class CryptoUtils.
 * This class provides the functionality to encrypt the password in the database.We are using one way encryption.We convert every input
 * password string into a unique 40-digit hex code  
 */
public class CryptoUtils {

	/**
	 * The main method.
	 * 
	 * @param arg
	 *            the arguments
	 */
	public static void main(String arg[]) {
		try {

			java.io.StreamTokenizer Input = new java.io.StreamTokenizer(
					System.in);

			
			Input.nextToken();
			String hash = byteArrayToHexString(CryptoUtils
					.computeHash(Input.sval));
		
			boolean ok = true;
			String inputHash = "";
			while (ok) {
			
				Input.nextToken();
				inputHash = byteArrayToHexString(CryptoUtils
						.computeHash(Input.sval));
				if (hash.equals(inputHash)) {
					ok = false;
				} else
					{}
			}
		} catch (Exception e) {
			
		}
	}

	/**
	 * Compute hash.
	 * This method generates a byte code corresponding to a string
	 * @param x
	 *            the x
	 * 
	 * @return the byte[]
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static byte[] computeHash(String x) throws Exception {
		java.security.MessageDigest d = null;
		d = java.security.MessageDigest.getInstance("SHA-1");
		d.reset();
		d.update(x.getBytes());
		return d.digest();
	}

	/**
	 * Byte array to hex string.
	 * This method converts the byte code returned by computeHash(String x) into a string
	 * @param b
	 *            the b
	 * 
	 * @return the string
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();
	}

}
