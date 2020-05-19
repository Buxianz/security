package com.rbi.security.tool;

import java.util.List;

/**
 * AbTreeInfo中已经包含
 */
public abstract  class AbTreeInfo<T> {
    private int id;
    private int parentId;
    private List<T> chiled;
    public int getId() {
        return this.id;
    }
    public int getParentId() {
        return this.parentId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<T> getChiled() {
        return chiled;
    }

    public void setChiled(List<T> chiled) {
        this.chiled = chiled;
    }
}
