package com.markswell.venda.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VOConverter<V, C> {

    public C entityParse(V t, Class<C> c) {
        return new ModelMapper().map(t, c);
    }

    public  V voParse(C c, Class<V> v) {
        return new ModelMapper().map(c, v);
    }

}
