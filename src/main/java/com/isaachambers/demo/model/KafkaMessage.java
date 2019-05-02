package com.isaachambers.demo.model;

public class KafkaMessage {
	private MessageType messageType;
	private String messageDetails;
	private String resourceIdentifier;

	public KafkaMessage(MessageType messageType, String messageDetails, String resourceIdentifier) {
		super();
		this.messageType = messageType;
		this.messageDetails = messageDetails;
		this.resourceIdentifier = resourceIdentifier;
	}

	public KafkaMessage() {
		
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public String getMessageDetails() {
		return messageDetails;
	}

	public void setMessageDetails(String messageDetails) {
		this.messageDetails = messageDetails;
	}

	public String getResourceIdentifier() {
		return resourceIdentifier;
	}

	public void setResourceIdentifier(String resourceIdentifier) {
		this.resourceIdentifier = resourceIdentifier;
	}

	@Override
	public String toString() {
		return "KafkaMessage [messageType=" + messageType + ", messageDetails=" + messageDetails
				+ ", resourceIdentifier=" + resourceIdentifier + "]";
	}

}
