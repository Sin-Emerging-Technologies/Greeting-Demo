package com.sinemergingtechnologies.database.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "users")
//bezkoder
//@Table(	name = "users",
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames = "username"),
//                @UniqueConstraint(columnNames = "email")
//        })
public class User {

    private UUID uuid = UUID.randomUUID();
//  bezkoder  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id Long id = uuid.getMostSignificantBits() & Long.MAX_VALUE;

//    same as email
//    @NotBlank
//    @Size(max = 50)
    private String username;
    private String firstname;
    private String lastname;
//    bezkoder
//    @NotBlank
//    @Size(max = 50)
//    @Email
    private String email;
    private String city;
    private String us_state;
    @Column(name="pass")
//    bezkoder
//    @NotBlank
//    @Size(max = 120)
    private String password;

//    bezkoder
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "roles",
//            joinColumns = @JoinColumn(name = "id"),
//            inverseJoinColumns = @JoinColumn(name = "id"))
    @JoinTable
    @OneToMany
    private Set<Role> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        System.out.println("this != o");

        if (!(o instanceof User))
            return false;
        System.out.println("(o instanceof User)");

        User other = (User) o;

        if (this.getId() == null) return false;
        System.out.println("this.getId() != null");
        System.out.println(this.getId() + ":" + other.getId());
        System.out.println(this.getEmail() + ":" + other.getEmail());

        return this.getId().equals(other.getId());
    }

    public String toString() {
        return "id: " + this.getId() +
        "\n, uuid: " + this.getUuid() +
        "\n, firstname: " + this.getFirstname() +
        "\n, lastname: " + this.getLastname() +
        "\n, email: " + this.getEmail() +
        "\n, city: " + this.getCity() +
        "\n, us_state: " + this.getUs_state() +
        "\n, password: " + this.getPassword();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
