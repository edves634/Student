package Prodject;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class StudentSurnameStorage {
    private TreeMap<String, Set<Long>> surnamesTreeMap = new TreeMap<>();

    public void studentCreated(Long id, String surname) {
        Set<Long> existingIds = surnamesTreeMap.getOrDefault(surname, new HashSet<>());
        existingIds.add(id);
        surnamesTreeMap.put(surname, existingIds);
    }

    public void studentDeleted(Long id, String surname) {
        surnamesTreeMap.get(surname).remove(id);
    }

    public void studentUpdated(Long id, String oldSurname, String newSurname) {
        studentDeleted(id, oldSurname);
        studentCreated(id, newSurname);

    }

    /**
     * Данный метод возвращает идентификаторы студентов
     * чьи фамилии в алфавитном порядке >= первой фамилии и <= второй фамилии
     * @return set
     */

    public Set<Long> getStudentBySurnamesFirstLastNameAndSecond(String input) {
        if (input == null || input.trim().isEmpty()) {
            // Если введена пустая строка, возвращаем всех студентов
            return surnamesTreeMap.values().stream()
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());
        }

        String[] surnames = input.split(",");
        if (surnames.length == 1) {
            // Если введена только одна фамилия, выполняем точный поиск
            String surname = surnames[0].trim();
            return surnamesTreeMap.getOrDefault(surname, Collections.emptySet());
        } else if (surnames.length == 2) {
            // Если введены две фамилии, выполняем поиск в диапазоне
            String firstSurname = surnames[0].trim();
            String secondSurname = surnames[1].trim();

            // Получаем студентов в диапазоне
            return surnamesTreeMap.subMap(firstSurname, true, secondSurname, true)
                    .values()
                    .stream()
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }
}





