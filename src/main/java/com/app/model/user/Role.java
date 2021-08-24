package com.app.model.user;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum Role {
	ROLE_USER(new HashSet<>(Arrays.asList(

	))), ROLE_ADMIN(new HashSet<>(Arrays.asList(

	)));

	private Set<Authority> permissions;

	private Role(Set<Authority> permissions) {
		this.permissions = permissions;
	}

	public Set<Authority> getPermissions() {
		return permissions;
	}
}
