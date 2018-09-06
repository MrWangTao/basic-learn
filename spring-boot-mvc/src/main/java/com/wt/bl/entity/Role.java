package com.wt.bl.entity;

import com.wt.bl.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author WangTao
 *         Created at 18/9/5 下午2:00.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private String id;
    private String name;
    private String cnName;
    private Integer level;
    private StatusEnum statusEnum;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private String note;

}
