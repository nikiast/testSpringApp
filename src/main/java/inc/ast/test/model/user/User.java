package inc.ast.test.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Entity
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Email should be not empty")
    @Size(min = 1, max = 15, message = "Username should be between 1 and 15 symbol")
    private String username;

    @NotEmpty(message = "Email should be not empty")
    @Pattern(regexp = "\\w+([\\.-]?\\w+)*@(\\w{2,5}[\\.]\\w{2,3})", message = "This email is not valid")
    private String email;

    @NotEmpty(message = "Password should be not empty")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[\\w]{6,}", message = "This password is not valid")
    private String password;

    private Boolean active;

    private String registrationTime;

    @Enumerated(EnumType.STRING)
    private Role role;

    protected User() {
    }

    public User(String username, String email, String password, Role role, String registrationTime, Boolean active) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.active = active;
        this.registrationTime = registrationTime;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isUser() {
        return this.role == Role.USER;
    }

    public boolean isProvider() {
        return this.role == Role.PROVIDER;
    }

    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(getRole());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isActive() == user.isActive() && Objects.equals(getId(), user.getId())
                && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getEmail(), user.getEmail())
                && Objects.equals(getPassword(), user.getPassword())
                && Objects.equals(getRegistrationTime(), user.getRegistrationTime()) && getRole() == user.getRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getEmail(), getPassword(), isActive(), getRegistrationTime(), getRole());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", registrationTime=" + registrationTime +
                ", role=" + role +
                '}';
    }
}