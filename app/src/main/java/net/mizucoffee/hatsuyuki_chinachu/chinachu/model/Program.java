package net.mizucoffee.hatsuyuki_chinachu.chinachu.model;

import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Program {

    private String id;
    private String category;
    private String title;
    private String fullTitle;
    private String detail;
    private Long start;
    private Long end;
    private Integer seconds;
    private String description;
    private HashMap<String,String> extra = new HashMap<>();;
    private Channel channel;
    private String subTitle;
    private Object episode;
    private List<String> flags = null;

    private String command;
    private String recordedFormat;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Object getEpisode() {
        return episode;
    }

    public void setEpisode(Object episode) {
        this.episode = episode;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public List<String> getFlags() {
        return flags;
    }

    public void setFlags(List<String> flags) {
        this.flags = flags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String, String> getExtra() {
        return extra;
    }

    public void setExtra(HashMap<String, String> extra) {
        this.extra = extra;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getRecordedFormat() {
        return recordedFormat;
    }

    public void setRecordedFormat(String recordedFormat) {
        this.recordedFormat = recordedFormat;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public ProgramItem getProgramItem(){
        ProgramItem pi = new ProgramItem();
        pi.setTitle(title);
        pi.setDescription(detail);
        pi.setCategory(category);
        pi.setStart(start);
        pi.setEnd(end);
        pi.setSeconds(seconds);
        pi.setId(id);
        pi.setSubtitle(subTitle);
        pi.setChannelId(channel.getId());
        pi.setChannelName(channel.getName());

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd E HH:mm", Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault());

        pi.setDate(sdf1.format(start)+"-"+sdf2.format(end) + " " + (seconds / 60) + "min");

        pi.setSimpleDate(sdf2.format(start));

        return pi;
    }

}