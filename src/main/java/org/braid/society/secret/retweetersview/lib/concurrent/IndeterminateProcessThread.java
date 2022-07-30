package org.braid.society.secret.retweetersview.lib.concurrent;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadFactory;
import java.util.function.Supplier;

public class IndeterminateProcessThread implements ThreadFactory {

    private static final Supplier<String> DEFAULT_LAZY_IDENTIFIER = WorkerThreadFactory.DEFAULT_LAZY_IDENTIFIER;

    private static final Supplier<String> DEFAULT_LAZY_SPECIFIER = WorkerThreadFactory.DEFAULT_LAZY_SPECIFIER;

    private final Supplier<String> identifier;

    public IndeterminateProcessThread() {
        this(DEFAULT_LAZY_IDENTIFIER, DEFAULT_LAZY_SPECIFIER.get());
    }

    public IndeterminateProcessThread(Supplier<String> identifier, String specifier) {
        this.identifier = () -> identifier.get() + " " + specifier;
    }

    @Nonnull
    @Override
    public Thread newThread(@Nonnull Runnable r) {
        final Thread thread = new Thread(r, identifier.get());
        thread.setDaemon(false);
        return thread;
    }

}
