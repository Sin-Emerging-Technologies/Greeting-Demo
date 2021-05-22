package com.sinemergingtechnologies.database.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "provideremails")
public class ProviderEmail {

    private UUID uuid = UUID.randomUUID();
    private @Id Long id = uuid.getMostSignificantBits() & Long.MAX_VALUE;
    private @NonNull String providerEmail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        System.out.println("this != o");

        if (!(o instanceof ProviderEmail))
            return false;
        System.out.println("(o instanceof ProviderEmail)");

        ProviderEmail other = (ProviderEmail) o;

        if (this.getId() == null) return false;
        System.out.println("this.getId() != null");
        System.out.println(this.getId() + ":" + other.getId());
        System.out.println(this.getProviderEmail() + ":" + other.getProviderEmail());

        return this.getId().equals(other.getId());
    }

    public String toString() {
        return "id: " + this.getId() +
                "\n, uuid: " + this.getUuid() +
                "\n, providerEmail: " + this.getProviderEmail();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
