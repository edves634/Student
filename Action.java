package Prodject;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Представляет вариант выбранный пользователем
 */

public enum Action {
    EXIT(0, false),
    CREATE(1, true),
    UPDATE(2, true),
    DELETE(3, true),
    STAT_BY_COURSE(4, false),
    STAT_BY_CITY(5, false),
    SEARCH(6, true),
    ERROR(-1, false);


    private Integer code;
    private boolean requireAdditionalData;

    Action(Integer code, boolean requireAdditionalData) {
        this.code = code;
        this.requireAdditionalData = requireAdditionalData;
    }

    public Integer getCode() {
        return code;
    }

    public boolean isRequireAdditionalData() {
        return requireAdditionalData;
    }

    public static Action fromCode(Integer code) {
        return Stream.of(Action.values())
                .filter(action -> Objects.equals(action.getCode(), code))
                .findFirst()
                .orElseGet(()-> {
                    System.out.println("Неизвестный код действия " + code);
                    return Action.ERROR;
                });

        }
    }






