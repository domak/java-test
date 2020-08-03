package org.dmk.asynctosync;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 */
public class PriceEvent {
	public int eventId;
	private int requestId;
	private int productId;
	public float price;

	public PriceEvent(int eventId, int requestId, int productId, float price) {
		this.eventId = eventId;
		this.requestId = requestId;
		this.productId = productId;
		this.price = price;
	}

	public int getEventId() {
		return eventId;
	}

	public int getRequestId() {
		return requestId;
	}

	public int getProductId() {
		return productId;
	}

	public float getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this).toString();
	}
}
