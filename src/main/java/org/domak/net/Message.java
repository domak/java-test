package org.domak.net;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	// -------------------------------------------------------------------------

	private String serviceName;
	private String methodName;
	private Object[] args;

	public Message(String serviceName, String methodName, Object[] args) {
		this.serviceName = serviceName;
		this.methodName = methodName;
		this.args = args;
	}

	public String getServiceName() {
		return serviceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public Object[] getArgs() {
		return args;
	}

	@Override
	public String toString() {
		StringBuilder buff = new StringBuilder(serviceName).append(".").append(methodName).append("(");
		for (Object arg : args) {
			buff.append(arg).append(", ");
		}
		buff.setLength(buff.length() - 2); // remove last comma
		buff.append(")");
		return buff.toString();
	}

}
