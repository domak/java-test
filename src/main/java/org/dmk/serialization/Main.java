/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dmk.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author domak
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		Address kremlin = new Address("1", "kremlin", "Moscou");
		Personn kroutchev = new Personn("Kroutchev", kremlin);
		Personn staline = new Personn("Staline", kremlin);
		System.out.println("same adress before serialization:" + (staline.getAddress() == kroutchev.getAddress()));

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

		// serialize object
		objectOutputStream.writeObject(kroutchev);
		objectOutputStream.writeObject(staline);

		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

		Personn deserializedKroutchev = (Personn) objectInputStream.readObject();
		Personn deserializedStaline = (Personn) objectInputStream.readObject();

		System.out.println("same personn:" + (deserializedKroutchev == kroutchev));
		System.out.println("same adress:" + (deserializedKroutchev.getAddress() == kroutchev.getAddress()));
		System.out.println("same deseralized adress:"
				+ (deserializedKroutchev.getAddress() == deserializedStaline.getAddress()));

		// other deserialization
		ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		ObjectInputStream objectInputStream2 = new ObjectInputStream(byteArrayInputStream2);

		Personn deserializedKroutchev2 = (Personn) objectInputStream2.readObject();
		System.out.println("same deseralized personn with two deserializations:"
				+ (deserializedKroutchev == deserializedKroutchev2));
		System.out.println("same deseralized adress with two deserializations:"
				+ (deserializedKroutchev.getAddress() == deserializedKroutchev2.getAddress()));

	}

}
