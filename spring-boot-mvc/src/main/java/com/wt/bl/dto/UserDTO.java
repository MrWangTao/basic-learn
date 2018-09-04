package com.wt.bl.dto;

import com.wt.bl.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author WangTao
 *         Created at 18/9/3 下午1:34.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDTO {

    private String id;
    private String name;
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    private String salt;
    private StatusEnum statusEnum;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private String note;

}
