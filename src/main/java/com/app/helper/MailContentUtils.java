package com.app.helper;

import com.app.config.mail.MailConfig.MailAction;

public class MailContentUtils {
	public static String createContent(MailAction action, String code) {
		String content = "<p>Hi there,<br>You just request us to " + action.name().replace("_", " ")
				+ "?!<br>If you do that, you will need validation code below:<br>\r\n"
				+ "      Your validation code is <b style =\"Color: red;\">" + code
				+ "</b><br><br>Thanks for using our services! <br><br>Have a nice day.\r\n" + "    </p>";
		return content;

	}
}
