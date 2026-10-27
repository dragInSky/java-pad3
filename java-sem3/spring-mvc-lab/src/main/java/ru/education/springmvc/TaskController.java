package ru.education.springmvc;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Locale;

@RestController
public class TaskController {

    private static final String TEXT_PLAIN_UTF8 = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8";

    @GetMapping(value = "/about", produces = TEXT_PLAIN_UTF8)
    public String about() {
        return "Дрягин Максим Андреевич, группа РИМ-250950 (Прикладной анализ данных)";
    }

    @GetMapping(value = "/time", produces = TEXT_PLAIN_UTF8)
    public String time() {
        return OffsetDateTime.now().toString();
    }

    @GetMapping(value = "/uppercase", produces = TEXT_PLAIN_UTF8)
    public String uppercase(@RequestParam(name = "text") String text) {
        return text.toUpperCase(Locale.ROOT);
    }
}
