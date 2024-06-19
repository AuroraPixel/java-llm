package com.hailiang.langchain4jdemo.pojo.gitlab.detail;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    private Boolean newFile;

    /**
     * 是否重命名文件
     */
    @JsonProperty("renamed_file")
    private Boolean renamedFile;

    /**
     * 是否删除文件
     */
    @JsonProperty("deleted_file")
    private Boolean deletedFile;


    /**
     *  获取差异前的信息
     * @return
     */
    public String getBeforeDiff() {
        if (StrUtil.isEmpty(diff)) {
            return "";
        }
        return getChangedBlock(diff, true);
    }


    /**
     * 获取差异后的信息
     * @return
     */
    public String getAfterDiff() {
        if (StrUtil.isEmpty(diff)) {
            return "";
        }
        return getChangedBlock(diff, false);
    }

    public String getBeforeAndAfterDiff(){
        return "代码名:\n"+getCodeName()+"\n"+"变更前:\n" + getBeforeDiff() + "\n" + "变更后:\n" + getAfterDiff();
    }

    public String getCodeName(){
        if(StrUtil.isNotEmpty(newPath)){
            String[] split = newPath.split("/");
            if(split.length > 0){
                return split[split.length - 1];
            }
        }
        return "";
    }

    private String getChangedBlock(String diff, boolean isOldVersion) {
        StringBuilder result = new StringBuilder();
        String[] diffLines = diff.split("\n");
        int originalLineNumber = 0;
        int newLineNumber = 0;
        boolean inChangedBlock = false;

        for (String line : diffLines) {
            if (line.startsWith("@@")) {
                // Parse the line numbers from the diff hunk header
                String[] parts = line.split(" ");
                String[] originalLineInfo = parts[1].split(",");
                String[] newLineInfo = parts[2].split(",");

                originalLineNumber = Integer.parseInt(originalLineInfo[0].substring(1));
                newLineNumber = Integer.parseInt(newLineInfo[0].substring(1));

                inChangedBlock = true;
            } else if (inChangedBlock) {
                if (line.startsWith(" ")) {
                    result.append(isOldVersion ? originalLineNumber : newLineNumber).append("  ").append(line.substring(1)).append("\n");
                    originalLineNumber++;
                    newLineNumber++;
                } else if (line.startsWith("-")) {
                    if (isOldVersion) {
                        result.append(originalLineNumber).append("- ").append(line.substring(1)).append("\n");
                    }
                    originalLineNumber++;
                } else if (line.startsWith("+")) {
                    if (!isOldVersion) {
                        result.append(newLineNumber).append("+ ").append(line.substring(1)).append("\n");
                    }
                    newLineNumber++;
                }
            }
        }

        return result.toString();
    }


}
