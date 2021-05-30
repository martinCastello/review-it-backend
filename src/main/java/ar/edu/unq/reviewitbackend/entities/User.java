package ar.edu.unq.reviewitbackend.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.ToString;

@ToString
@Entity
public class User extends Auditable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="El campo Nombre no puede estar vacio")
    @Size(max = 50)
	private String name;
	
	@NotBlank(message="El campo Apellido no puede estar vacio")
    @Size(max = 50)
	@Column(name = "last_name")
	private String lastName;
	
	@Email(message="El correo electronico no tiene un formato valido")
    @NotBlank(message="Debe proporcionar un correo electronico")
    @Size(max = 255)
	private String email;
	
	@NotBlank
    @Size(max = 50)
	@Column(name = "user_name")
	private String userName;
	
	@NotBlank
    @Size(max = 255)
	private String password;
	
	@NotBlank
	@Size(max = 255)
	private String avatar = "https://www.adl-logistica.org/wp-content/uploads/2019/07/imagen-perfil-sin-foto.png";
	
	@NotNull
	@Column(name = "is_private")
	private Boolean isPrivate = false;
	
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "user")
	private List<Review> reviews;

	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="to")
    private List<Follower> followers;

	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy="from")
    private List<Follower> following;

	@Lob
	@Column(name = "avatar_file")
	private byte[] avatarFile;
	
	@Transient
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private MultipartFile avatarFileForView;
	
	public User () {}
	
	public User(String name, String lastName, String email, String nickname, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.userName = nickname;
        this.password = password;
    }
	
	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public String getUserName() {
		return this.userName;
	}

	public String getPassword() {
		return this.password;
	}
	
	public String getAvatar() {
		return this.avatar;
	}
	
	public List<Follower> getFollowing(){
		return this.following;
	}
	
	public List<Follower> getFollowers(){
		return this.followers;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public byte[] getAvatarFile() {
		return avatarFile;
	}

	public void setAvatarFile(byte[] avatarFile) {
		this.avatarFile = avatarFile;
	}

	public MultipartFile getAvatarFileForView() {
		return avatarFileForView;
	}

	public void setAvatarFileForView(MultipartFile avatarFileForView) {
		this.avatarFileForView = avatarFileForView;
	}

//	public void addFollower(Followers follower){
//		this.followers.add(follower);
//	}
//
//	public void addFollow(Followers follow){
//		this.following.add(follow);
//	}
}
