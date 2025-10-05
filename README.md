# 🏥 Hospital Management System

> **Note:** This is a learning project created to explore and understand **Spring Data JPA**, **JPQL**, **Custom Queries**, **Entity Relationships**, and **JPA Concepts** through a real-world domain model.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue.svg)](https://www.postgresql.org/)

## 📚 Learning Objectives

This project demonstrates the following JPA/Hibernate concepts:

- ✅ **Query Methods** - Spring Data JPA derived query methods
- ✅ **JPQL Queries** - Custom JPQL queries with parameters
- ✅ **Native Queries** - Direct SQL queries with `nativeQuery = true`
- ✅ **Projections** - DTO projections using constructor expressions
- ✅ **Entity Relationships** - `@OneToOne`, `@OneToMany`, `@ManyToOne`
- ✅ **Cascading** - Understanding cascade types and their impact
- ✅ **Bidirectional Relationships** - `mappedBy` and ownership concepts
- ✅ **Update Queries** - `@Modifying` and `@Transactional`
- ✅ **Pagination** - Using `Pageable` for large datasets
- ✅ **Enum Types** - Mapping Java enums to database
- ✅ **Generation Strategies** - `IDENTITY`, `SEQUENCE`, `TABLE`, `UUID`, `AUTO`
- ✅ **Timestamps** - `@CreationTimestamp` for audit fields
- ✅ **Constraints** - `@UniqueConstraint`, `@Index`, column constraints

---

## 🏗️ Project Structure

```
src/main/java/com.abhinav.hospitalManagement.HospitalManagement/
├── entity/              # JPA Entities
│   ├── Patient.java     # @OneToOne with Insurance, @OneToMany with Appointments
│   ├── Doctor.java      # @OneToMany with Appointments
│   ├── Appointment.java # @ManyToOne with Patient & Doctor
│   ├── Insurance.java   # @OneToOne with Patient (inverse side)
│   ├── Department.java  # Example entity
│   └── type/
│       └── BloodGroupType.java  # Enum for blood groups
├── repository/          # JPA Repositories with custom queries
│   ├── PatientRepository.java
│   ├── DoctorRepository.java
│   ├── AppointmentRepository.java
│   ├── InsuranceRepository.java
│   └── DepartmentRepository.java
├── service/             # Business logic layer
│   ├── PatientServiceImpl.java
│   ├── AppointmentService.java
│   └── InsuranceService.java
└── dto/                 # Data Transfer Objects
    └── BloodGroupCountResponseEntity.java
```

---

## 🔗 Entity Relationships

### Overview

```
Patient (1) ←──→ (1) Insurance
   ↓ (1)
   │
   └──→ (N) Appointment (N) ←── (1) Doctor
```

### Detailed Relationships

#### 1. **Patient ↔ Insurance** (`@OneToOne` Bidirectional)
```java
// Patient (Owning Side)
@OneToOne
@JoinColumn(name = "InsuranceId")
private Insurance insurance;

// Insurance (Inverse Side)
@OneToOne(mappedBy = "insurance")
private Patient patient;
```
- **Concept:** One patient has one insurance policy
- **Ownership:** Patient owns the relationship (has the foreign key)
- **Cascade:** Not defined - prevents accidental deletion propagation

#### 2. **Patient ↔ Appointments** (`@OneToMany` / `@ManyToOne`)
```java
// Patient
@OneToMany(mappedBy = "patient")
private List<Appointment> appointments;

// Appointment
@ManyToOne
@JoinColumn(name="patient_id", nullable = false)
private Patient patient;
```
- **Concept:** One patient can have many appointments
- **Ownership:** Appointment owns the relationship
- **Cascade:** Not defined - appointments don't affect patient lifecycle

#### 3. **Doctor ↔ Appointments** (`@OneToMany` / `@ManyToOne`)
```java
// Doctor
@OneToMany(mappedBy = "doctor")
private List<Appointment> appointments;

// Appointment
@ManyToOne
@JoinColumn(name = "doctor_id", nullable = false)
private Doctor doctor;
```
- **Concept:** One doctor can have many appointments
- **Ownership:** Appointment owns the relationship

---

## 🔍 Query Methods & JPQL Examples

### 1. **Derived Query Methods**
Spring Data JPA automatically generates queries from method names:

```java
// Find by single field
Optional<Patient> findByName(String name);

// Find first match with OR condition
Optional<Patient> findFirstByBirthDateOrEmail(LocalDate date, String email);
```

📖 **Reference:** [Spring Data JPA Query Methods](https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html)

### 2. **JPQL Queries with Positional Parameters**
```java
@Query("SELECT p FROM Patient p WHERE p.bloodGroup = ?1")
List<Patient> findByBloodGroup(@Param("bloodGroup") BloodGroupType bloodGroupType);
```
- Uses entity name (`Patient`) not table name
- `?1` refers to first parameter position

### 3. **JPQL Queries with Named Parameters**
```java
@Query("SELECT p FROM Patient p WHERE p.birthDate > :birthDate")
List<Patient> findByBornAfterDate(@Param("birthDate") LocalDate birthDate);
```
- `:birthDate` is a named parameter
- More readable than positional parameters

### 4. **JPQL Projection with Constructor Expression**
```java
@Query("SELECT new com.abhinav.hospitalManagement.HospitalManagement.dto.BloodGroupCountResponseEntity(p.bloodGroup, COUNT(p.id)) FROM Patient p GROUP BY p.bloodGroup")
List<BloodGroupCountResponseEntity> countEachBloodGroupType();
```
- **Concept:** Returns DTOs instead of entities
- Uses `new` keyword with fully qualified class name
- DTO must have matching constructor: `BloodGroupCountResponseEntity(BloodGroupType, Long)`
- **Why Long?** `COUNT()` returns `Long`, not `int`

### 5. **Native SQL Queries**
```java
@Query(value = "SELECT * FROM patient", nativeQuery = true)
Page<Patient> findAllPatients(Pageable pageable);
```
- Uses actual table name (`patient`) not entity name
- **Limitation:** Cannot use projections with native queries

### 6. **Update Queries**
```java
@Modifying
@Transactional
@Query("UPDATE Patient p SET p.name = :name WHERE id = ?1")
int updateNameWithId(@Param("id") Long id, @Param("name") String name);
```
- Requires `@Modifying` annotation
- Requires `@Transactional` for write operations
- Returns number of rows affected

---

## 📊 Pagination Example

```java
@Query(value = "SELECT * FROM patient", nativeQuery = true)
Page<Patient> findAllPatients(Pageable pageable);

// Usage:
Pageable pageable = PageRequest.of(0, 10); // Page 0, 10 items per page
Page<Patient> patients = patientRepository.findAllPatients(pageable);
```

---

## 🎯 Key JPA Concepts Demonstrated

### 1. **Generation Strategies**
```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

**Available Strategies:**
- **`TABLE`** - Uses a dedicated table to generate IDs. Portable but slower.
- **`SEQUENCE`** - Uses DB sequence (PostgreSQL/Oracle). Efficient for high concurrency.
- **`IDENTITY`** - Uses auto-increment/identity columns. Simple but may limit batching.
- **`UUID`** - Generates UUID primary keys. Good for distributed systems; larger keys.
- **`AUTO`** - Provider selects the best strategy for the database.

### 2. **Cascading (Intentionally NOT Used)**
```java
// No cascade defined - prevents unintended propagation
@ManyToOne
@JoinColumn(name="patient_id", nullable = false)
private Patient patient;
```

**Why no cascading?**
- Changes to `Appointment` should NOT cascade to `Patient` or `Doctor`
- Deleting an appointment shouldn't delete the patient
- Explicit control over entity lifecycle

### 3. **Bidirectional Relationships**
```java
// Owning side (has the foreign key)
@OneToOne
@JoinColumn(name = "InsuranceId")
private Insurance insurance;

// Inverse side (uses mappedBy)
@OneToOne(mappedBy = "insurance")
private Patient patient;
```

**Key Points:**
- Only **one side** should have `@JoinColumn` (owning side)
- Other side uses `mappedBy` (inverse side)
- Prevents two foreign keys pointing to each other

### 4. **Timestamps**
```java
@CreationTimestamp
@Column(nullable = false, updatable = false)
private LocalDateTime createdAt;
```
- Automatically populated on entity creation
- `updatable = false` prevents modification

### 5. **Enums**
```java
@Column(nullable = false)
private BloodGroupType bloodGroup;

// Enum definition
public enum BloodGroupType {
    O_POSITIVE, O_NEGATIVE,
    A_POSITIVE, A_NEGATIVE,
    B_POSITIVE, B_NEGATIVE,
    AB_POSITIVE, AB_NEGATIVE
}
```
- Stored as `VARCHAR` by default (can use `@Enumerated(EnumType.ORDINAL)` for integers)

---

## 🚀 Getting Started

### Prerequisites
- **Java 21** (Amazon Corretto or OpenJDK)
- **PostgreSQL** (any recent version)
- **Maven** (included via wrapper)

### Database Setup

1. Create PostgreSQL database:
```sql
CREATE DATABASE hospitalManagement;
```

2. Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/hospitalManagement
spring.datasource.username=your_username
spring.datasource.password=your_password

# Hibernate DDL auto (for learning)
spring.jpa.hibernate.ddl-auto=update
# Options: create-drop | create | update | validate | none
# For production: use 'validate' or 'none' with migration tools (Flyway/Liquibase)

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Run the Application

```bash
# Using Maven wrapper with Java 21
./mvnw spring-boot:run

# Or compile and run
./mvnw clean package
java -jar target/HospitalManagement-0.0.1-SNAPSHOT.jar
```

### Sample Data

Sample data is automatically loaded from `src/main/resources/data.sql`:
- **8 Patients** with diverse blood groups
- **3 Doctors** with different specializations
- Ready for testing repository methods

---

## 🧪 Testing Repository Methods

### Example Test Cases

```java
// Test JPQL projection
List<BloodGroupCountResponseEntity> results = 
    patientRepository.countEachBloodGroupType();
// Expected: 8 entries, 1 count each for O+, O-, A+, A-, B+, B-, AB+, AB-

// Test derived query method
Optional<Patient> patient = patientRepository.findByName("John Doe");

// Test JPQL with parameter
List<Patient> oPositive = 
    patientRepository.findByBloodGroup(BloodGroupType.O_POSITIVE);

// Test update query
int rowsAffected = patientRepository.updateNameWithId(1L, "John Smith");

// Test pagination
Pageable pageable = PageRequest.of(0, 5);
Page<Patient> page = patientRepository.findAllPatients(pageable);
```

---

## 📝 Hibernate DDL Auto Options

```properties
spring.jpa.hibernate.ddl-auto=update
```

**Options Explained:**

| Option | Behavior | Use Case |
|--------|----------|----------|
| `create-drop` | Creates schema on startup, drops on shutdown | Tests, ephemeral environments |
| `create` | Creates schema on startup, may overwrite data | Early development |
| `update` | Alters schema to match entities (non-destructive) | Development (use with caution) |
| `validate` | Only validates schema matches entities | **Production** (recommended) |
| `none` | No schema management | **Production** with migration tools |

**Production Recommendation:**
- Use `validate` or `none`
- Manage schema with **Flyway** or **Liquibase**
- Prevents accidental data loss
- Provides versioned, controlled migrations

---

## 🎓 Learning Resources

### Spring Data JPA
- [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/reference/)
- [Query Methods Reference](https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html)

### JPQL & Hibernate
- [JPQL Language Reference](https://docs.oracle.com/javaee/7/tutorial/persistence-querylanguage.htm)
- [Hibernate Documentation](https://hibernate.org/orm/documentation/)

### JPA Relationships
- [JPA Entity Relationships](https://www.baeldung.com/jpa-entity-relationships)
- [Cascade Types Explained](https://www.baeldung.com/jpa-cascade-types)

---

## 🛠️ Technologies Used

- **Spring Boot 3.5.6** - Application framework
- **Spring Data JPA** - Data persistence
- **Hibernate 6.6.x** - ORM implementation
- **PostgreSQL** - Relational database
- **Lombok** - Boilerplate code reduction
- **Maven** - Build tool

---

## 📌 Project Status

This is an **incomplete learning project** focused on:
- Understanding JPA/Hibernate concepts
- Practicing JPQL and query methods
- Exploring entity relationships
- Learning cascade and transaction management

**Not production-ready** - Missing:
- REST API layer (controllers)
- Service layer validation
- Exception handling
- Security/authentication
- Comprehensive testing
- API documentation (Swagger)
- Deployment configuration

---

### Key Takeaways

- **Always specify the owning side** in bidirectional relationships
- **Be cautious with cascading** - can lead to unintended deletions
- **Use projections** for read-only DTOs to avoid fetching entire entities
- **COUNT returns Long** not int - important for DTO constructors
- **Validate in production** - never use `create` or `update` in prod
- **Named parameters** are more readable than positional parameters

---
