package com.app.model.user;

public enum Authority {
	USER_CREATE("user:create");

	private String permission;

	private Authority(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
}
