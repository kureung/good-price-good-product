package kr.co.gpgp.web.common;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.stream.IntStream;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class Pages<T> {

    private final int firstPage;
    private final int lastPage;

    private Pages(int firstPage, int lastPage) {
        this.firstPage = firstPage;
        this.lastPage = lastPage;
    }

    public static <T> Pages<T> of(Page<T> page, int pageLength) {
        int firstPage = Math.max(1, page.getPageable().getPageNumber() - pageLength);
        int lastPage = Math.min(page.getTotalPages(), page.getPageable().getPageNumber() + pageLength);

        return new Pages<>(firstPage, lastPage);
    }

    public List<Integer> getPages() {
        return IntStream.rangeClosed(firstPage, lastPage)
                .boxed()
                .collect(toList());
    }

}
