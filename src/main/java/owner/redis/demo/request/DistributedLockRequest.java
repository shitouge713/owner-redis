package owner.redis.demo.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@ApiModel(description = "分布式锁入参")
public class DistributedLockRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "订单编号不可为空")
    private String orderNo;
}
