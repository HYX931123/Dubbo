package org.springframe.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 序列化工具类
 * @author 何壹轩
 * @version 2016-06-28
 */
@SuppressWarnings("unchecked")
public class SerializeUtil implements Serializable {

	private static final long serialVersionUID = -9037140198904313568L;

	/**
	 * 序列化
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] serialize(Object value) {
		if (value == null) {
			throw new NullPointerException("Can't serialize null");
		}
		byte[] rv = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream os = null;
		try {
			bos = new ByteArrayOutputStream();
			os = new ObjectOutputStream(bos);
			os.writeObject(value);
			os.close();
			bos.close();
			rv = bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("serialize error");
		} finally {
			close(os);
			close(bos);
		}
		return rv;
	}

	/**
	 * 反序列话
	 * 
	 * @param in
	 * @return
	 */
	public static Object deserialize(byte[] in) {
		return deserialize(in, Object.class);
	}

	public static <T> T deserialize(byte[] in, Class<T>... requiredType) {
		Object rv = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream is = null;
		try {
			if (in != null) {
				bis = new ByteArrayInputStream(in);
				is = new ObjectInputStream(bis);
				rv = is.readObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("deserialize error");
		} finally {
			close(is);
			close(bis);
		}
		return (T) rv;
	}

	/**
	 * 关闭流
	 * 
	 * @param closeable
	 */
	private static void close(Closeable closeable) {
		if (closeable != null)
			try {
				closeable.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("close stream error");
			}
	}
}
