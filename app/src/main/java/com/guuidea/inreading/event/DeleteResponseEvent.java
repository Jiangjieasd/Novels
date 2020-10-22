package com.guuidea.inreading.event;

import com.guuidea.inreading.model.bean.CollBookBean;

/**
 * Created by guuidea on 17-5-27.
 */

public class DeleteResponseEvent {
    public boolean isDelete;
    public CollBookBean collBook;

    public DeleteResponseEvent(boolean isDelete,CollBookBean collBook){
        this.isDelete = isDelete;
        this.collBook = collBook;
    }
}
