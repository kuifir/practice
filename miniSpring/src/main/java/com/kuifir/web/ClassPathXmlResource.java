package com.kuifir.web;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class ClassPathXmlResource implements Resource{
    private final Document document;
    private final Element rootElement;
    private final Iterator<Element> elementIterator;

    public ClassPathXmlResource(URL xmlPath) {
        SAXReader saxReader=new SAXReader();
        try {
            document = saxReader.read(xmlPath);
            rootElement = document.getRootElement();
            elementIterator = rootElement.elementIterator();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasNext() {
        return this.elementIterator.hasNext();
    }

    @Override
    public Element next() {
        return this.elementIterator.next();
    }
}
