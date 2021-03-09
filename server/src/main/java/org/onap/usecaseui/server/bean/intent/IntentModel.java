package org.onap.usecaseui.server.bean.intent;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="intent_model")
public class IntentModel implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "size")
    private Float size;

    @Column(name = "active")
    private Integer active;

    public IntentModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}