package ru.learn.learnSpring.api;

import lombok.Data;

@Data
public class PostParameters {

    /**
     * ID пользователя
     */
    private int id;

    /**
     * Получить отложенные записи, работает только для текущего пользователя
     */
    private boolean queue;

    private String text;


    private Long dateFom;


    private Long dateTo;


    /**
     * Отступ от начала списка
     */
    private Integer offset;

    /**
     * Количество элементов на страницу
     */
    private int itemPerPage = 20;

    public PostParameters(){}

    public PostParameters(String text, Long dateFrom, Long dateTo, int offset, int itemPerPage) {
        this.text = text;
        this.dateFom = dateFrom;
        this.dateTo = dateTo;
        this.offset = offset;
        this.itemPerPage = itemPerPage;
    }
}
