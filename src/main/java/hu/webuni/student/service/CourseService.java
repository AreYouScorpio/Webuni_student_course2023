package hu.webuni.student.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import hu.webuni.student.model.Course;
import hu.webuni.student.model.QCourse;
import hu.webuni.student.repository.CourseRepository;
import hu.webuni.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourseService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;


    @Transactional
    public List<Course> searchCourses(Predicate predicate, Pageable pageable) {

        /*

        // without Pageable:
        List<Course> courses = courseRepository.findAll(predicate, "Course.students", EntityGraph.EntityGraphType.LOAD);
        courses = courseRepository.findAll(QCourse.course.in(courses), "Course.teachers", EntityGraph.EntityGraphType.LOAD);
         */

        //with Pageable:

        Page<Course> coursePage = courseRepository.findAll(predicate, pageable);

        BooleanExpression inPredicate = QCourse.course.in(coursePage.getContent());
        List<Course> courses =
                courseRepository.findAll(inPredicate, "Course.students", EntityGraph.EntityGraphType.LOAD, Sort.unsorted());
        // itt felesleges rendezni, viszont mivel hozzaadtuk a sort parametert a QueryDslWithEntityGraphRepository-hoz, muszaj legyen egy parameter, am lehetne itt is pageable.getSort()

        courses =
                courseRepository.findAll(inPredicate, "Course.teachers", EntityGraph.EntityGraphType.LOAD, pageable.getSort());

        return courses;
    }

    @Transactional
    public Course save(Course course) {
        //a takeoff/landing airportból csak az id-t vesszük figyelembe, már létezniük kell
//        student.setTakeoff(studentRepository.findById(flight.getTakeoff().getId()).get());
//        student.setLanding(studentRepository.findById(flight.getLanding().getId()).get());
        return courseRepository.save(course);
    }

    /* my old version
    *** from here


    public List<Course> findCoursesByExample(Course example) {
        long courseId = example.getId();
        System.out.println(courseId);
        String courseName = example.getName();
        System.out.println(courseName);
        String teacherName = null;
        Teacher teacher = example.getTeacher();
        if (teacher != null)
            teacherName = teacher.getName();
        System.out.println(teacherName);
        long studentId = 0L;
        int semester = 0;
        Student student = example.getStudent();
        if (student != null)
            studentId = student.getId();
        System.out.println(studentId);
        if (student != null)
            semester = example.getStudent().getSemester();
        System.out.println(semester);


        //Specification<Flight> spec = Specification.where(null); // üres Specification, ami semmire nem szűr

        // a FlightSpecifications feltételeit ide írjuk bele közvetlen --->

        ArrayList<Predicate> predicates = new ArrayList<Predicate>();


        QCourse course = QCourse.course;

        if (courseId > 0 ) {

            // spec = spec.and(FlightSpecifications.hasId(id));
            predicates.add(course.id.eq(courseId));
        }

        if (StringUtils.hasText(courseName)) // SpringFramework-ös StringUtils
            // spec = spec.and(FlightSpecifications.hasFlightNumber(flightNumber));
            predicates.add(course.name.startsWithIgnoreCase(courseName));


        if (StringUtils.hasText(teacherName)) // SpringFramework-ös StringUtils
            // spec = spec.and(FlightSpecifications.hasFlightNumber(flightNumber));
            predicates.add(course.teacher.name.startsWithIgnoreCase(teacherName));

        if (studentId > 0) {

            // spec = spec.and(FlightSpecifications.hasId(id));
            predicates.add(course.student.id.eq(studentId));
        }

        if (semester > 0) {

            // spec = spec.and(FlightSpecifications.hasId(id));
            predicates.add(course.student.semester.between(semester, semester));
        }



        //return flightRepository.findAll(spec, Sort.by("id"));
        return Lists.newArrayList(courseRepository.findAll(ExpressionUtils.allOf(predicates)));
    }

    * to here ***
     */

    public List<Course> findAll() {
        // return new ArrayList<>(airports.values());
        // return em.createQuery("SELECT a from Airport a", Airport.class).getResultList();

        return courseRepository.findAll();
    }

    public Optional<Course> findById(long id) {
        //return airports.get(id);
        //return em.find(Airport.class, id);
        return courseRepository.findById(id);
    }

    @Transactional
    public void delete(long id) {
        // em.remove(findById(id));
        // airports.remove(id);
        courseRepository.deleteById(id);
    }

    @Transactional
    public Course update(Course course) {
        // airports.put(id, airport);
        //checkUniqueIata(airport.getIata(), airport.getId());
        if (courseRepository.existsById(course.getId())) {
            //return airport;
            //return em.merge(airport); SD--->

//            logEntryService.createLog(String.format("Airport modified with id %d new name is %s",
//                    airport.getId(),
//                    airport.getName()));
//
//            callBackendSystem(); // mesterséges hiba generátor
            return courseRepository.save(course);
        } else
            throw new NoSuchElementException();
    }

}
