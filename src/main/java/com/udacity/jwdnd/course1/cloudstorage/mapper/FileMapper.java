package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface FileMapper {
    @Select("select * from FILES where userId = #{userId}")
    List<File> getListFileByUserId(Integer userId);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, userId, fileData) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void addFile(File file);

    @Delete("delete from FILEs where fileId = #{fileId}")
    void deleteFileById(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userId = #{userId} AND fileName = #{fileName}")
    File checkFile(String fileName,Integer userId);

    @Select("select * from FILES where fileId = #{fileId}")
    File getFileById(Integer fileId);

}
