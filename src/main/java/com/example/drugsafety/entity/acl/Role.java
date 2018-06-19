package com.example.drugsafety.entity.acl;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {
	public static final String ADMIN = "ADMIN";
	public static final String DEVELOPER = "DEVELOPER";
	public static final String CASE_DOCTOR = "CASE_DOCTOR";
	public static final String CASE_USER = "CASE_USER";

	private long id;
	private String name;
	private Set<User> users;
	private String slug;

	@Id
	@GeneratedValue()
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "role_name", nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(mappedBy = "roles")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Column(name = "slug", nullable = true)
	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

}
