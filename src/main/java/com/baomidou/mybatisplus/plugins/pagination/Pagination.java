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
package com.baomidou.mybatisplus.plugins.pagination;

import java.io.Serializable;

import org.apache.ibatis.session.RowBounds;

/**
 * <p>
 * 简单分页模型
 * </p>
 * 用户可以通过继承 org.apache.ibatis.session.RowBounds实现自己的分页模型<br>
 * 注意：插件仅支持RowBounds及其子类作为分页参数
 *
 * @author hubin
 * @Date 2016-01-23
 */
public class Pagination extends RowBounds implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 总数 */
    private int total;

    /* 每页显示条数，默认 10 */
    private int size = 10;

    /* 总页数 */
    private int pages;

    /* 当前页 */
    private int current = 1;

    /* 查询总记录数（默认 true） */
    private boolean searchCount = true;

    /**
     * 查询总数优化（默认 false 该属性只针对于Optimize.DEFAULT有效)
     */
    private boolean optimizeCount = false;

    public Pagination() {
        super();
    }

    /**
     * <p>
     * 分页构造函数
     * </p>
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public Pagination(int current, int size) {
        this(current, size, true);
    }

    public Pagination(int current, int size, boolean searchCount) {
        super(offsetCurrent(current, size), size);
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.searchCount = searchCount;
    }

    protected static int offsetCurrent(int current, int size) {
        if (current > 0) {
            return (current - 1) * size;
        }
        return 0;
    }

    public int getOffsetCurrent() {
        return offsetCurrent(this.current, this.size);
    }

    public boolean hasPrevious() {
        return this.current > 1;
    }

    public boolean hasNext() {
        return this.current < this.pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPages() {
        if (this.size == 0) {
            return 0;
        }
        this.pages = this.total / this.size;
        if (this.total % this.size != 0) {
            this.pages++;
        }
        return this.pages;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    public boolean isOptimizeCount() {
        return optimizeCount;
    }

    public void setOptimizeCount(boolean optimizeCount) {
        this.optimizeCount = optimizeCount;
    }

    @Override
    public String toString() {
        return "Pagination { total=" + total + " ,size=" + size + " ,pages=" + pages + " ,current=" + current + " }";
    }

}
