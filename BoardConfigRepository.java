package com.web.relocation.repository;

import com.web.relocation.entity.BoardConfig;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardConfigRepository extends JpaRepository<BoardConfig, Long> {
    List<BoardConfig> findAll(Specification<BoardConfig> specification, Pageable pageable);

    int count(Specification<BoardConfig> specification);

    BoardConfig save(BoardConfig boardConfig);

    @Query("Select new BoardConfig(bc.bcCode, bc.bcName) from BoardConfig bc Where bc.bcIsDeleted = 'n'")
    List<BoardConfig> boardCodeList();

    @Modifying
    @Query("Update BoardConfig set " +
            "bcCode = :#{#boardConfig.bcCode}, " +
            "bcName = :#{#boardConfig.bcName}, " +
            "bcCode = :#{#boardConfig.bcCode}, " +
            "bcIsUserView = :#{#boardConfig.bcIsUserView}, " +
            "bcModDt = :#{#boardConfig.bcModDt}, " +
            "bcModIp = :#{#boardConfig.bcModIp}, " +
            "adminNo = :#{#boardConfig.adminNo} " +
            "where bcNo = :#{#boardConfig.bcNo}")
    int updateBoardConfig(BoardConfig boardConfig);

    @Modifying
    @Query("Update BoardConfig Set bcIsDeleted = 'y' where bcNo = :#{#bcNo}")
    int deleteBoardConfig(long bcNo);
}
