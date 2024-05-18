package com.edu.book.infrastructure.po.book;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.edu.book.infrastructure.po.BasePo;
import java.util.Date;

/**
 * <p>
 * 图书表
 * </p>
 *
 * @author 
 * @since 2024-03-24 22:54:53
 */
@TableName("t_book")
class BookPo : BasePo() {

    /**
     * 业务唯一id
     */
    @TableField("c_uid")
    var uid: String? = null

    /**
     * isbn
     */
    @TableField("c_isbn_code")
    var isbnCode: String? = null

    /**
     * 标题
     */
    @TableField("c_title")
    var title: String? = null

    /**
     * 副标题
     */
    @TableField("c_sub_title")
    var subTitle: String? = null

    /**
     * 图片地址
     */
    @TableField("c_pic_url")
    var picUrl: String? = null

    /**
     * 作者
     */
    @TableField("c_author")
    var author: String? = null

    /**
     * 简介
     */
    @TableField("c_summary")
    var summary: String? = null

    /**
     * 出版社
     */
    @TableField("c_publisher")
    var publisher: String? = null

    /**
     * 发布地方
     */
    @TableField("c_public_place")
    var publicPlace: String? = null

    /**
     * 发布时间
     */
    @TableField("c_public_date")
    var publicDate: Date? = null

    /**
     * 页数
     */
    @TableField("c_page")
    var page: Int? = null

    /**
     * 价格 单位分
     */
    @TableField("c_price")
    var price: Int? = null

    /**
     * 装订方式
     */
    @TableField("c_binding")
    var binding: String? = null

    /**
     * isbn10位
     */
    @TableField("c_isbn10_code")
    var isbn10Code: String? = null

    /**
     * 主题词
     */
    @TableField("c_keyword")
    var keyword: String? = null

    /**
     * cip核准号
     */
    @TableField("c_cip")
    var cip: String? = null

    /**
     * 版次
     */
    @TableField("c_edition")
    var edition: String? = null

    /**
     * 印次
     */
    @TableField("c_impression")
    var impression: String? = null

    /**
     * 正文语种
     */
    @TableField("c_language")
    var language: String? = null

    /**
     * 开本
     */
    @TableField("c_format")
    var format: String? = null

    /**
     * 中图法分类
     */
    @TableField("c_book_class")
    var bookClass: String? = null

    /**
     * 园区
     */
    @TableField("c_garden")
    var garden: String? = null

    /**
     * 园区
     */
    @TableField("c_garden_uid")
    var gardenUid: String? = null

}
