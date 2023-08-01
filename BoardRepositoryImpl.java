package com.web.relocation.repository;

import com.web.relocation.dto.board.SelectBoardList;
import com.web.relocation.dto.manu.SelectMenuDto;
import com.web.relocation.entity.Board;
import com.web.relocation.entity.QBoard;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class BoardRepositoryImpl extends QuerydslRepositorySupport implements BoardMainRepository {
    @Autowired
    ModelMapper modelMapper;

    @PersistenceContext
    EntityManager em;

    public BoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public List<SelectBoardList> search(String where, String bcCode, Pageable pageable) {
        StringBuilder qb = new StringBuilder();
        List<String> orderList = new ArrayList<>();

        StringBuilder noticeBcCode = new StringBuilder("");
        if (!bcCode.equals("all")) {
            noticeBcCode.append(" and bc_code = '" + bcCode + "' ");
        }

        qb.append("select b_no, bc_code, ref, step, depth, b_title, b_content, b_writer, b_password, b_reply_cnt, b_comment_cnt, b_read_cnt, b_is_notice, b_is_secret, b_is_deleted, a_no, m_no, b_mod_ip, b_mod_dt, b_reg_dt from (" +
                "    select b_no, concat('notice_', bc_code) as bc_code, ref, step, depth, b_title, b_content, b_writer, b_password, b_reply_cnt, b_comment_cnt, b_read_cnt, b_is_notice, b_is_secret, b_is_deleted, a_no, m_no, b_mod_ip, b_mod_dt, b_reg_dt, 'y' as noti_flag from pja_board where b_is_notice = 'y' and b_is_deleted = 'n'" + noticeBcCode.toString() +
                "  union" +
                "    select b_no, bc_code, ref, step, depth, b_title, b_content, b_writer, b_password, b_reply_cnt, b_comment_cnt, b_read_cnt, b_is_notice, b_is_secret, b_is_deleted, a_no, m_no, b_mod_ip, b_mod_dt, b_reg_dt, 'n' as noti_flag from pja_board where " + where +
                ") e ");

        Iterator<Sort.Order> orderIterator = pageable.getSort().iterator();

        while (orderIterator.hasNext()) {
            Sort.Order order = orderIterator.next();
            orderList.add(order.getProperty() + " " + order.getDirection());
        }
        qb.append(" order by ").append(String.join(", ", orderList));

        Query nativeQuery = em.createNativeQuery(qb.toString(), Board.class)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize());

        List<Board> boardLists = nativeQuery.getResultList();
        List<Long> boardNoList = new ArrayList<>();
        List<SelectBoardList> selectBoardList = boardLists.stream().map((p) -> {
            SelectBoardList board = modelMapper.map(p, SelectBoardList.class);
            if (boardNoList.contains(board.getBNo())) {
                board.setBcCode("notice");
                board = board.deepCopy();
            }
            else {
                boardNoList.add(board.getBNo());
            }

            return board;
        }).collect(Collectors.toList());
//        List<SelectBoardList> menu =

        return selectBoardList;
    }

    @Override
    public int getTotalSearchCnt(String where) {
        Query nativeQuery = em.createNativeQuery("select count(b_no) from (" +
                        "    select b_no from pja_board where b_is_notice = 'y' and b_is_deleted = 'n'" +
                        "  union" +
                        "    select b_no from pja_board where " + where +
                        ") e ");

        return ((Number)nativeQuery.getSingleResult()).intValue();
    }
}
