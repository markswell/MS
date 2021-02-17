package com.markswell.venda.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VOConverter<V, C> {

    public C entityParse(V t, C c) {
        return (C) new ModelMapper().map(t, c.getClass());
    }

    public  V voParse(C c, V v) {
        return (V) new ModelMapper().map(c, v.getClass());
    }
}
