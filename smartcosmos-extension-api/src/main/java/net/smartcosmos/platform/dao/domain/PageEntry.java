package net.smartcosmos.platform.dao.domain;

/*
 * *#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*
 * SMART COSMOS Platform Server API
 * ===============================================================================
 * Copyright (C) 2013 - 2015 Smartrac Technology Fletcher, Inc.
 * ===============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#*#
 */

import java.util.Collection;
import java.util.Iterator;

import net.smartcosmos.platform.api.dao.domain.IPage;
import net.smartcosmos.util.json.JsonGenerationView;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

public class PageEntry<T> implements IPage<T>
{
    @JsonView(JsonGenerationView.Standard.class)
    private final int totalPages;
    @JsonView(JsonGenerationView.Standard.class)
    private final int totalSize;
    @JsonView(JsonGenerationView.Standard.class)
    private final int currentPage;
    @JsonView(JsonGenerationView.Standard.class)
    private final int pageSize;
    @JsonView(JsonGenerationView.Standard.class)
    private final Collection<T> contents;

    public PageEntry(@JsonProperty("contents") final Collection<T> contents,
            @JsonProperty("totalPages") final int totalPages,
            @JsonProperty("totalSize") final int totalSize, @JsonProperty("currentPage") final int currentPage,
            @JsonProperty("pageSize") final int pageSize)
    {
        super();
        this.contents = contents;
        this.totalPages = totalPages;
        this.totalSize = totalSize;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    @Override
    public Iterator<T> iterator()
    {
        return contents.iterator();
    }

    @Override
    public int getNumber()
    {
        return currentPage;
    }

    @Override
    public int getSize()
    {
        return this.contents.size();
    }

    @Override
    public int getNumberOfElements()
    {
        return pageSize;
    }

    @Override
    public Collection<T> getContent()
    {
        return contents;
    }

    @Override
    public boolean hasContent()
    {
        return contents.size() > 0;
    }

    @Override
    public boolean isFirst()
    {
        return currentPage == 1;
    }

    @Override
    public boolean isLast()
    {
        return currentPage == totalPages;
    }

    @Override
    public boolean hasNext()
    {
        return currentPage < totalPages;
    }

    @Override
    public boolean hasPrevious()
    {
        return currentPage > 1;
    }

    @Override
    public int getTotalPages()
    {
        return this.totalPages;
    }

    @Override
    public int getTotalElements()
    {
        return this.totalSize;
    }

}
