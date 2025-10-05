package com.abhinav.hospitalManagement.HospitalManagement.entity;

import com.abhinav.hospitalManagement.HospitalManagement.entity.type.BloodGroupType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String name;

    @Column(nullable = false,length = 50)
    private String gender;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BloodGroupType bloodGroup;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;


    //This is owning side
    @OneToOne(cascade = {CascadeType.ALL},orphanRemoval = true)
    @JoinColumn(name = "InsuranceId")
    private Insurance insurance;

    //orphan removal
    //Cascade Remove
    @OneToMany(mappedBy = "patient",cascade = {CascadeType.REMOVE},orphanRemoval = true)
//    @ToString.Exclude
    private List<Appointment> appointments=new ArrayList<>();
}











//
//@Entity
//@ToString
//@Getter
//@Setter
////used for table configurations
//@Table(
//        name = "PatientTable",
//        uniqueConstraints = {
//                //no use creating for single
//                //@UniqueConstraint(name="unique_patient_email",columnNames = "email"),
//                @UniqueConstraint(name="unique_patient_name_birthdate",columnNames = {"name","birthDate"})
//        },
//        //checks while insertion and updation but makes slow so keep in check
//        indexes={
//                @Index(name="idx_patient_birth_date",columnList = "birthDate")
//        }
//        //reduces time from n to log n but also increase space and time to insert as not it is storing indexes as well
//
//)
//public class Patient {
//
//    @Id
//    // Generation strategy options (brief):
//    // TABLE    - Uses a dedicated table to generate ids. Portable but can be slower.
//    // SEQUENCE - Uses a DB sequence (Postgres/Oracle). Efficient for high concurrency.
//    // IDENTITY - Uses auto-increment/identity columns. Simple but may limit batching.
//    // UUID     - Generates UUID primary keys. Good for distributed systems; larger keys.
//    // AUTO     - Provider selects the best strategy for the database. Convenient but
//    //            may vary between environments.
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long Id;
//
//    @Column(
////            name="patient_name",
//            nullable=false,
//            length = 40
//    )
//    private String name;
//
////    @ToString.Exclude
//    private LocalDate birthDate;
//
//    @Column(
//            unique = true,
//            nullable = false
//    )
//    private String email;
//
//    private String gender;
//
//    @CreationTimestamp
//    @Column(updatable = false)
//    private LocalDateTime createdAt;
//
//    @Enumerated(EnumType.STRING)
//    private BloodGroupType bloodGroup;
//
//}
