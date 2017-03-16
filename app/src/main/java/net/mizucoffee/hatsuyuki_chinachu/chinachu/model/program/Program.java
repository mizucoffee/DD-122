package net.mizucoffee.hatsuyuki_chinachu.chinachu.model.program;

import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Program {

    private String id;
    private Channel channel;
    private String category;
    private String title;
    private String subTitle;
    private String fullTitle;
    private String detail;
    private Object episode;
    private Long start;
    private Long end;
    private Integer seconds;
    private List<String> flags = null;
    private Boolean isManualReserved;
    private Boolean isConflict;
    private Boolean isSigTerm;
    private Tuner tuner;
    private String recorded;



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

    public Boolean getIsManualReserved() {
        return isManualReserved;
    }

    public void setIsManualReserved(Boolean isManualReserved) {
        this.isManualReserved = isManualReserved;
    }

    public Boolean getIsConflict() {
        return isConflict;
    }

    public void setIsConflict(Boolean isConflict) {
        this.isConflict = isConflict;
    }

    public Boolean getIsSigTerm() {
        return isSigTerm;
    }

    public void setIsSigTerm(Boolean isSigTerm) {
        this.isSigTerm = isSigTerm;
    }

    public Tuner getTuner() {
        return tuner;
    }

    public void setTuner(Tuner tuner) {
        this.tuner = tuner;
    }

    public String getRecorded() {
        return recorded;
    }

    public void setRecorded(String recorded) {
        this.recorded = recorded;
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

    public ProgramItem getProgramItem(String server){
        ProgramItem pi = new ProgramItem();
        pi.setTitle(title);
        pi.setDescription(detail);
        pi.setThumbUrl("http://" + server + "/api/recorded/" + id + "/preview.png");
        pi.setCategory(category);
        pi.setStart(start);
        pi.setId(id);
        pi.setSubtitle(subTitle);
        pi.setChannelId(channel.getId());
        pi.setChannelName(channel.getName());
        pi.setChannelUrl("http://" + server + "/api/channel/" + channel.getId() + "/logo.png");

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd E HH:mm", Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault());

        pi.setDate(sdf1.format(start)+"-"+sdf2.format(end) + " " + (seconds / 60) + "min");

        return pi;
    }


}