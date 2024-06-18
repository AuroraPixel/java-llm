package com.hailiang.langchain4jdemo.pojo.gitlab.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * gitLab差异信息详情
 */
@Data
public class DiffDetail {
    /**
     * 差异内容
     */
    private String diff;

    /**
     * 新文件路径
     */
    @JsonProperty("new_path")
    private String newPath;

    /**
     * 旧文件路径
     */
    @JsonProperty("old_path")
    private String oldPath;

    /**
     * 新文件模式
     */
    @JsonProperty("a_mode")
    private String aMode;

    /**
     * 旧文件模式
     */
    @JsonProperty("b_mode")
    private String bMode;

    /**
     * 是否为新文件
     */
    @JsonProperty("new_file")
    private boolean newFile;

    /**
     * 是否重命名文件
     */
    @JsonProperty("renamed_file")
    private boolean renamedFile;

    /**
     * 是否删除文件
     */
    @JsonProperty("deleted_file")
    private boolean deletedFile;
}
