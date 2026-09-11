package assembly.general.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MembershipStatus membershipStatus;

    @Column(nullable = false)
    private Instant memberSince;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

//    public UUID getId() { return id; }
//    public void setId(UUID id) { this.id = id; }
//    public String getEmail() { return email; }
//    public void setEmail(String email) { this.email = email; }
//    public String getPassword() { return password; }
//    public void setPassword(String password) { this.password = password; }
//    public String getFirstName() { return firstName; }
//    public void setFirstName(String firstName) { this.firstName = firstName; }
//    public String getLastName() { return lastName; }
//    public void setLastName(String lastName) { this.lastName = lastName; }
//    public String getPhoneNumber() { return phoneNumber; }
//    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
//    public Role getRole() { return role; }
//    public void setRole(Role role) { this.role = role; }
//    public MembershipStatus getMembershipStatus() { return membershipStatus; }
//    public void setMembershipStatus(MembershipStatus membershipStatus) { this.membershipStatus = membershipStatus; }
//    public Instant getMemberSince() { return memberSince; }
//    public void setMemberSince(Instant memberSince) { this.memberSince = memberSince; }
//    public Instant getCreatedAt() { return createdAt; }
//    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
//    public Instant getUpdatedAt() { return updatedAt; }
//    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

}
