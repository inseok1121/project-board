package com.project.projectboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;
@RequiredArgsConstructor
@Transactional
@Service
public class PaginationService {
    private static final int BAR_LENGTH = 5;

    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {
        int startPageNumber = Math.max(currentPageNumber - BAR_LENGTH/2, 0);
        int endPageNumber = Math.min(startPageNumber+BAR_LENGTH, totalPages);

        return IntStream.range(startPageNumber, endPageNumber).boxed().toList();
    }

    public int currentBarLength() {
        return BAR_LENGTH;
    }
}
