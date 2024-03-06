package library.libraryback.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Role implements GrantedAuthority {

    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false,unique = true)
    private String roleName;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
