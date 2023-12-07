package hu.webuni.student.service;

import com.google.common.collect.Lists;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import hu.webuni.student.model.QStudent;
import hu.webuni.student.model.Student;
import hu.webuni.student.repository.CourseRepository;
import hu.webuni.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;

    @Transactional
    public Student save(Student student) {
        //a takeoff/landing airportból csak az id-t vesszük figyelembe, már létezniük kell
//        student.setTakeoff(studentRepository.findById(flight.getTakeoff().getId()).get());
//        student.setLanding(studentRepository.findById(flight.getLanding().getId()).get());
        return studentRepository.save(student);
    }

    public List<Student> findStudentsByExample(Student example) {
        long id = example.getId();
        String name = example.getName();

        LocalDate birthdate = example.getBirthdate();

        int semester = example.getSemester();

        //Specification<Flight> spec = Specification.where(null); // üres Specification, ami semmire nem szűr

        // a FlightSpecifications feltételeit ide írjuk bele közvetlen --->

        ArrayList<Predicate> predicates = new ArrayList<Predicate>();

        QStudent student = QStudent.student;

        if (id > 0) {

            // spec = spec.and(FlightSpecifications.hasId(id));
            predicates.add(student.id.eq(id));
        }

        if (StringUtils.hasText(name)) // SpringFramework-ös StringUtils
            // spec = spec.and(FlightSpecifications.hasFlightNumber(flightNumber));
            predicates.add(student.name.startsWithIgnoreCase(name));


        if (semester >= 1) // SpringFramework-ös StringUtils
        //spec = spec.and(FlightSpecifications.hasTakeoffTime(takeoffTime));
        {

            predicates.add(student.semester.between(semester, semester+1));

        }


        //return flightRepository.findAll(spec, Sort.by("id"));
        return Lists.newArrayList(studentRepository.findAll(ExpressionUtils.allOf(predicates)));
    }

    public List<Student> findAll() {
        // return new ArrayList<>(airports.values());
        // return em.createQuery("SELECT a from Airport a", Airport.class).getResultList();

        return studentRepository.findAll();
    }

    public Optional<Student> findById(long id) {
        //return airports.get(id);
        //return em.find(Airport.class, id);
        return studentRepository.findById(id);
    }

    @Transactional
    public void delete(long id) {
        // em.remove(findById(id));
        // airports.remove(id);
        studentRepository.deleteById(id);
    }

    @Transactional
    public Student update(Student student) {
        // airports.put(id, airport);
        //checkUniqueIata(airport.getIata(), airport.getId());
        if (studentRepository.existsById(student.getId())) {
            //return airport;
            //return em.merge(airport); SD--->

//            logEntryService.createLog(String.format("Airport modified with id %d new name is %s",
//                    airport.getId(),
//                    airport.getName()));
//
//            callBackendSystem(); // mesterséges hiba generátor
            return studentRepository.save(student);
        } else
            throw new NoSuchElementException();
    }

}
