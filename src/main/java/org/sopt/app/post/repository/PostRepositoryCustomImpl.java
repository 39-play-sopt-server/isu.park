package org.sopt.app.post.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.sopt.app.post.entity.Category;
import org.sopt.app.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;

import static org.sopt.app.post.entity.QPost.post;

public class PostRepositoryCustomImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public PostRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Post> findPostsByDynamicFilter(Category category, Long authorId, Pageable pageable){
        BooleanBuilder builder = new BooleanBuilder();
        // 카테고리 필터 조건
        if(category != null) {
            builder.and(post.category.eq(category));
        }
        if(authorId != null) {
            builder.and(post.member.id.eq(authorId));
        }
        // 데이터 조회
        List<Post> posts = queryFactory
                .selectFrom(post)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.createdAt.desc())
                .fetch();

        Long total = queryFactory
                .select(post.count())
                .from(post)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(posts, pageable, total == null ? 0 : total);
    }

}
