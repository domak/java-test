package org.domak.net.sample;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProcessorArg implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> attributes = new HashMap<String, Object>();

	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}

	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

}
