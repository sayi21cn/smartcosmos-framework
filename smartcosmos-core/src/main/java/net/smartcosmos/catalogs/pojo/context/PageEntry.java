/*
 * SMART COSMOS SDK
 * (C) Copyright 2013-2014, Smartrac Technology Fletcher, Inc.
 * 267 Cane Creek Rd, Fletcher, NC, 28732, USA
 * All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.smartcosmos.catalogs.pojo.context;

import com.fasterxml.jackson.annotation.JsonView;
import net.smartcosmos.catalogs.model.context.IBook;
import net.smartcosmos.catalogs.model.context.IChapter;
import net.smartcosmos.catalogs.model.context.IChapterSection;
import net.smartcosmos.catalogs.model.context.ILibrary;
import net.smartcosmos.catalogs.model.context.IPage;
import net.smartcosmos.catalogs.model.context.IPageEntry;
import net.smartcosmos.catalogs.model.context.IShelf;
import net.smartcosmos.objects.model.context.IObject;
import net.smartcosmos.pojo.base.AccountTypedNamedObject;
import net.smartcosmos.util.json.JsonGenerationView;

public class PageEntry extends AccountTypedNamedObject<IPageEntry> implements IPageEntry
{
    @JsonView(JsonGenerationView.Restricted.class)
    protected ILibrary library;

    @JsonView(JsonGenerationView.Restricted.class)
    protected IShelf shelf;

    @JsonView(JsonGenerationView.Restricted.class)
    protected IBook book;

    @JsonView(JsonGenerationView.Restricted.class)
    protected IChapter chapter;

    @JsonView(JsonGenerationView.Restricted.class)
    protected IChapterSection chapterSection;

    @JsonView(JsonGenerationView.Restricted.class)
    protected IPage page;

    @JsonView(JsonGenerationView.Minimum.class)
    protected IObject object;

    @Override
    public ILibrary getLibrary()
    {
        return library;
    }

    @Override
    public void setLibrary(ILibrary library)
    {
        this.library = library;
    }

    @Override
    public void setShelf(IShelf shelf)
    {
        this.shelf = shelf;
    }

    @Override
    public IShelf getShelf()
    {
        return shelf;
    }

    @Override
    public IBook getBook()
    {
        return book;
    }

    @Override
    public void setBook(IBook book)
    {
        this.book = book;
    }

    @Override
    public IChapter getChapter()
    {
        return chapter;
    }

    @Override
    public void setChapter(IChapter chapter)
    {
        this.chapter = chapter;
    }

    @Override
    public IChapterSection getChapterSection()
    {
        return chapterSection;
    }

    @Override
    public void setChapterSection(IChapterSection chapterSection)
    {
        this.chapterSection = chapterSection;
    }

    @Override
    public IPage getPage()
    {
        return page;
    }

    @Override
    public void setPage(IPage page)
    {
        this.page = page;
    }

    @Override
    public IObject getObject()
    {
        return object;
    }

    @Override
    public void setObject(IObject object)
    {
        this.object = object;
    }
}
