package com.guuidea.inreading.event;

import com.guuidea.inreading.model.flag.BookDistillate;
import com.guuidea.inreading.model.flag.BookSort;
import com.guuidea.inreading.model.flag.BookType;

/**
 * Created by guuidea on 17-4-21.
 */

public class SelectorEvent {

    public BookDistillate distillate;

    public BookType type;

    public BookSort sort;

    public SelectorEvent(BookDistillate distillate,
                         BookType type,
                         BookSort sort) {
        this.distillate = distillate;
        this.type = type;
        this.sort = sort;
    }
}
