package data.tuple;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-29)
 */
public class Generator {
    private final static ClassLoader CLASS_LOADER = Generator.class.getClassLoader();
    private final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private final static String UPPER_ALPHABET = ALPHABET.toUpperCase();
    private static final String PACKAGE_NAME = "data.tuple";
    private final static String PATH = "src/main/java/" + PACKAGE_NAME.replace('.', '/') + "/";
    private static final int MAX_TUPLE_LENGTH = 26;

    public static void main(String[] args) throws IOException {
        IntStream.rangeClosed(2, MAX_TUPLE_LENGTH)
                .forEach(Generator::generateTupleSource);
        generateInterface(1, MAX_TUPLE_LENGTH);
    }

    private static void generateInterface(int minTupleLength, int maxTupleLength) {
        writeToFile(
                PATH + "Tuple.java",
                format(
                        getResourceAsString("Tuple.jtl"),
                        new HashMap<String, Object>() {{
                            put(
                                    "body",
                                    IntStream.rangeClosed(minTupleLength, maxTupleLength)
                                            .mapToObj(classNumber -> format(
                                                    "" +
                                                            "    static <${types}>\n" +
                                                            "    ${class_name}<${types}>\n" +
                                                            "    t(${constructor_params}) {\n" +
                                                            "        return new ${class_name}<>(${names});\n" +
                                                            "    }\n",
                                                    new HashMap<String, String>() {{
                                                        put("types", getNames(classNumber, UPPER_ALPHABET));
                                                        put("class_name", "T" + classNumber);

                                                        put("constructor_params", getParams(classNumber));
                                                        put("names", getNames(classNumber, ALPHABET));
                                                    }}))
                                            .collect(Collectors.joining("\n")));
                        }}));
    }

    private static void generateTupleSource(int classNumber) {
        writeToFile(
                PATH + "T" + classNumber + ".java",
                formatTemplate(
                        getResourceAsString("T.jtl"),
                        classNumber));
    }

    private static void writeToFile(String fileName, String content) {
        try (final PrintWriter printWriter = new PrintWriter(fileName)) {
            printWriter.write(content);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getResourceAsString(String name) {
        final String template;
        try (final InputStream templateStream = CLASS_LOADER.getResourceAsStream(name)) {
            java.util.Scanner s = new java.util.Scanner(templateStream).useDelimiter("\\A");
            template = s.hasNext() ? s.next() : "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return template;
    }

    private static String formatTemplate(String template, final int classNumber) {
        final int prevClassNumber = classNumber - 1;
        return format(
                template,
                new HashMap<String, String>() {{
                    put("package", PACKAGE_NAME);
                    put("class_name", "T" + classNumber);
                    put("types", getNames(classNumber, UPPER_ALPHABET));
                    put("prev_class", "T" + prevClassNumber);
                    put("prev_types", getNames(prevClassNumber, UPPER_ALPHABET));

                    put("cur_type", encodeNumber(prevClassNumber, UPPER_ALPHABET));
                    put("cur_name", encodeNumber(prevClassNumber, ALPHABET));

                    put("constructor_params", getParams(classNumber));

                    put("prev_names", getNames(prevClassNumber, ALPHABET));

                    put("names", getNames(classNumber, ALPHABET));
                    put(
                            "placeholders",
                            getPlaceholders(classNumber));
                }}
        );
    }

    private static String getPlaceholders(int classNumber) {
        final String placeholders = partition(
                IntStream.range(0, classNumber)
                        .mapToObj(__ -> "?"),
                10)
                .map(line -> String.join(", ", line))
                .collect(Collectors.joining(",\n"));
        return classNumber > 10 ? "\n" + placeholders : placeholders;
    }

    private static String getParams(int classNumber) {
        return partition(
                IntStream.range(0, classNumber)
                        .mapToObj(x -> encodeNumber(x, UPPER_ALPHABET) + " " + encodeNumber(x, ALPHABET)),
                10)
                .map(line -> String.join(", ", line))
                .collect(Collectors.joining(",\n"));
    }

    private static String getNames(int classNumber, String alphabet) {
        final String names = partition(
                IntStream.range(0, classNumber)
                        .mapToObj(x -> encodeNumber(x, alphabet)),
                10)
                .map(line -> String.join(", ", line))
                .collect(Collectors.joining(",\n"));
        return classNumber > 10 ? "\n" + names : names;

    }

    private static String encodeNumber(int number, String alphabet) {
        final StringBuilder sb = new StringBuilder();
        final int length = alphabet.length();
        if (number == 0) {
            sb.append(alphabet.charAt(0));
        }
        while (number != 0) {
            sb.append(alphabet.charAt(number % length));
            number /= length;
        }
        return sb.reverse().toString();
    }

    private static String format(String template, Map<String, ?> args) {
        return args.entrySet().stream()
                .reduce(
                        template,
                        (s, entry) -> s.replace(String.format("${%s}", entry.getKey()),
                                Objects.toString(entry.getValue())),
                        (__, ___) -> null);
    }

    public static <T> Stream<List<T>> partition(Stream<T> xs, int batchSize) {
        final Spliterator<T> xSpliterator = xs.spliterator();
        final Iterator<T> xIterator = Spliterators.iterator(xSpliterator);
        final int characteristics = xSpliterator.characteristics();

        return StreamSupport.stream(
                Spliterators.spliterator(
                        new Iterator<List<T>>() {
                            @Override
                            public boolean hasNext() {
                                return xIterator.hasNext();
                            }

                            @Override
                            public List<T> next() {
                                final List<T> result = new ArrayList<>();
                                while (result.size() < batchSize && xIterator.hasNext()) {
                                    result.add(xIterator.next());
                                }
                                return result;
                            }
                        },
                        (characteristics & Spliterator.SIZED) != 0
                                ? (xSpliterator.getExactSizeIfKnown() - 1) / batchSize + 1
                                : -1,
                        characteristics ^ Spliterator.ORDERED
                ),
                false
        );
    }
}