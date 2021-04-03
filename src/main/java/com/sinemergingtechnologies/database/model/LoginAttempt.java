package com.sinemergingtechnologies.database.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class LoginAttempt {

    private @NonNull String email;
    private @NonNull String pass;
    private @NonNull String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        System.out.println("this != o");

        if (!(o instanceof Client))
            return false;
        System.out.println("(o instanceof Client)");

        Client other = (Client) o;

        if (this.getEmail() == null) return false;
        System.out.println("this.getEmail() != null");
        System.out.println(this.getEmail() + ":" + other.getEmail());
        System.out.println(this.getEmail() + ":" + other.getEmail());

        return this.getEmail().equals(other.getEmail());
    }

    public String toString() {
        return "email: " + this.getEmail() + "\npass: " + this.getPass() + "\nrole: " + this.getRole();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
