package com.nobodyhub.friday.crawler.core.definition.html.selector;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.nobodyhub.friday.crawler.core.definition.common.item.Item;
import com.nobodyhub.friday.crawler.core.definition.common.item.ItemType;
import com.nobodyhub.friday.crawler.core.definition.html.HtmlItemPattern;
import com.nobodyhub.friday.crawler.core.definition.html.HtmlLinkContent;
import lombok.EqualsAndHashCode;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

import static com.nobodyhub.friday.crawler.core.definition.common.item.Item.HTML_SRC;

/**
 * @author Ryan
 */
@EqualsAndHashCode(callSuper = true)
public class HtmlAudioItemPattern extends HtmlItemPattern {

    public HtmlAudioItemPattern(
            @JsonProperty("urlPattern") String urlPattern,
            @JsonProperty("selectors") List<String> selectors) {
        super(ItemType.AUDIO, urlPattern, selectors);
    }

    /**
     * @param url
     * @param document
     * @param selector
     * @return
     * @see org.jsoup.nodes.Node#absUrl(String)
     */
    @Override
    protected List<Item> select(String url, HtmlLinkContent document, String selector) {
        List<Item> results = Lists.newArrayList();
        Elements elements = document.getDocument().select(selector + " source");
        for (Element element : elements) {
            Item result = new Item(this.type, url, selector);
            result.addAttr(HTML_SRC, StringUtil.resolve(element.baseUri(), element.attr(HTML_SRC)));
            results.add(result);
        }
        return results;
    }
}
