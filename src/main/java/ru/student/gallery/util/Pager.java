package ru.student.gallery.util;

import java.util.ArrayList;
import java.util.List;

public class Pager {

    public static List<Integer> build(int page, int pages) {
        List<Integer> numbers = new ArrayList<>();

        if (pages <= 7) {
            for (int i = 1; i <= pages; i++) {
                numbers.add(i);
            }
            return numbers;
        }

        numbers.add(1);

        int from = Math.max(2, page - 1);
        int to = Math.min(pages - 1, page + 1);

        if (from > 2) {
            numbers.add(null);
        }
        for (int i = from; i <= to; i++) {
            numbers.add(i);
        }
        if (to < pages - 1) {
            numbers.add(null);
        }

        numbers.add(pages);
        return numbers;
    }
}
