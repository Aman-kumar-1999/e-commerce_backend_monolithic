package com.eqipped.helper;



public class EmailDetails {
	

		// Class data members
		private String recipient;
		private String msgBody;
		private String subject;
		private String attachment;
		
		
		
		@Override
		public String toString() {
			return "EmailDetails [recipient=" + recipient + ", msgBody=" + msgBody + ", subject=" + subject
					+ ", attachment=" + attachment + "]";
		}
		
		
		
		public EmailDetails() {
			super();
			// TODO Auto-generated constructor stub
		}



		public EmailDetails(String recipient, String msgBody, String subject, String attachment) {
			super();
			this.recipient = recipient;
			this.msgBody = msgBody;
			this.subject = subject;
			this.attachment = attachment;
		}
		public String getRecipient() {
			return recipient;
		}
		public String getMsgBody() {
			return msgBody;
		}
		public String getSubject() {
			return subject;
		}
		public String getAttachment() {
			return attachment;
		}
		public void setRecipient(String recipient) {
			this.recipient = recipient;
		}
		public void setMsgBody(String msgBody) {
			this.msgBody = msgBody;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public void setAttachment(String attachment) {
			this.attachment = attachment;
		}
		
		
	

}
