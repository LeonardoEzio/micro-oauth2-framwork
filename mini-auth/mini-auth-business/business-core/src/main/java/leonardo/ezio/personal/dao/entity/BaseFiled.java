package leonardo.ezio.personal.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author : LeonardoEzio
 * @Date: 2021-07-27 11:13
 */
@Data
@Accessors(chain = true)
public class BaseFiled implements Serializable{

    private static final long serialVersionUID = -7176390653391227433L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

}
