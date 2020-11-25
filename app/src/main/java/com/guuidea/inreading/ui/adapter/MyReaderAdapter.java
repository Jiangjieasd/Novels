package com.guuidea.inreading.ui.adapter;

import com.glong.reader.widget.ReaderView;
import com.guuidea.inreading.model.bean.BaseResponseModel;
import com.guuidea.inreading.model.bean.BookChapterContent;
import com.guuidea.inreading.model.bean.BookContentRequestDto;
import com.guuidea.inreading.model.bean.Chapter;
import com.guuidea.inreading.model.remote.BookApi;
import com.guuidea.inreading.model.remote.RemoteHelper;
import com.guuidea.inreading.model.remote.RemoteRepository;
import com.guuidea.inreading.utils.LogUtils;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

/**
 * Created by Garrett on 2018/11/28.
 * contact me krouky@outlook.com
 */
public class MyReaderAdapter extends ReaderView.Adapter<Chapter, BookChapterContent> {

    private Disposable disposable;
    private String bookId;

    public MyReaderAdapter(String bookId) {
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
