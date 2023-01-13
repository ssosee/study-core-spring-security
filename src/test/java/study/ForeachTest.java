package study;

import org.junit.jupiter.api.Test;

import java.util.*;

public class ForeachTest {

    @Test
    void forEachTest() {
        List<List<String>> str = new ArrayList<>();
        List<List<String>> newStr = new ArrayList<>();
        List<String> inStr = List.of("a", "b", "c");
        str.add(inStr);

        str.forEach(s1 -> {
            List<String> str2 = new ArrayList<>();
            s1.forEach(s2 -> {
                str2.add(UUID.randomUUID().toString());
                newStr.add(str2);
            });
        });


        for (List<String> strings : newStr) {
            System.out.println(newStr);
            for (String string : strings) {
                System.out.println(string);
            }
        }
    }
}
