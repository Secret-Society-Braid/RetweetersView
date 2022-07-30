package org.braid.society.secret.retweetersview.lib.concurrent;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

public class WorkerThreadFactory implements ThreadFactory {

    protected static final Supplier<String> DEFAULT_LAZY_IDENTIFIER = () -> "RetweetersView";

    protected static final Supplier<String> DEFAULT_LAZY_SPECIFIER = () -> "default";

    private final AtomicLong count = new AtomicLong(0);
    private final Supplier<String> identifier;
    private final boolean isDaemon;

    public WorkerThreadFactory() {
        this(DEFAULT_LAZY_IDENTIFIER, DEFAULT_LAZY_SPECIFIER.get());
    }

    public WorkerThreadFactory(Supplier<String> identifier, String specifier) {
        this(identifier, specifier, true);
    }

    public WorkerThreadFactory(Supplier<String> identifier, String specifier, boolean isDaemon) {
        this.identifier = () -> identifier.get() + " " + specifier;
        this.isDaemon = isDaemon;
    }

    @Override
    public Thread newThread(@Nonnull Runnable r) {
        final Thread thread = new Thread(r, identifier.get() + "-Worker " + count.incrementAndGet());
        thread.setDaemon(isDaemon);
        return thread;
    }
}
