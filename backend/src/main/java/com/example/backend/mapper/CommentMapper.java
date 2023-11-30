package com.example.backend.mapper;

import com.example.backend.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @author demo
 * @since 2023-11-28
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
