package com.app.config.mail;

public class MailConfig {
	public static final String HOST_NAME = "smtp.gmail.com";
	public static final String APP_EMAIL = "17130132@st.hcmuaf.edu.vn";
	public static final String APP_PASSWORD = "xwyp gfkx jvne wfin";
//	public static final String ANOTHER_APP_PASSWORD = "xwyp gfkx jvne wfin";
	public static final int SSL_PORT = 465;
	public static final int TSL_PORT = 587;

	public static final String SIGNATURE = "<br>--<br><h3 style=\"color: red;\">"
			+ "Nghia Le</h3> <br>Phone: (+84) 868 88 0758 <br>\r\n"
			+ "  Facebook: <a href=\"fb.com/le.nghia.5602\">fb.com/le.nghia.5602</a> <br>\r\n"
			+ "  Gmail: <a href=\"17130132@st.hcmuaf.edu.com\">17130132@st.hcmuaf.edu.com</a>";

	public enum MailAction {
		RECOVERY_YOUR_PASSWORD("VALIDATION CODE TO RECOVER YOUR PASSWORD"),
		REGISTER_NEW_ACCOUNT("VALIDATION CODE TO REGISTER USER");

		public String subject;

		private MailAction(String subject) {
			this.subject = subject;
		};

		@Override
		public String toString() {
			return this.subject;
		}
	}
}