package ar.edu.unq.reviewitbackend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.ToString;

@ToString
@Entity
@SequenceGenerator(name = "SEQ_USER", initialValue = 1, allocationSize = 1, sequenceName = "SEQ_USER")
public class User{
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER")
	private Long id;
	
	@NotBlank
    @Size(max = 50)
	private String name;
	
	
	@NotBlank
    @Size(max = 50)
	@Column(name = "last_name")
	private String lastName;
	
	@NotBlank
    @Size(max = 255)
	private String email;
	
	@NotBlank
    @Size(max = 255)
	@Column(name = "user_name")
	private String userName;
	
	@NotBlank
    @Size(max = 255)
	private String password;
	
	public User () {}
	
	public User(String name, String lastName, String email, String nickname, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.userName = nickname;
        this.password = password;
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

}
