package com.bankmisr.onlinebookstore.controller;

import com.bankmisr.onlinebookstore.dto.UserDTO;
import com.bankmisr.onlinebookstore.entity.User;
import com.bankmisr.onlinebookstore.exception.BadRequestException;
import com.bankmisr.onlinebookstore.exception.NotFoundException;
import com.bankmisr.onlinebookstore.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "User Crud Operations APIs")
@CrossOrigin
@RestController
@RequestMapping("/book-store/v1/api")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@ApiOperation(notes = "status = 403, message = Access Denied" + "\n"
			+ "status = 400 , message =  Email is already exist" + "\n"
			+ "status = 400 , message =  user name is already exist, please use another one" + "\n"
			+ "status = 400 , message =  UserName can not be empty" + "\n"
			+ "status = 400 , message =  Password can not be empty" + "\n"
			+ "status = 400 , message =  Email can not be empty" + "\n"
			+ "status = 400 , message =  Role can not be empty" + "\n"
			+ "status = 400 , message =  FirstName can not be empty" + "\n"
			+ "status = 400 , message =  LastName can not be empty\n"
			+ "\n", value = "This API is used to regiseter users to online book store, You must have Admin role to be able to access this API")
	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	
	@PostMapping("/users")
	public void register(@Valid @RequestBody UserDTO user) throws BadRequestException {
		User persistentUser = new User();
		BeanUtils.copyProperties(user, persistentUser);
		userService.save(persistentUser);
	}


	@ApiOperation(notes = "status = 403, message = Access Denied" + "\n"
			+ "status = 404 , message =  there is no user with userName: {userName}"
			+ "\n", value = "This API is used to edit user (firstname, lastname, activate/deactivate and role) by userId"
			+ ", You must have Admin role to be able to access this API")
	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	@PostMapping("/users/{userName:.+}/edit")
	public void editUser(@PathVariable String userName, @RequestBody UserDTO user) throws NotFoundException {
		User persistentUser = new User();
		BeanUtils.copyProperties(user, persistentUser);
		userService.editUser(userName, persistentUser);
	}

	@ApiOperation(notes = "status = 403, message = Access Denied"
			+ "\n", value = "This API is used to get all users by a given role, You must have Admin role to be able to access this API")
	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	@GetMapping("/users")
	public List<User> getUsers(@RequestParam(value= "offset", required=true) String offset, @RequestParam(value= "limit", required=true) String limit,
			@RequestParam(value= "userRole", required=false) String userRole) throws NumberFormatException {
		return userService.getUsers(Integer.parseInt(offset), Integer.parseInt(limit), userRole);
	}

	@ApiOperation(notes = "status = 403, message = Access Denied" + "\n"
			+ "status = 404 , message =  there is no user with userName: {userName}"
			+ "\n", value = "This API is used to get user by userName, You must have Admin role to be able to access this API")
	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	@GetMapping("/users/{userName:.+}")
	public User getUserByUsername(@PathVariable String userName) throws NotFoundException {
		return userService.findByUsername(userName);
	}

	@ApiOperation(notes = "status = 403, message = Access Denied" + "\n"
			+ "status = 404, message = User not Exist" + "\n"
			+ "\n", value = "This API is used to delete user")
	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	@DeleteMapping("/users/{userName:.+}")
	public void deleteUser(@PathVariable String userName) throws NotFoundException {
		userService.deleteUser(userName);
	}
}
