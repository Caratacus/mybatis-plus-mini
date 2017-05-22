/**
 * Copyright (c) 2011-2014, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.mybatisplus.test.proxy.mapper;

/**
 * <p>
 * 模拟 mybatis 加载 xml 执行 sql 返回
 * </p>
 *
 * @author hubin
 * @Date 2016-07-06
 */
public class UserMapperImpl implements IUserMapper {

    public User selectById(Long id) {
        System.err.println(" ---  执行SQL 绑定数据 ---");
        User user = new User();
        user.setId(id);
        user.setName("mybatis-plus");
        user.setAge(100);
        return user;
    }

}
