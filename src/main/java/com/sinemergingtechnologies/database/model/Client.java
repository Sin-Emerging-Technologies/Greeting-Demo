package com.sinemergingtechnologies.database.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
//@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "testtable1")
public class Client {

//    Variable may not have been initialized
//    https://projectlombok.org/features/constructor
//    I think this is ok because it runs and Postman works
 // is this needed? rds set up with auto increment

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    private @NonNull String firstname;
    private @NonNull String lastname;
    private @NonNull String email;
    private @NonNull String city;
    private @NonNull String us_state;
    private @NonNull String pass;
    private @NonNull String confirm;

}
