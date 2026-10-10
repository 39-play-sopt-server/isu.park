package org.sopt.Repository;

import org.sopt.Model.Post;
import java.util.List;
import java.util.Optional;

// Data Access Layer에 속하며, 데이터의 저장/조회/수정/삭제를 전담하는 역할
// 비즈니스 로직과 실제 데이터 저장소 사이의 중개자 역할 담당

// 1. 데이터 Persistence 관리
// 2. 비즈니스 로직과의 격리(추상화)
// 3. 데이터 접근 방식 변경의 유연성 제공
public interface PostRepository {
    void save(Post post); // 저장
    List<Post> findAll();  // 목록 조회
    Optional<Post> findById(int id);  // 단건 조회
    void deleteById(int id);  // 삭제
}
