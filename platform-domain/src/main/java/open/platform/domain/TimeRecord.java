package open.platform.domain;

import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Author: root
 * Date:2016/1/25
 */
public class TimeRecord {
    private Long id;

    private Long userId;

    private String username;

    private Integer onlineFlag; //上班是否打卡

    private Date onlineDate;

    private Integer offlineFlag; //下班是否打卡

    private Date offlineDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getOnlineFlag() {
        return onlineFlag;
    }

    public void setOnlineFlag(Integer onlineFlag) {
        this.onlineFlag = onlineFlag;
    }

    public Date getOnlineDate() {
        return onlineDate;
    }

    public void setOnlineDate(Date onlineDate) {
        this.onlineDate = onlineDate;
    }

    public Integer getOfflineFlag() {
        return offlineFlag;
    }

    public void setOfflineFlag(Integer offlineFlag) {
        this.offlineFlag = offlineFlag;
    }

    public Date getOfflineDate() {
        return offlineDate;
    }

    public void setOfflineDate(Date offlineDate) {
        this.offlineDate = offlineDate;
    }
}
