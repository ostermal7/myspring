package com.example.myspring.dao;

import com.example.myspring.entity.Discipline;
import com.example.myspring.entity.Semestr;
import com.example.myspring.entity.Student;
import com.example.myspring.entity.StudentDiscAndMark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DAOManager {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DAOManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Discipline> disciplineList(){
        return jdbcTemplate.query("SELECT * FROM discipline WHERE status='1'", new BeanPropertyRowMapper<>(Discipline.class));
    }

    public  void deleteDiscipline(int id){
         jdbcTemplate.update("UPDATE discipline SET status ='0' WHERE id=?",id);
    }

    public void save(Discipline discipline){
        jdbcTemplate.update("INSERT INTO discipline (`disciplines`) VALUES (?)",discipline.getDisciplines());
    }

    public Discipline getDisciplineById(int id){
        return jdbcTemplate.query("SELECT * FROM discipline WHERE status='1' AND id=?",new Object[]{id}, new BeanPropertyRowMapper<>(Discipline.class))
                .stream().findAny().orElse(null);
    }
    public void changeName(Discipline discipline,int id){
        jdbcTemplate.update("UPDATE discipline SET disciplines = ? WHERE id=?", discipline.getDisciplines(),id);
    }
    public List<Student> studentsList(){
        return jdbcTemplate.query("SELECT * FROM student where status='1'", new BeanPropertyRowMapper<>(Student.class));
    }
    public void createStudent(Student student){
        jdbcTemplate.update("INSERT INTO student (`sername`, `name`, `group`, `dateofadmission`) VALUES (?,?,?,?)",student.getSername(),student.getName(),student.getGroup(),student.getDateofadmission());
    }
    public void deleteStudents(int id){
        jdbcTemplate.update("UPDATE student SET status = '0' WHERE id =?", id);
    }
    public Student getStudentById(int id){
        return jdbcTemplate.query("SELECT * FROM student WHERE status='1' and id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Student.class))
                .stream().findAny().orElse(null);
    }
    public void changeStudent(Student student,int id){
        jdbcTemplate.update("UPDATE student SET `sername` =?, `name` = ?, `group` =?, `dateofadmission` =? WHERE `id` = ?",student.getSername(),student.getName(),student.getGroup(),student.getDateofadmission(),id);
    }
    public List<Semestr> getAllActiveSemesters(){
        return jdbcTemplate.query("SELECT * FROM semestr WHERE status='1'",  new BeanPropertyRowMapper<>(Semestr.class));
    }
    public List<Discipline> getDisciplinesFromSemester(int semId){
        return jdbcTemplate.query("SELECT disc.id as id_discipline, disc.disciplines as disciplines, disc.status as discipline_status, s.id as semestr_id, s.name as sem_name, s.duration, s.status as sem_status FROM students_15.semestr_discipline as sd\n" +
                "left join discipline as disc on sd.id_discipline=disc.id\n" +
                "left join semestr as s on sd.id_semestr=s.id\n" +
                "where s.id=? and s.status='1'", new Object[]{semId}, new BeanPropertyRowMapper<>(Discipline.class));
    }
    public void deleteSemester(int id){
        jdbcTemplate.update("UPDATE semestr SET status = '0' WHERE id =? ",id);
    }
    public List<Semestr> gettingIdForNewSemester(){
        return jdbcTemplate.query("SELECT id FROM semestr", new BeanPropertyRowMapper<>(Semestr.class));
    }
    public void createNewSemester(String duration,int id){
        jdbcTemplate.update("INSERT INTO `semestr` (`name`, `duration`, `status`) VALUES ('Семестр "+id+"','"+duration+"', '1')");
    }
    public void putDisciplinesOfNewSemester(int id,String discId){
        jdbcTemplate.update("INSERT INTO `semestr_discipline` (`id_semestr`, `id_discipline`) VALUES (?,?)",id,discId);
    }
    public Semestr getSemesterById(int id){
        return jdbcTemplate.query("SELECT * FROM semestr WHERE status='1' and id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Semestr.class))
                .stream().findAny().orElse(null);
    }
    public void deleteOldDisciplines(int id){
        jdbcTemplate.update("DELETE FROM `semestr_discipline` WHERE (`id_semestr` = ?)",id);
    }
    public void setSemesterDuration(String duration, int id){
        jdbcTemplate.update("UPDATE `semestr` SET `duration` =? WHERE (`id` = ?)",duration,id);
    }
    public List<StudentDiscAndMark> getDisciplinesAndMarksFromSemester(int semesterId, int studentId){
        return jdbcTemplate.query("SELECT sd.id as sem_dis_id, sd.id_discipline, d.disciplines, d.status as disc_status, sd.id_semestr as semester_id,sem.name as semester_name, sem.status as sem_status, st.id as student_id, st.status as student_status, m.mark FROM mark as m\n" +
                "left join student as st on m.id_student=st.id\n" +
                "left join semestr_discipline as sd on m.id_semestr_discipline=sd.id\n" +
                "left join semestr as sem on sd.id_semestr=sem.id\n" +
                "left join discipline as d on sd.id_discipline=d.id\n" +
                "where sd.id_semestr = ? and st.status= '1' and sem.status='1' and d.status='1' and st.id=?", new Object[]{semesterId,studentId}, new BeanPropertyRowMapper<>(StudentDiscAndMark.class));
    }
}
