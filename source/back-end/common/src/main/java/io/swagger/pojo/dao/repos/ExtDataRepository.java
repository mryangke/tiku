package io.swagger.pojo.dao.repos;

import io.swagger.pojo.dao.ExtData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 功能描述：题目扩展信息实体Jpa操作类
 *
 * @作者：黄宽波
 * @时间:2019-07-09
 */
@Repository
public interface ExtDataRepository extends JpaRepository<ExtData, Long> {

    List<ExtData> findAllByProblemIdIn(List<Long> problemIdList);

    @Modifying
    @Query(nativeQuery = true,
            value = "update ext_data set is_del=?2 where problem_id=?1")
    int updateIsDelByProblemId(Long problemId, Boolean isDel);

    void deleteAllByProblemId(Long problemId);
}
