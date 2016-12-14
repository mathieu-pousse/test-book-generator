package documentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by poussma on 13/12/16.
 */
public class TestBookGenerator implements AfterTestExecutionCallback, BeforeTestExecutionCallback, BeforeAllCallback {

    private MustacheResolver resolver = new MustacheResolver();

    @Override
    public void beforeAll(ContainerExtensionContext context) throws Exception {
        System.out.println(context.getDisplayName());
    }

    @Override
    public void beforeTestExecution(TestExtensionContext context) throws Exception {
        TestEntry entry = new TestEntry();
        if (context.getTestMethod().isPresent()) {
            Method m = context.getTestMethod().get();
            if (m.getAnnotation(DisplayName.class) != null) {
                // don't mind
                entry.displayName = context.getDisplayName();
            } else {
                entry.displayName = prettify(m.getName());
            }

        }
        entry.tags = context.getTags();
        entry.startedAt = System.currentTimeMillis();
        context.getStore().put(context.getUniqueId(), entry);
    }

    private static String prettify(String name) {
        String s = name.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
        Pattern p = Pattern.compile("([A-Z])([a-z])");
        Matcher m = p.matcher(s);
        if (m.find()) {
            StringBuffer sb = new StringBuffer();
            do {
                m.appendReplacement(sb, m.group(1).toLowerCase() + m.group(2));
            } while (m.find());
            m.appendTail(sb);
            return sb.toString();
        }
        return s;
    }

    @Override
    public void afterTestExecution(TestExtensionContext context) throws Exception {
        TestEntry entry = (TestEntry) context.getStore().get(context.getUniqueId());
        entry.took = System.currentTimeMillis() - entry.startedAt;
        entry.status = "success";
        entry.statusIcon = "✔";
        final Optional<Throwable> testException = context.getTestException();
        if (testException.isPresent()) {
            entry.status = "failed";
            entry.statusIcon = "✖";
            entry.reason = testException.get().getMessage();
        } else {
            if (context.getTestInstance() == null) {
                entry.status = "skipped";
                entry.statusIcon = "Ø";
            }
        }
        System.out.println(resolver.resolve(entry));
    }
}
