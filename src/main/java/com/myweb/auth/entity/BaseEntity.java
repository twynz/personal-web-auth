package com.myweb.auth.entity;

import com.myweb.auth.utils.TimestampAdapter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 8388417013613884409L;

    @XmlJavaTypeAdapter(TimestampAdapter.class)
    private Timestamp createTime;

    @XmlJavaTypeAdapter(TimestampAdapter.class)
    private Timestamp updateTime;

    private UUID createBy;

    private UUID updateBy;

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public UUID getCreateBy() {
        return createBy;
    }

    public void setCreateBy(UUID createBy) {
        this.createBy = createBy;
    }

    public UUID getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(UUID updateBy) {
        this.updateBy = updateBy;
    }

}
