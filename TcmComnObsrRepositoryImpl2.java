package com.satech.ndms.comnmng.log.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.satech.ndms.common.obsr.GbSearchCode;
import com.satech.ndms.common.obsr.SetGbSearchGroupBy;
import com.satech.ndms.comnmng.log.dto.TcmComnObsrGetParamDto;
import com.satech.ndms.comnmng.log.dto.TcmComnObsrResultDto;
import com.satech.ndms.comnmng.log.entity.QTcmComnObsr;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
public class TcmComnObsrRepositoryImpl2 implements TcmComnObsrRepositoryCustom {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    private final QTcmComnObsr qTcmComnObsr;

    public TcmComnObsrRepositoryImpl2(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
        qTcmComnObsr = QTcmComnObsr.tcmComnObsr;
    }

    @Override
    public List<TcmComnObsrResultDto> getComnObsr(TcmComnObsrGetParamDto tcmComnObsrGetParamDto) {

        SetGbSearchGroupBy setGbSearchGroupBy = new SetGbSearchGroupBy(
                qTcmComnObsr.tcmComnObsr.obsrDttm,
                tcmComnObsrGetParamDto.getGbSearch()
        );

        List<Expression<?>> groupByList = setGbSearchGroupBy.getGroupByList();

        List<TcmComnObsrResultDto> result = queryFactory.select(
                        Projections.constructor(TcmComnObsrResultDto.class,
                                // ... 생략 ...
                        )
                )
                .from(qTcmComnObsr)
                .where(
                    qTcmComnObsr.obsrDttm.between(
                            tcmComnObsrGetParamDto.getFromObsrDttm(),
                            tcmComnObsrGetParamDto.getToObsrDttm()
                    )
                )
                .orderBy(qTcmComnObsr.obsrDttm.castToNum(Integer.class).asc())
                .groupBy(groupByList.toArray(new Expression<?>[0]))
                .fetch();
        return result;
    }

    @Override
    public List<TcmComnObsrResultDto> getComnObsrSetData(int ssrSeq, String obsrDttm) {

        List<TcmComnObsrResultDto> result = queryFactory.select(
                        Projections.constructor(TcmComnObsrResultDto.class,
                                // ... 생략 ...
                        )
                )
                .from(qTcmComnObsr)
                .where(
                        (
                            qTcmComnObsr.ssrSeq.eq(ssrSeq)
                        )
                        .and(
                            qTcmComnObsr.obsrDttm.eq(obsrDttm)
                        )

                )
                .fetch();
        return result;
    }

}
