package com.web.relocation.repository;

import com.web.relocation.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardMainRepository {
    @Query(value = "select b_no, bc_code, ref, step, depth, b_title, b_content, b_writer, b_password, b_reply_cnt, b_comment_cnt, b_read_cnt, b_is_notice, b_is_secret, b_is_deleted, a_no, m_no, b_mod_ip, b_mod_dt, b_reg_dt from (" +
            "    select *, 'y' as noti_flag from pja_board where b_is_notice = 'y' and b_is_deleted = 'n'" +
            "  union all" +
            "    select *, 'n' as noti_flag from pja_board where b_is_deleted = 'n' " +
            ") e ",
            countQuery = "select * from (" +
                    "    select * from pja_board where b_is_notice = 'y' and b_is_deleted = 'n'" +
                    "  union all" +
                    "    select * from pja_board where b_is_deleted = 'n' " +
                    ") e"
    , nativeQuery = true)
    Page<Board> findBoardAll(String where, Pageable pageable);

    Page<Board> findAll(Specification<Board> specification, Pageable pageable);

    int count(Specification<Board> specification);
}
