package com.easybuild.site.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@NotNull
	@Column(name = "email")
	private String email;

	@Column(name = "password")
	@JsonIgnore
	private String password;
/////////////////////////////////
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "fk_user_id", referencedColumnName = "id")
//	private List<Post> post;

	@ManyToOne
	@JoinColumn(name = "fk_role_id")
	private Role role;
	
	@Column(name = "folder")
	private String folder;
	
	@Column(name = "folder_parent_id")
	private String folderParentId;
	
	@Column(name = "mobile_no")
	private String mobileNo;
	
	@ManyToOne
	@JoinColumn(name = "fk_gender_id")
	private Gender gender;
	
	@Column(name = "profile_pic")
	private String profilePic;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "address")
	private String address;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	protected User() {
	}

	public User(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
	}
//Create
	public User(String firstName, String lastName, String email, String password, String country, String city, 
			String mobileNo, String address, Gender gender, Role role,
			String folder, String folderParentId, String profilePic, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.country = country;
		this.city = city;
		this.mobileNo = mobileNo;
		this.address = address;
		this.gender = gender;
		this.role = role;
		this.folder = folder;
		this.folderParentId = folderParentId;
		this.profilePic = profilePic;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
//Update
//	public User(long id, String firstName, String lastName, String email, Role role, String country, String city, String mobileNo, String address,
//			String profilePic, Timestamp createdAt, Timestamp updatedAt) {
//		this.id =  id;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.role = role;
//		this.country = country;
//		this.city = city;
//		this.mobileNo = mobileNo;
//		this.address = address;
//		this.profilePic = profilePic;
//		this.createdAt = createdAt;
//		this.updatedAt = updatedAt;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getFolderParentId() {
		return folderParentId;
	}

	public void setFolderParentId(String folderParentId) {
		this.folderParentId = folderParentId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
