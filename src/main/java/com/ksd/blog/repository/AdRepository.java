package com.ksd.blog.repository;

import com.ksd.blog.entity.Ad;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface AdRepository extends JpaRepository<Ad, String> {
    // 某类型下“当前生效”的广告，按 sort 升序
//    List<Ad> findByAdTypeIdAndAdBeginTimeLessThanEqualAndAdEndTimeGreaterThanEqualOrderByAdSortAsc(
//            String adTypeId, LocalDateTime now1, LocalDateTime now2);
//    List<Ad> findByAdTypeIdAndStatus(String adTypeId, Integer status);



//        @Query("""
//        SELECT a FROM Ad a
//        WHERE a.adTypeId = :typeId
//          AND a.adBeginTime <= :now
//          AND a.adEndTime >= :now
//        ORDER BY a.adSort ASC
//    """)
//        List<Ad> findActiveByTypeId(@Param("typeId") String typeId,
//                                    @Param("now") LocalDateTime now);
    List<Ad> findByAdTypeId(String adTypeId);
    List<Ad> findByAdTypeIdOrderByAdSort(String adTypeId);
    @Query("""
    SELECT a FROM Ad a
    WHERE a.adTypeId = :typeId
      AND a.adBeginTime <= :now
      AND a.adEndTime >= :now
    ORDER BY a.adSort ASC
""")
    List<Ad> findActiveByTypeId(@Param("typeId") String typeId,
                                @Param("now") LocalDateTime now);

}
