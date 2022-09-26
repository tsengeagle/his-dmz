package com.example.dmzserver.healthhub.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class UploadData {
    public String        ward_bed;
    public String        cusid;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public LocalDateTime measure_time;
    public String        vital_sign_type;
    public int           time_period_code;
    public int           data_source_code;
    public double        sbp;
    public double        dbp;
    public double        hb;
    public double        bs;
    public double        oxygen;
    public double        temperature;
    public double        respiration;
    public double        pain;

    @Override
    public String toString() {
        return "UploadData{" +
               "ward_bed='" + ward_bed + '\'' +
               ", cusid='" + cusid + '\'' +
               ", measure_time='" + measure_time + '\'' +
               ", vital_sign_type='" + vital_sign_type + '\'' +
               ", time_period_code=" + time_period_code +
               ", data_source_code=" + data_source_code +
               ", sbp=" + sbp +
               ", dbp=" + dbp +
               ", hb=" + hb +
               ", bs=" + bs +
               ", oxygen=" + oxygen +
               ", temperature=" + temperature +
               ", respiration=" + respiration +
               ", pain=" + pain +
               '}';
    }
}
