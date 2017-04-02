package ru.eninja.flang.lab3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PhoneFinder {

    private static final String phoneRegEx =
            "((\\+7|8)[ ]?)?\\(\\d{3}\\)[ ]\\d{3}-\\d{2}-\\d{2}";
//            "((\\+7|8)[ \\-]?)?([89]\\d{2}|\\([89]\\d{2}\\))[ \\-]?\\d{3}[ \\-]?\\d{2}[ \\-]?\\d{2}";
    private static final Pattern pattern = Pattern.compile(phoneRegEx);


    public static String[] findPhoneNumbers(String filename) throws IOException {
        Path path = Paths.get(filename);

        if (!Files.exists(path)) {
            throw new FileNotFoundException();
        }


        LinkedList<String> phoneNumbersList = new LinkedList<>();
        for (String line : Files.lines(path).collect(Collectors.toList())) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                String group = matcher.group();
                phoneNumbersList.add(group);
            }
        }
        return phoneNumbersList.toArray(new String[phoneNumbersList.size()]);
    }
}
