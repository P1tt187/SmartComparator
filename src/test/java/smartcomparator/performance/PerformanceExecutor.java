package smartcomparator.performance;

import java.time.Duration;
import java.time.Instant;

public class PerformanceExecutor {

    private final ExecutionBody executionBody;

    public PerformanceExecutor(ExecutionBody executionBody) {
        this.executionBody = executionBody;
    }

    public long calculateDuration() {
        Instant before = Instant.now();
        executionBody.execute();
        Instant after = Instant.now();
        return Duration.between(before, after).toMillis();
    }
}
