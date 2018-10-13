package transfers.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class CsvReader {

    private static final String DELIMITER = ",";
    private static final String COMMENT_PREFIX = "#";

    private final String fileName;

    public CsvReader(final String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return list of lines with a list of values as split by {@link #DELIMITER}.
     */
    public List<List<String>> readCsvWithHeader() {
        try (final InputStream input = getClass().getResourceAsStream(fileName)) {
            try (final BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
                return buffer.lines()
                        .filter(line -> !line.startsWith(COMMENT_PREFIX))
                        .skip(1) // header should be first line in the file (excluding comments)
                        .map(line -> Arrays.asList(line.replace(" ", "").split(DELIMITER)))
                        .collect(Collectors.toList());

            } catch (final IOException e) {
                handleIOException(e);
            }
        } catch (final IOException e) {
            handleIOException(e);
        }
        return null;
    }

    private void handleIOException(final IOException e) {
        System.out.println(format("An IO Exception occurred while reading [%s]", fileName));
        e.printStackTrace();
    }
}
