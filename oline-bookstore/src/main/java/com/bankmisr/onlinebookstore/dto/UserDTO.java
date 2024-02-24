package com.bankmisr.onlinebookstore.dto;

import com.bankmisr.onlinebookstore.constant.AppConstants;
import com.bankmisr.onlinebookstore.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Setter
@Getter
public class UserDTO {
	private String id;
	@NotEmpty(message = AppConstants.INVALID_USER_NAME)
	@NotNull(message = AppConstants.INVALID_USER_NAME)
	private String username;

	/** The password. */
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotEmpty(message = AppConstants.INVALID_PASWORD)
	@NotNull(message = AppConstants.INVALID_PASWORD)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$", message = "Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character")
	private String password;
	@NotEmpty(message = AppConstants.INVALID_EMAIL)
	@NotNull(message = AppConstants.INVALID_EMAIL)
	private String email;

	@NotEmpty(message = AppConstants.INVALID_FIRSTNAME)
	@NotNull(message = AppConstants.INVALID_FIRSTNAME)
	private String firstname;
	@NotEmpty(message = AppConstants.INVALID_LASTNAME)
	@NotNull(message = AppConstants.INVALID_LASTNAME)
	private String lastname;
	private boolean active;
	@NotNull(message = AppConstants.INVALID_ROLE)
	private List<Role> roles;

}
