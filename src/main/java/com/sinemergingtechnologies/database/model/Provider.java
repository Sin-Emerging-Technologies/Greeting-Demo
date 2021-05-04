package com.sinemergingtechnologies.database.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "providers")
public class Provider {

    private UUID provider_uuid = UUID.randomUUID();
    private @Id
    Long id = provider_uuid.getMostSignificantBits() & Long.MAX_VALUE;
    private @NonNull String firstname;
    private @NonNull String lastname;
    private @NonNull String email;
    private @NonNull String city;
    private @NonNull String us_state;
    @Column(name="pass")
    private @NonNull String password;
    private @NonNull String confirm;
    private @NonNull String provider_type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        System.out.println("this != o");

        if (!(o instanceof Provider))
            return false;
        System.out.println("(o instanceof Provider)");

        Provider other = (Provider) o;

        if (this.getId() == null) return false;
        System.out.println("this.getId() != null");
        System.out.println(this.getId() + ":" + other.getId());
        System.out.println(this.getEmail() + ":" + other.getEmail());

        return this.getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
