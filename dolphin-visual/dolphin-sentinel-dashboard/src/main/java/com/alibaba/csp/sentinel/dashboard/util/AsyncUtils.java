package com.alibaba.csp.sentinel.dashboard.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Eric Zhao
 * @since 1.4.1
 */
public final class AsyncUtils {

	private static final Logger LOG = LoggerFactory.getLogger(AsyncUtils.class);

	public static <R> CompletableFuture<R> newFailedFuture(Throwable ex) {
		CompletableFuture<R> future = new CompletableFuture<>();
		future.completeExceptionally(ex);
		return future;
	}

	public static <R> CompletableFuture<List<R>> sequenceFuture(List<CompletableFuture<R>> futures) {
		return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenApply(
				v -> futures.stream().map(AsyncUtils::getValue).filter(Objects::nonNull).collect(Collectors.toList()));
	}

	public static <R> CompletableFuture<List<R>> sequenceSuccessFuture(List<CompletableFuture<R>> futures) {
		return CompletableFuture.supplyAsync(() -> futures.parallelStream().map(AsyncUtils::getValue)
				.filter(Objects::nonNull).collect(Collectors.toList()));
	}

	public static <T> T getValue(CompletableFuture<T> future) {
		try {
			return future.get(10, TimeUnit.SECONDS);
		}
		catch (Exception ex) {
			LOG.error("getValue for async result failed", ex);
		}
		return null;
	}

	public static boolean isSuccessFuture(CompletableFuture future) {
		return future.isDone() && !future.isCompletedExceptionally() && !future.isCancelled();
	}

	private AsyncUtils() {
	}

}
