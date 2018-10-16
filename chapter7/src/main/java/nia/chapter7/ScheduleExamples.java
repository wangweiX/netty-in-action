package nia.chapter7;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Listing 7.2 Scheduling a task with a ScheduledExecutorService
 * <p>
 * Listing 7.3 Scheduling a task with EventLoop
 * <p>
 * Listing 7.4 Scheduling a recurring task with EventLoop
 * <p>
 * Listing 7.5 Canceling a task using ScheduledFuture
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class ScheduleExamples {

    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    /**
     * Listing 7.2 Scheduling a task with a ScheduledExecutorService
     */
    public static void schedule() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
        ScheduledFuture<?> future = executor.schedule(
                () -> System.out.println("Now it is 60 seconds later"), 60, TimeUnit.SECONDS);
        executor.shutdown();
    }

    /**
     * Listing 7.3 Scheduling a task with EventLoop
     */
    public static void scheduleViaEventLoop() {
        // get reference from somewhere
        Channel ch = CHANNEL_FROM_SOMEWHERE;
        ScheduledFuture<?> future = ch.eventLoop().schedule(
                () -> System.out.println("60 seconds later"), 60, TimeUnit.SECONDS);

    }

    /**
     * Listing 7.4 Scheduling a recurring task with EventLoop
     */
    public static void scheduleFixedViaEventLoop() {
        // get reference from somewhere
        Channel ch = CHANNEL_FROM_SOMEWHERE;
        ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(
                () -> System.out.println("Run every 60 seconds"), 60, 60, TimeUnit.SECONDS);
    }

    /**
     * Listing 7.5 Canceling a task using ScheduledFuture
     */
    public static void cancelingTaskUsingScheduledFuture() {
        // get reference from somewhere
        Channel ch = CHANNEL_FROM_SOMEWHERE;
        ScheduledFuture<?> future = ch.eventLoop().scheduleAtFixedRate(
                () -> System.out.println("Run every 60 seconds")
                , 60, 60, TimeUnit.SECONDS);
        // Some other code that runs...
        boolean mayInterruptIfRunning = false;
        future.cancel(mayInterruptIfRunning);
    }
}
