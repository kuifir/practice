package com.kuifir.test;

import com.kuifir.test.impl.AServiceImpl;

public class BaseService {

    private AServiceImpl as;
    private BaseService bbs;

    public AService getAs() {
        return as;
    }

    public void setAs(AServiceImpl as) {
        this.as = as;
    }

    public BaseService getBbs() {
        return bbs;
    }

    public void setBbs(BaseService bbs) {
        this.bbs = bbs;
    }

    @Override
    public String toString() {
        return "BaseService{}";
    }
}
