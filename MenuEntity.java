package com.web.relocation.entity;

import com.web.relocation.common.FlagType;
import com.web.relocation.common.MenuType;
import com.web.relocation.dto.manu.SelectMenuDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name = "pja_menu")
@SqlResultSetMapping(
        name = "SelectMenuListMapping",
        classes = @ConstructorResult(
                targetClass = SelectMenuDto.class,
                columns = {
                        @ColumnResult(name="m_no"),
                        @ColumnResult(name="m_name", type = String.class),
                        @ColumnResult(name="m_comment", type = String.class),
                        @ColumnResult(name="m_code", type = String.class),
                        @ColumnResult(name="m_path", type = String.class),
                        @ColumnResult(name="m_parent"),
                        @ColumnResult(name="m_sort"),
                        @ColumnResult(name="m_is_view", type = String.class),
                        @ColumnResult(name="m_is_use", type = String.class),
                        @ColumnResult(name="m_link_type", type = String.class),
                        @ColumnResult(name="m_width", type = String.class),
                        @ColumnResult(name="m_height", type = String.class),
                        @ColumnResult(name="m_is_login_yn", type = String.class),
                        @ColumnResult(name="m_use_button", type = String.class),
                })
)
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="m_no")
    private int no;

    @Column(name = "m_code", updatable = false, nullable = false, columnDefinition = "varchar(10) default '' comment '메뉴 관리 코드'")
    private String code;

    @Column(name="m_auth_menu_code", insertable = false, updatable = false, nullable=false, columnDefinition="varchar(10) default '' comment '권한 메뉴 코드'")
    private String authMenuCode;

    @Column(name="m_name", nullable = false, columnDefinition = "varchar(100) default '' comment '메뉴명'")
    private String name;

    @Column(name="m_comment", columnDefinition = "varchar(255) default '' comment '메뉴 설명'")
    private String comment;

    @Column(name="m_path", columnDefinition = "varchar(255) default '' comment '메뉴 경로'")
    private String path;

    @Column(name="m_parent", updatable = false, nullable = false, columnDefinition = "int(10) default 0 comment '상위 메뉴 일련번호'")
    private int parent;

    @Column(name="m_sort", nullable = false, columnDefinition = "int(10) default 0 comment '정렬 순서'")
    private int sort;

    @Enumerated(EnumType.STRING)
    @Column(name="m_is_view", nullable = false, columnDefinition = "enum('y','n') default 'y' comment '노출 여부'")
    private FlagType isView;

    @Enumerated(EnumType.STRING)
    @Column(name="m_is_use", nullable = false, columnDefinition = "enum('y','n') default 'y' comment '사용 여부'")
    private FlagType isUse;

    @Enumerated(EnumType.STRING)
    @Column(name="m_link_type", columnDefinition = "varchar(8) default 'SELF' comment '메뉴 구분'")
    private MenuType linkType;

    @Column(name="m_width", columnDefinition = "varchar(10) comment '가로 사이즈'")
    private String width;

    @Column(name="m_height", columnDefinition = "varchar(10) comment '세로 사이즈'")
    private String height;

    @Enumerated(EnumType.STRING)
    @Column(name="m_is_login", nullable = false, columnDefinition = "enum('y','n') default 'y' comment '로그인 여부'")
    private FlagType isLogin;

    @Column(name="m_use_button", nullable = false, columnDefinition = "varchar(15) default 'N,N,N,N,N,N,N' comment '기본 기능 버튼 활성화'")
    private String useButton;

    @Column(name="m_ip", nullable = false, columnDefinition = "varchar(15) default '000.000.000.000' comment '등록, 수정, 삭제 ip'")
    private String ip;

    @Enumerated(EnumType.STRING)
    @Column(name="m_is_delete", insertable = false, nullable = false, columnDefinition = "enum('y','n') default 'n' comment '삭제 여부'")
    private FlagType isDelete;

    @Column(name="m_mod_dt", insertable = false, columnDefinition = "datetime comment '수정, 삭제 일자'")
    private LocalDateTime modDt;

    @Column(name="m_reg_dt", updatable = false, columnDefinition = "datetime comment '등록일'")
    private LocalDateTime regDt;

    public MenuEntity(String code, String name, String comment, String useButton) {
        this.code = code;
        this.name = name;
        this.comment = comment;
        this.useButton = useButton;
    }
}
