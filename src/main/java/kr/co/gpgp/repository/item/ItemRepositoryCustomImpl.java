package kr.co.gpgp.repository.item;

import static kr.co.gpgp.domain.item.QItem.item;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import kr.co.gpgp.domain.item.ItemSearchCondition;
import kr.co.gpgp.domain.item.dto.ItemResponse;
import kr.co.gpgp.domain.item.dto.QItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ItemResponse> searchItem(ItemSearchCondition condition, Pageable pageable) {
        List<ItemResponse> content = searchItemContent(condition, pageable);
        JPAQuery<Long> totalCount = getTotalCount(condition);

        return PageableExecutionUtils.getPage(content, pageable, totalCount::fetchOne);
    }

    private JPAQuery<Long> getTotalCount(ItemSearchCondition condition) {
        return queryFactory
                .select(item.count())
                .from(item)
                .where(
                        itemNameContains(condition.getItemName()),
                        priceGoe(condition.getPriceGoe()),
                        priceLoe(condition.getPriceLoe()));
    }

    private List<ItemResponse> searchItemContent(ItemSearchCondition condition, Pageable pageable) {
        return queryFactory
                .select(new QItemResponse(
                        item.id,
                        item.price,
                        item.stockQuantity,
                        item.info.name,
                        item.info.weight,
                        item.info.code,
                        item.info.imageUrl,
                        item.info.releaseDate))
                .from(item)
                .where(
                        itemNameContains(condition.getItemName()),
                        priceGoe(condition.getPriceGoe()),
                        priceLoe(condition.getPriceLoe()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression itemNameContains(String itemName) {
        return StringUtils.hasText(itemName) ? item.info.name.contains(itemName):null;
    }

    private BooleanExpression priceGoe(Integer priceGoe) {
        return priceGoe!=null ? item.price.goe(priceGoe):null;
    }

    private BooleanExpression priceLoe(Integer priceLoe) {
        return priceLoe!=null ? item.price.loe(priceLoe):null;
    }

}
