package documentation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by poussma on 14/12/16.
 */
public class MustacheResolver {

    private static Pattern MUSTACHE = Pattern.compile("\\{\\{ *?([\\w.]+) *?}}");

    public String resolve(TestEntry context) {
        BufferedReader input = new BufferedReader(new InputStreamReader(MustacheResolver.class.getResourceAsStream("/acme/test-entry.snippet")));
        return input.lines().map(line -> {
            Matcher matcher = MUSTACHE.matcher(line);
            if (!matcher.find()) {
                return line;
            } else {
                StringBuffer buffer = new StringBuffer();
                do {
                    matcher.appendReplacement(buffer, stringify(value(matcher.group(1), context)));
                } while (matcher.find());
                matcher.appendTail(buffer);
                return buffer.toString();
            }
        }).collect(Collectors.joining("\n"));
    }

    private String stringify(Object v) {
        return v == null ? "" : v.toString();
    }

    private Object value(String group, TestEntry context) {
        try {
            return context.getClass().getMethod("get" + Character.toUpperCase(group.charAt(0)) + group.substring(1)).invoke(context);
        } catch (NoSuchMethodException e) {
            return context.misc.get(context);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
