package hu.webuni.student.model;

import lombok.*;

//new:
import jakarta.persistence.*;
//old:
//import javax.persistence.NamedEntityGraph;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@NamedEntityGraph(
        name = "Course.students",
        attributeNodes = @NamedAttributeNode("students"))
@NamedEntityGraph(
        name = "Course.teachers",
        attributeNodes = @NamedAttributeNode("teachers"))
public class Course {

    @Id
    @GeneratedValue // (strategy = GenerationType.AUTO)
    @ToString.Include
    @EqualsAndHashCode.Include
    private long id;

    //@Size(min = 3, max = 20)
    @ToString.Include
    private String name;


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Student> students;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Teacher> teachers;

//    public Course(String name, Student student, Teacher teacher) {
//        this.name = name;
//        this.student = student;
//        this.teacher = teacher;
//    }


    public Course(String name, Set<Student> students, Set<Teacher> teachers) {
        this.name = name;
        this.students = students;
        this.teachers = teachers;
    }

    public Course(String name) {
        this.name = name;
    }

}
