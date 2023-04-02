package com.ordis.base;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class IssueHelper {
	private final Predicate<?> predicate;
	private final Supplier<? extends Issue> supplier;

	public IssueHelper(Predicate<?> predicate, Supplier<? extends Issue> supplier) {
		this.predicate = predicate;
		this.supplier = supplier;
	}

	public Predicate<?> getPredicate() {
		return predicate;
	}

	public Supplier<? extends Issue> getSupplier() {
		return supplier;
	}
}
