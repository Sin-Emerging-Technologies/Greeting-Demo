package com.sinemergingtechnologies.database.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "clients")
public class Client {

    private UUID client_uuid = UUID.randomUUID();
    private @Id Long id = client_uuid.getMostSignificantBits() & Long.MAX_VALUE;
    private @NonNull String firstname;
    private @NonNull String lastname;
    private @NonNull String email;
    private @NonNull String city;
    private @NonNull String us_state;
    private @NonNull String pass;
    private @NonNull String confirm;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        System.out.println("this != o");

        if (!(o instanceof Client))
            return false;
        System.out.println("(o instanceof Client)");

        Client other = (Client) o;

        if (this.getId() == null) return false;
        System.out.println("this.getId() != null");
        System.out.println(this.getId() + ":" + other.getId());
        System.out.println(this.getEmail() + ":" + other.getEmail());

        return this.getId().equals(other.getId());
    }

    public String toString() {
        return "id: " + this.getId() +
        "\n, client_uuid: " + this.getClient_uuid() +
        "\n, firstname: " + this.getFirstname() +
        "\n, lastname: " + this.getLastname() +
        "\n, email: " + this.getEmail() +
        "\n, city: " + this.getCity() +
        "\n, us_state: " + this.getUs_state() +
        "\n, pass: " + this.getPass() +
        "\n, confirm: " + this.getConfirm();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
