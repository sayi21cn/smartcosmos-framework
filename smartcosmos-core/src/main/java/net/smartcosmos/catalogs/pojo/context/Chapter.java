

package net.smartcosmos.catalogs.pojo.context;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import net.smartcosmos.catalogs.model.context.IBook;
import net.smartcosmos.catalogs.model.context.IChapter;
import net.smartcosmos.catalogs.model.context.IChapterSection;
import net.smartcosmos.catalogs.model.context.ILibrary;
import net.smartcosmos.catalogs.model.context.IShelf;
import net.smartcosmos.pojo.base.AccountNamedObject;
import net.smartcosmos.util.json.JsonGenerationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Chapter extends AccountNamedObject<IChapter> implements IChapter
{
    @JsonView(JsonGenerationView.Restricted.class)
    @JsonDeserialize(as = Library.class)
    protected ILibrary library;

    @JsonView(JsonGenerationView.Restricted.class)
    @JsonDeserialize(as = Shelf.class)
    protected IShelf shelf;

    @JsonView(JsonGenerationView.Restricted.class)
    @JsonDeserialize(as = Book.class)
    protected IBook book;

    @JsonView(JsonGenerationView.Minimum.class)
    @JsonDeserialize(contentAs = ChapterSection.class)
    protected Collection<IChapterSection> chapterSections = new ArrayList<>();

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
    public IChapter addChapterSection(IChapterSection chapterSection)
    {
        Preconditions.checkNotNull(chapterSection, "chapterSection must not be null");
        chapterSections.add(chapterSection);
        return this;
    }

    @Override
    public Collection<IChapterSection> getChapterSections()
    {
        return Collections.unmodifiableCollection(chapterSections);
    }
}
