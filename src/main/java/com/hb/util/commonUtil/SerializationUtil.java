package com.hb.util.commonUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.SignedObject;
import java.util.Collection;

/**
 * 得到要复制对象的类型
 * @author 338342
 *
 */
public final class SerializationUtil {
	private SerializationUtil() {
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T clone(final T object)
			throws ClassNotFoundException, IOException {
		final ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
		serialization(output, object);
		final ByteArrayInputStream input = new ByteArrayInputStream(
				output.toByteArray());
		return (T) deserialization(input);
	}

	public static Serializable deserialization(final InputStream inputStream)
			throws ClassNotFoundException, IOException {
		final Serializable object;
		final ObjectInputStream input = new ObjectInputStream(inputStream);
		object = (Serializable) input.readObject();
		return object;
	}

	public static <T extends Serializable> void serialization(
			final OutputStream outputStream, final T object) throws IOException {
		ObjectOutputStream output = new ObjectOutputStream(outputStream);
		output.writeObject(object);
	}

	public static Serializable[] deserializations(final InputStream inputStream)
			throws IOException, ClassNotFoundException {
		return (Serializable[]) deserialization(inputStream);
	}

	public static void serializations(final OutputStream outputStream,
			final Serializable object) throws IOException {
		serialization(outputStream, object);
	}

	public static void serializations(final OutputStream outputStream,
			final Collection<Serializable> objects) throws IOException {
		serialization(outputStream,
				objects.toArray(new Serializable[objects.size()]));
	}

	public static SignedObject genSignedObject(final Serializable object,
			KeyPairGenerator generator) throws InvalidKeyException,
			SignatureException, NoSuchAlgorithmException, IOException {
		return new SignedObject(object, generator.generateKeyPair()
				.getPrivate(), Signature.getInstance(generator.getAlgorithm()));
	}
}