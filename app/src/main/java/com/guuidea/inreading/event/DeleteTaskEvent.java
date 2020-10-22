package com.guuidea.inreading.event;

import com.guuidea.inreading.model.bean.CollBookBean;

/**
 * Created by guuidea on 17-5-27.
 */

public class DeleteTaskEvent {
    public CollBookBean collBook;

    public DeleteTaskEvent(CollBookBean collBook){
        this.collBook = collBook;
    }
}
