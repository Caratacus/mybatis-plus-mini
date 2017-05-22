/**
 * Copyright (c) 2011-2014, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baomidou.mybatisplus.enums;

import com.baomidou.mybatisplus.exceptions.MybatisPlusException;

/**
 * <p>
 * 字段策略枚举类
 * </p>
 *
 * @author Caratacus
 * @Date 2017-05-22
 */
public enum FieldStrategy {
    IGNORED, NOT_NULL, NOT_EMPTY;

    /**
     * 获取枚举
     *
     * @param fieldStrategy
     * @return
     */
    public static FieldStrategy getFieldStrategy(String fieldStrategy) {
        FieldStrategy[] fieldStrategies = FieldStrategy.values();
        for (FieldStrategy strategy : fieldStrategies) {
            if (strategy.name().equalsIgnoreCase(fieldStrategy)) {
                return strategy;
            }
        }
        throw new MybatisPlusException("Error: Unknown fieldStrategy type, or do not support changing fieldStrategy!\n");
    }

}
