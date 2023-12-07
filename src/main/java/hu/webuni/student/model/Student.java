package hu.webuni.student.model;

import lombok.*;
//old:
//import javax.persistence.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.*;

//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Cacheable
public class Student {

    @Id
    @GeneratedValue // (strategy = GenerationType.AUTO)
    @ToString.Include
    @EqualsAndHashCode.Include()
    private long id;

    @ToString.Include
    //@Size(min = 3, max = 20)
    private String name;
    //@Size (min = 3, max = 10)
    private LocalDate birthdate;
    private int semester;

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;



    public Student(String name, LocalDate birthdate, int semester) {
        this.name = name;
        this.birthdate = birthdate;
        this.semester = semester;
    }
}
