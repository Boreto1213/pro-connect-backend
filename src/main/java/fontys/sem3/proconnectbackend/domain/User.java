package fontys.sem3.proconnectbackend.domain;

import fontys.sem3.proconnectbackend.domain.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User implements UserDetails {
    @NotNull
    protected Long id;
    @Email
    @Length(max = 30)
    protected String email;
    @NotEmpty
    protected String password;
    @NotEmpty
    @Length(max = 30)
    protected String firstName;
    @NotEmpty
    @Length(max = 30)
    protected String lastName;
    @Pattern(regexp = "^\\+[\\d]{1,3}[\\d]{9}$")
    protected String phone;
    @NotEmpty
    @Length(max = 30)
    protected String city;
    @NotEmpty
    @Length(max = 30)
    protected String address;
    @Length(max = 255)
    protected String profileImageUrl;
    @Enumerated(EnumType.STRING)
    protected Role role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}