package main.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class OffsetLimitPageable extends PageRequest {
    private final int offset;

    protected OffsetLimitPageable(int offset, int size, Sort sort) {
        super(offset, size, sort);
        this.offset = offset;
    }
    @Override
    public long getOffset() {
        return offset;
    }
    public static OffsetLimitPageable of(int offset, int size, Sort sort) {
        return new OffsetLimitPageable(offset, size, sort);
    }
    public static OffsetLimitPageable of(int offset, int size) {
        return new OffsetLimitPageable(offset, size, Sort.unsorted());
    }
}
