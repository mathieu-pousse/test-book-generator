package documentation;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by poussma on 13/12/16.
 */
@Getter
public class TestEntry {

    long startedAt;

    long took;

    String displayName;

    String status;

    String reason;

    Set<String> tags;

    String statusIcon;

    Map<String, Object> misc = new HashMap<>();

    @Override
    public String toString() {
        return "TestEntry{" +
                "startedAt=" + startedAt +
                ", took=" + took +
                ", displayName='" + displayName + '\'' +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                ", tags=" + tags +
                ", statusIcon='" + statusIcon + '\'' +
                '}';
    }
}
