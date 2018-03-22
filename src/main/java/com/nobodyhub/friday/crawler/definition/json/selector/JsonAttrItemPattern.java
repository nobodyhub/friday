package com.nobodyhub.friday.crawler.definition.json.selector;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nobodyhub.friday.crawler.definition.common.item.ItemType;
import com.nobodyhub.friday.crawler.definition.json.JsonItemPattern;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Ryan
 */
@EqualsAndHashCode(callSuper = true)
public class JsonAttrItemPattern extends JsonItemPattern {
    public JsonAttrItemPattern(
            @JsonProperty("urlPattern") String urlPattern,
            @JsonProperty("selectors") List<String> selectors) {
        super(ItemType.TEXT, urlPattern, selectors);
    }
}