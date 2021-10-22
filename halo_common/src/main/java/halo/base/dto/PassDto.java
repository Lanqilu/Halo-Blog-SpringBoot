package halo.base.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author HALO
 */
@Data
public class PassDto implements Serializable {

	@NotBlank(message = "新密码不能为空")
	private String password;

	@NotBlank(message = "旧密码不能为空")
	private String currentPass;
}
