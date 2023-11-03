package com.example.backend.mapping;

import java.util.List;

public interface StrategyMapper<S, D> {
    D map(S source);
    S mapReverse(D source);
    List<D> mapAll(List<S> source);
    List<S> mapAllReverse(List<D> source);
}
