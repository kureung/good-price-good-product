package kr.co.gpgp.repository.item;

import static kr.co.gpgp.domain.item.QItem.item;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import kr.co.gpgp.domain.item.ItemSearchCondition;
import kr.co.gpgp.domain.item.ItemSearchDto;
import kr.co.gpgp.domain.item.QItemSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ItemSearchDto> searchItem(ItemSearchCondition condition, Pageable pageable) {
        List<ItemSearchDto> content = searchItemContent(condition, pageable);
        JPAQuery<Long> totalCount = getTotalCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, totalCount::fetchOne);
    }

    private JPAQuery<Long> getTotalCount(ItemSearchCondition condition) {
        return queryFactory
                .select(item.count())
                .from(item)
                .where(containsCondition(condition.getItemNameOrAuthor()));
    }

    private List<ItemSearchDto> searchItemContent(ItemSearchCondition condition, Pageable pageable) {
        QItemSearchDto itemSearchDto = new QItemSearchDto(item.id, item.info.name, item.price, item.info.author);
        return queryFactory
                .select(itemSearchDto)
                .from(item)
                .where(
                        containsCondition(condition.getItemNameOrAuthor()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression containsCondition(String condition) {
        String nullSafeCondition = Optional.ofNullable(condition).orElse("");
        return itemNameContains(nullSafeCondition).or(authorContains(nullSafeCondition));
    }

    private BooleanExpression itemNameContains(String itemName) {
        return item.info.name.contains(itemName);
    }

    private BooleanExpression authorContains(String author) {
        return item.info.author.contains(author);
    }

}
