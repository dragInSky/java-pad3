package MyFirstTestAppSpringBoot.model;

import MyFirstTestAppSpringBoot.util.DateTimeUtil;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Response {

    /**
     * Уникальный идентификатор сообщения
     */
    private String uid;

    /**
     * Уникальный идентификатор операции
     */
    private String operationUid;

    /**
     * Время создания сообщения
     */
    private String systemTime;

    /**
     * Код ответа (success / failed)
     */
    private Codes code;

    /**
     * Годовая премия
     */
    private Double annualBonus;

    /**
     * Квартальная премия (только для управленцев)
     */
    private Double quarterlyBonus;

    /**
     * Код ошибки
     */
    private ErrorCodes errorCode;

    /**
     * Сообщение об ошибке
     */
    private ErrorMessages errorMessage;

    public static Response success(Request request) {
        return Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
    }

}