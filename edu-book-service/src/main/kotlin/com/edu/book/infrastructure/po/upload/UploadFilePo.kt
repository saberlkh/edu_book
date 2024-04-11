package com.edu.book.infrastructure.po.upload;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;

/**
 * <p>
 * 文件上传表
 * </p>
 *
 * @author 
 * @since 2024-04-11 22:16:48
 */
@TableName("t_upload_file")
class UploadFilePo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * 文件路径
     */
    @TableField("c_file_path")
    var filePath: String? = null

    /**
     * 文件名称
     */
    @TableField("c_file_key")
    var fileKey: String? = null

    /**
     * 文件类型
     */
    @TableField("c_file_type")
    var fileType: String? = null

    /**
     * 存储桶名称
     */
    @TableField("c_bucket_name")
    var bucketName: String? = null

    /**
     * 视频封面地址
     */
    @TableField("c_video_cover_url")
    var videoCoverUrl: String? = null

}
