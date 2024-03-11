package com.edu.book.infrastructure.po

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableLogic
import java.io.Serializable
import java.sql.Timestamp
import java.time.LocalDateTime

/**
 * @Author: hongruiming
 * @Date: 2020/12/9 10:23 上午
 * @Description:
 */
open class BasePo(

    @TableId(value = "c_id", type = IdType.AUTO)
    var id: Int? = null,

    @TableField(value = "c_create_time")
    var createTime: Timestamp? = null,

    @TableField(value = "c_update_time", update = "now()")
    var updateTime: Timestamp = Timestamp.valueOf(LocalDateTime.now()),

    @TableField(value = "c_is_deleted")
    @TableLogic(value = "0", delval = "1")
    //此处代表逻辑删除 0：正常 1：已删除
    var deleted: Int = 0

) : Serializable