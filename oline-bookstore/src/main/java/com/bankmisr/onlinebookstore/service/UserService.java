package com.bankmisr.onlinebookstore.service;

import com.bankmisr.onlinebookstore.constant.AppConstants;
import com.bankmisr.onlinebookstore.constant.RoleEnum;
import com.bankmisr.onlinebookstore.encryption.Encryption;
import com.bankmisr.onlinebookstore.entity.User;
import com.bankmisr.onlinebookstore.exception.BadRequestException;
import com.bankmisr.onlinebookstore.exception.NotFoundException;
import com.bankmisr.onlinebookstore.model.Role;
import com.bankmisr.onlinebookstore.repository.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    public static final int MAX_VALUE = 200;
    private final UserRepository userRepository;
    private final Encryption encryption;

    public UserService(UserRepository userRepository, Encryption encryption) {
        this.userRepository = userRepository;
        this.encryption = encryption;
    }

    @PostConstruct
    private void init() {
        addDefaultUser();
    }

    private void addDefaultUser() {
        User dbUser = userRepository.findByUsername("superadmin");
        if (dbUser == null) {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setUsername("superadmin");
            user.setFirstname("Hussein");
            user.setLastname("Mohamed");
            user.setPassword(encryption.encode(("bookstore#2024@superadmin")));
            List<Role> roles = new ArrayList<>();
            Role role = getRole(RoleEnum.ROLE_SUPERADMIN);
            roles.add(role);
            user.setRoles(roles);
            user.setActive(true);
            user.setCreationDate(new Date());
            userRepository.save(user);
        }
    }

    public static Role getRole(RoleEnum roleName) {
        Role role=new Role();
        role.setId(UUID.randomUUID().toString());
        role.setName(roleName);
        return role;
    }
    public User findByUsername(String username) throws NotFoundException {
        User user = findByUsernameWithPass(username);
        user.setPassword(null);

        return user;

    }
    public User findByUsernameWithPass(String username) throws NotFoundException {
        User user = userRepository.findByUsername(username.toLowerCase());
        if (user == null)
            throw new NotFoundException("there is no user with username: " + username);
        return user;

    }

    @Transactional
    public void save(User user) throws BadRequestException {
        isValidPassword(user);
        user.setEmail(user.getEmail().toLowerCase());
        user.setUsername(user.getUsername().toLowerCase());
        if (isValidUser(user)) {
            try {
                user.setCreationDate(new Date());
                user.setPassword(encodePassword(user.getPassword()));
                user.setActive(true);
                userRepository.save(user);

            } catch (DuplicateKeyException e) {
                throw new BadRequestException("username or email is already exist");
            }
        }
    }

    public static String encodePassword(String password) {
        byte[] encodedBytes = Base64.getEncoder().encode(password.getBytes());
        return new String(encodedBytes);
    }

    private void isValidPassword(User user) throws BadRequestException {
        if (user.getPassword().contains(user.getUsername())) {
            throw new BadRequestException("Password should not be the same or a part of username");
        }
    }
    public boolean isValidUser(User user) throws BadRequestException {

        if (!isValidEmail(user.getEmail())) {
            throw new BadRequestException("Email already exist");
        }

        if (!isValidUsername(user.getUsername())) {
            throw new BadRequestException("Username already exist");
        }

        return true;
    }

    private boolean isValidUsername(String username) {
        return (userRepository.findByUsername(username) == null);
    }

    private boolean isValidEmail(String userEmail) {
        return (userRepository.findByEmail(userEmail) == null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username.toLowerCase());
        if (user == null)
            throw new UsernameNotFoundException("This user does not exist");
        if (!user.isActive())
            throw new UsernameNotFoundException("This User is Suspended");

        List<GrantedAuthority> authorities = new ArrayList<>();

        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true,
                true, true, true, authorities);
    }

    @Transactional
    public void editUser(String userName, User user) throws NotFoundException {
        User currentUser = findByUsernameWithPass(userName);
        if (user.getFirstname() != null) {
            currentUser.setFirstname(user.getFirstname());
        }
        if (user.getLastname() != null) {
            currentUser.setLastname(user.getLastname());
        }
        if (user.getRoles() != null) {
            currentUser.setRoles(user.getRoles());
        }

        userRepository.save(currentUser);
    }

    public List<User> getUsers(int offset, int limit, String userRole){
        if (limit > MAX_VALUE)
            limit = MAX_VALUE;

        Page<User> page = userRepository.findUsersByRolesName(userRole, PageRequest.of(offset, limit));

        return page.getContent();
    }

    public void deleteUser(String userName) throws NotFoundException {
        int deleteStatus = userRepository.deleteUserByUsername(userName);
        if (deleteStatus == 0) {
            throw new NotFoundException(AppConstants.USER_NOT_EXIST);
        }
    }


}
