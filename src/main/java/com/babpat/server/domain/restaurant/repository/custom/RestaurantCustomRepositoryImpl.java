package com.babpat.server.domain.restaurant.repository.custom;

import com.babpat.server.domain.restaurant.dto.response.RestaurantInfo;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

import static com.babpat.server.domain.restaurant.entity.QRestaurant.restaurant;

@Repository
@RequiredArgsConstructor
public class RestaurantCustomRepositoryImpl implements RestaurantCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<RestaurantInfo> findRestaurantHasName(String keyword, Pageable pageable) {
        List<RestaurantInfo> matchedRestaurants = jpaQueryFactory
                .select(

                        restaurant.name,
                        restaurant.menus,
                        restaurant.category1,
                        restaurant.category2,
                        restaurant.thumbnail
                )
                .from(restaurant)
                .where(
                        keywordEq(keyword)
                )
                .orderBy(restaurant.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(tuple -> {
                    String name = tuple.get(restaurant.name);
                    String mainMenus = String.valueOf(tuple.get(restaurant.menus));
                    String category1 = tuple.get(restaurant.category1);
                    String category2 = tuple.get(restaurant.category2);
                    String thumbnailUrl = tuple.get(restaurant.thumbnail);

                    List<String> categories = parsingCategories(category1, category2);

                    return new RestaurantInfo(name, mainMenus, categories, thumbnailUrl);
                })
                .toList();


        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(restaurant.count())
                .from(restaurant)
                .where(
                        keywordEq(keyword)
                );

        return PageableExecutionUtils.getPage(matchedRestaurants, pageable, countQuery::fetchOne);
    }

    private BooleanExpression keywordEq(String keywordCond) {
        if (StringUtils.isBlank(keywordCond)) {
            return null;
        }
        return restaurant.name.contains(keywordCond);
    }

    private List<String> parsingCategories(String category1, String category2) {
        return Arrays.stream((category1 + "," + category2).split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}
