package de.jhdp.model.users;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="USER_ROLE_DEF")
public class UserRoleAttributeDefinition implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String attributeName;
	
	@ManyToOne
	private UserRoleDefinition role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public UserRoleDefinition getRole() {
		return role;
	}

	public void setRole(UserRoleDefinition role) {
		this.role = role;
	}
	
}
