package com.nobodyhub.friday.crawler.request.interest.html.attr;

import com.google.common.collect.Lists;
import com.nobodyhub.friday.crawler.request.interest.common.InterestType;
import com.nobodyhub.friday.crawler.request.interest.html.HtmlInterest;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * @author Ryan
 */
public class HtmlAttrInterest extends HtmlInterest<String, HtmlAttrSelector> {
    public HtmlAttrInterest(String urlPattern) {
        super(InterestType.TEXT, urlPattern);
    }

    @Override
    public List<String> fetch(Document document) {
        List<String> contents = Lists.newArrayList();
        for (HtmlAttrSelector selector : selectors) {
            contents.addAll(selector.select(document));
        }
        return contents;
    }
}