package com.easybuild.site.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "tbl_user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "username")
	private String username;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	@JsonIgnore
	private String password;

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

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	protected User() {
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
//Create
	public User(String username, String email, String password, Gender gender, Role role,
			String folder, String folderParentId, String profilePic, Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.role = role;
		this.folder = folder;
		this.folderParentId = folderParentId;
		this.profilePic = profilePic;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
//Update
	public User(String username, String email, Role role, String mobileNo, Gender gender,
			String profilePic, Timestamp createdAt, Timestamp updatedAt) {
		this.username = username;
		this.email = email;
		this.role = role;
		this.mobileNo = mobileNo;
		this.gender = gender;
		this.profilePic = profilePic;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

}
