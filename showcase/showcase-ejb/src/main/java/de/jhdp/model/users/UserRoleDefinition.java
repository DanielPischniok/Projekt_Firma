package de.jhdp.model.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ROLE_DEFINITION")
public class UserRoleDefinition implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String roleName;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE, mappedBy="role")
	private List<UserRoleAttributeDefinition> attributes;
	
	public UserRoleDefinition(){
		attributes = new ArrayList<UserRoleAttributeDefinition>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<UserRoleAttributeDefinition> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<UserRoleAttributeDefinition> attributes) {
		this.attributes = attributes;
	}
	
}
