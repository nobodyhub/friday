package com.nobodyhub.friday.crawler.task.common;

/**
 * Types of Interested Contents
 *
 * @author Ryan
 */
public enum ContentType {
    /**
     * Absolute URL
     * the difference with {@link this#TEXT} is the value of {@link this#URL} might be a base URL + value of {@link this#TEXT}
     */
    URL,
    /**
     * Text, usually the attribute of element
     */
    TEXT,
    /**
     * Imaage
     */
    IMAGE,
    /**
     * Audio
     */
    AUDIO,
    /**
     * Video
     */
    VIDEO
}
