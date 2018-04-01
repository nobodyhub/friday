package com.nobodyhub.friday.crawler.core.execution;

import com.google.common.collect.Lists;
import com.nobodyhub.friday.crawler.core.definition.common.link.Link;
import com.nobodyhub.friday.crawler.core.definition.common.task.Task;
import com.nobodyhub.friday.crawler.core.execution.common.CrawlerThreadFactory;
import com.nobodyhub.friday.crawler.core.execution.partition.TaskExecutionPartitionPolity;
import lombok.Getter;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Ryan
 */
@Getter
public class TaskExecution {

    private static final int MAX_THREAD = Runtime.getRuntime().availableProcessors();

    private final List<Future<?>> futures = Lists.newArrayList();

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(MAX_THREAD, MAX_THREAD,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            new CrawlerThreadFactory());

    /**
     * definition of task to be executed
     */
    protected final Task task;
    /**
     * metadata of task execution
     */
    protected final TaskExecutionMetadata metadata;
    /**
     * the limits of number of {@link Link}s to be processed
     * calculated from {@link Task#limit} according to
     */
    protected final AtomicReference<BigInteger> limits;

    private TaskExecution(Task task, TaskExecutionMetadata metadata, BigInteger limits) {
        this.task = task;
        this.metadata = metadata;
        this.limits = new AtomicReference<>(limits);
    }

    public static List<TaskExecution> build(Task task, TaskExecutionPartitionPolity policy) {
        TaskExecutionMetadata metadata = TaskExecutionMetadata.generate();
        List<TaskExecution> executions = Lists.newArrayList();
        List<BigInteger> limits = policy.partition(task.getLimit());
        for (BigInteger limit : limits) {
            executions.add(
                    new TaskExecution(task, metadata, limit)
            );
        }
        return executions;
    }

    public void submit(Runnable r) {
        futures.add(executor.submit(r));
    }

    public void shutdown() {
        executor.shutdown();
        for (Future future : futures) {
            future.cancel(true);
        }
    }

    public boolean isFinished() {
        return limits.get().compareTo(BigInteger.ZERO) <= 0;
    }

    public void countDown() {
        limits.updateAndGet((integer -> integer.subtract(BigInteger.ONE)));
    }
}