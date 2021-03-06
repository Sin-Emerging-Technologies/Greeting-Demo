package com.sinemergingtechnologies.database.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client {

//    Variable may not have been initialized
//    https://projectlombok.org/features/constructor
//    I think this is ok because it runs and Postman works
 // is this needed? rds set up with auto increment

//    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    private UUID client_uuid = UUID.randomUUID();
    private @Id BigInteger id = new BigInteger(client_uuid.toString().replace("-", ""), 16);
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

        return this.getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
