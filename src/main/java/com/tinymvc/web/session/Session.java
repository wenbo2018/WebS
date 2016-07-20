package com.tinymvc.web.session;

import java.util.Set;

public interface Session {

	String getId();

	<T> T getAttribute(String attributeName);

	Set<String> getAttributeNames();

	void setAttribute(String attributeName, Object attributeValue);

	void removeAttribute(String attributeName);
}