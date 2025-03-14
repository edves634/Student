package Prodject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class StudentStorage {
    private Map<Long, Student> studentStorageMap = new HashMap<>();
    private StudentSurnameStorage studentSurnameStorage = new StudentSurnameStorage();

    private Long currentId = 0L;


    /**
     * Создание данных о студенте
     * @param student данные о студенте
     * @return сгенерированный идентификатор о студенте
     */
    public Long createStudent(Student student) {
        Long nextId = getNextId();
        studentStorageMap.put(nextId, student);
        studentSurnameStorage.studentCreated(nextId, student.getSurname());
        return nextId;
    }

    /**
     * Обновление данных о студенте
     * @param id идентификатор студента
     * @param student данные студента
     * @return true если данные были обновлены, false если студент не был найден
     */
    public boolean updateStudent(Long id, Student student) {
        if (!studentStorageMap.containsKey(id)) {
            return false;
        } else {
            String newSurname = student.getSurname();
            String oldStudent = studentStorageMap.get(id).getSurname();
            studentSurnameStorage.studentUpdated(id, oldStudent, newSurname);
            studentStorageMap.put(id, student);
            return true;
        }
    }

    /**
     * Удаляет данные о студенте
     * @param id идентификатор студента
     * @return true если студент был удален
     * false если студент не был найден по идентификатору
     */
    public boolean deleteStudent(Long id) {
        Student removed = studentStorageMap.remove(id);
        if (removed != null) {
            String surname = removed.getSurname();
            studentSurnameStorage.studentDeleted(id, surname);
        }
        return removed != null;
    }

//    public void search(String surname) {
//        Set<Long> studets = studentSurnameStorage
//                .getStudentBySurnamesLessOrEqualThan(surname);
//        for (Long studentId : studets) {
//            Student student = studentStorageMap.get(studentId);
//            System.out.println(studentId);
//        }
//    }

    /**
     * используем фамилию студентов для вывода фамилии
     * @param surname
     */

    public void search(String surname) {
        Set<Long> studentIds = studentSurnameStorage.getStudentBySurnamesFirstLastNameAndSecond(surname);
        for (Long studentId : studentIds) {
            Student student = studentStorageMap.get(studentId);
            if (student != null) {
                System.out.println(student.getSurname());
            }
        }
    }

    public Long getNextId() {
        currentId = currentId + 1;
        return currentId;
    }

    public void printAll() {
        System.out.println(studentStorageMap);
    }

    public void printMap(Map<String, Long> data) {
        data.entrySet().stream().forEach(e -> {
            System.out.println(e.getKey() + ": " + e.getValue());
        });
    }

    public Map<String, Long> getCountByCourse() {
        Map<String, Long> res = studentStorageMap.values().stream()
                .collect(Collectors.toMap(
                        student -> student.getCourse(),
                        student -> 1L,
                        (count1, count2) -> count1 + count2
                ));
        return res;
    }
    public Map<String, Long> getCountByCity() {
        Map<String, Long> res = studentStorageMap.values().stream()
                .collect(Collectors.toMap(
                        student -> student.getCity(),
                        student -> 1L,
                        (count1, count2) -> count1 + count2
                ));
        return res;
    }
}












