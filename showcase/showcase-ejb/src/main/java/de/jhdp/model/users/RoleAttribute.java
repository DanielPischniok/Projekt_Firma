package de.jhdp.model.users;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ROLE_ATTRIBUTES")
public class RoleAttribute implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long attributeId;
	
	@ManyToOne
	private Role role;
	
	private String attributeName;
	
	@OneToMany
	private List<RoleAttributeValue> attributeValues;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public Long getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(Long attributeId) {
		this.attributeId = attributeId;
	}

	public List<RoleAttributeValue> getAttributeValues() {
		return attributeValues;
	}

	public void setAttributeValues(List<RoleAttributeValue> attributeValues) {
		this.attributeValues = attributeValues;
	}

}
