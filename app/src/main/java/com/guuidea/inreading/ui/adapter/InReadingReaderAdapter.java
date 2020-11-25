package com.guuidea.inreading.ui.adapter;

import com.glong.reader.widget.ReaderView;
import com.guuidea.inreading.model.bean.BookChapterContent;
import com.guuidea.inreading.model.bean.Chapter;
import com.guuidea.inreading.model.remote.RemoteRepository;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Garrett on 2018/11/28.
 * contact me krouky@outlook.com
 */
public class InReadingReaderAdapter extends
        ReaderView.Adapter<Chapter, BookChapterContent> {

    private Disposable disposable;
    private String bookId;

    public InReadingReaderAdapter(String bookId) {
        this.bookId = bookId;
    }

    @Override
    public String obtainCacheKey(Chapter chapter) {
        return chapter.getId() + chapter.getChapterName();
    }

    @Override
    public String obtainChapterName(Chapter chapter) {
        return chapter.getChapterName();
    }

    @Override
    public String obtainChapterContent(BookChapterContent bookChapterContent) {
        return bookChapterContent.getChapterContent();
    }

    @Override
    public BookChapterContent downLoad(Chapter chapter) {
        final BookChapterContent[] content = new BookChapterContent[1];
        //未切换线程，保证代码的顺序执行，确保正确的返回值（download方法执行在子线程中）
        disposable = RemoteRepository.getInstance().fetchReadingChapter(bookId, chapter.getId())
                .subscribe(new Consumer<BookChapterContent>() {
                    @Override
                    public void accept(BookChapterContent bookChapterContent) throws Exception {
                        content[0] = bookChapterContent;
                    }
                });
        return content[0];
    }

    public Disposable getDownloadDisposable() {
        return disposable;
    }
}
