package com.gb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gb.backend.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户反馈数据访问层
 */
@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {
} 