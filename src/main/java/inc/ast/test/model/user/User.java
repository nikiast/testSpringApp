package inc.ast.test.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Entity
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean active;
    private LocalDateTime registrationTime;

    @Enumerated(EnumType.STRING)
    private Role role;

    protected User() {
    }

    public User(String username, String email, String password, Role role, LocalDateTime registrationTime, boolean active) {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
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

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
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