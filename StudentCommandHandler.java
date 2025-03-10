package Prodject;

import java.util.Map;

public class StudentCommandHandler {

    private StudentStorage studentStorage = new StudentStorage();

    private void processStatsByCourseCommand(Command command) {
        Map<String, Long> data = studentStorage.getCountByCourse();
        studentStorage.printMap(data);

    }

    private void processStatsByCityCommand(Command command) {
        Map<String, Long> data = studentStorage.getCountByCity();
        studentStorage.printMap(data);

    }


    public void processCommand(Command command) {
        Action action = command.getAction();
        switch (action) {
            case CREATE -> {
                processCreateCommand(command);
                break;
            }
            case UPDATE -> {
                processUpdateCommand(command);
                break;
            }
            case DELETE -> {
                processDeleteCommand(command);
                break;
            }
            case STAT_BY_COURSE -> {
                processStatsByCourseCommand(command);
                break;
            }
            case STAT_BY_CITY -> {
                processStatsByCityCommand(command);
                break;
            }
            case SEARCH -> {
                processSearchCommand(command);
                break;
            }
            default -> {
                System.out.println("Действие " + action + " не поддерживается");
            }
        }
        System.out.println("Обработка команды. Действие: " + command.getAction().name()
                + ", данные: " + command.getData());
    }

    private void processSearchCommand(Command command) {
        String surName = command.getData();
        studentStorage.search(surName);
    }

    private void processCreateCommand(Command command) {
        String data = command.getData();
        String[] dataArray = data.split(",");
        if (dataArray.length != 5) {
            System.out.println("Ввод данных подразумевает ввод 5-ти элементов в формате: Фамилия, Имя, Курс, Город, возраст");
            return;
        }

        // Проверка возраста
        int age;
        try {
            age = Integer.parseInt(dataArray[4].trim());
            if (age < 0) {
                System.out.println("Некорректный ввод возраста, он не может быть отрицательным");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Возраст должен быть числом.");
            return;

        }
        // Проверка на буквенные значения для фамилии, имени, курса и города
        String surname = dataArray[0].trim();
        String name = dataArray[1].trim();
        String course = dataArray[2].trim();
        String city = dataArray[3].trim();

        if (!surname.matches("[a-zA-Zа-яА-ЯёЁ]+") ||
                !name.matches("[a-zA-Zа-яА-ЯёЁ]+") ||
                !course.matches("[a-zA-Zа-яА-ЯёЁ]+") ||
                !city.matches("[a-zA-Zа-яА-ЯёЁ]+")) {
            System.out.println("Фамилия, имя, курс и город должны содержать только буквенные значения.");
            return;
        }
        // Проверка возраста

        Student student = new Student();
        student.setSurname(dataArray[0]);
        student.setName(dataArray[1]);
        student.setCourse(dataArray[2]);
        student.setCity(dataArray[3]);
        student.setAge(Integer.valueOf(dataArray[4]));


        studentStorage.createStudent(student);
        studentStorage.printAll();

    }

    private void processUpdateCommand(Command command) {
        String data = command.getData();
        String[] dataArray = data.split(",");

        if (dataArray.length != 6) {
            System.out.println("Ввод данных подразумевает ввод 6-ти элементов в формате: Код ID, Фамилия, Имя, Курс, Город, возраст");
            return;
        }
        long id;
        try {
            id = Long.parseLong(dataArray[0].trim()); // Пробуем преобразовать первый элемент в Long
        } catch (NumberFormatException e) {
            System.out.println("Первый элемент должен быть числовым ID.");
            return;
        }

        Student student = new Student();
        student.setSurname(dataArray[1]);
        student.setName(dataArray[2]);
        student.setCourse(dataArray[3]);
        student.setCity(dataArray[4]);
        student.setAge(Integer.valueOf(dataArray[5]));

        studentStorage.updateStudent(id, student);
        studentStorage.printAll();

    }

    public void processDeleteCommand(Command command) {
        String data = command.getData();
        Long id;
        try {
            id = Long.valueOf(data);
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат: Код ID должен быть числом");
            return;
        }
        if (id < 0) {
            System.out.println("Неверный формат: Код ID не может быть отрицательным");
            return;
        }
        studentStorage.deleteStudent(id);
        studentStorage.printAll();
    }
}

