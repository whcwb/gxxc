package com.cwb.platform.biz.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "biz_train_place")
public class BizTrainPlace implements Serializable {
    /**
     * 驾校编号+两位编码
     */
    @Id
    @Column(name = "place_id")
    @GeneratedValue(generator = "JDBC")
    private String placeId;
    /**
     * 区县行政区划代码
     */
    @Column(name = "region_code")
    private String regionCode;
    /**
     * 驾校代码
     */
    @Column(name = "school_code")
    private String schoolCode;

    /**
     * 训练场地编号
     */
    @Column(name = "place_number")
    private String placeNumber;

    /**
     * 训练场地名称
     */
    @Column(name = "place_name")
    private String placeName;

    /**
     * 场地缩略图
     */
    @Column(name = "place_icon")
    private String placeIcon;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 地址
     */
    private String address;

    /**
     * 实测面积
     */
    @Column(name = "measured_area")
    private String measuredArea;

    /**
     * 核实面积
     */
    @Column(name = "verification_area")
    private String verificationArea;

    /**
     * 状态
     */
    private String state;

    /**
     * 经办人
     */
    @Column(name = "create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 审核时间
     */
    @Column(name = "auditor_time")
    private String auditorTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private String updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 培训车型
     */
    @Column(name = "tech_driver_type")
    private String techDriverType;

    /**
     * 围栏面积
     */
    @Column(name = "enclosure_area")
    private String enclosureArea;

    /**
     * 驾校名称
     */
    @Column(name = "school_name")
    private String schoolName;

    /**
     * 备案图片
     */
    @Column(name = "place_beianImg")
    private String placeBeianimg;

    @Column(name = "place_img")
    private String placeImg;

    @Column(name = "place_cover_img")
    private String placeCoverImg;

    public String getPlaceCoverImg() {
        return placeCoverImg;
    }

    public void setPlaceCoverImg(String placeCoverImg) {
        this.placeCoverImg = placeCoverImg;
    }

    public String getPlaceImg() {
        return placeImg;
    }

    public void setPlaceImg(String placeImg) {
        this.placeImg = placeImg;
    }

    /**
     * 车辆数
     */
    @Column(name = "approved_car_num")
    private String approvedCarNum;

    /**
     * 地理信息坐标（场地电子地图）
     */
    @Column(name = "place_coordinate")
    private String placeCoordinate;
    /**
     * GPS点位的距离
     */
    @Transient
    private Double distance;

    private static final long serialVersionUID = 1L;

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    /**
     * 获取驾校编号+两位编码
     *
     * @return place_id - 驾校编号+两位编码
     */
    public String getPlaceId() {
        return placeId;
    }

    /**
     * 设置驾校编号+两位编码
     *
     * @param placeId 驾校编号+两位编码
     */
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    /**
     * 获取驾校代码
     *
     * @return school_code - 驾校代码
     */
    public String getSchoolCode() {
        return schoolCode;
    }

    /**
     * 设置驾校代码
     *
     * @param schoolCode 驾校代码
     */
    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    /**
     * 获取训练场地编号
     *
     * @return place_number - 训练场地编号
     */
    public String getPlaceNumber() {
        return placeNumber;
    }

    /**
     * 设置训练场地编号
     *
     * @param placeNumber 训练场地编号
     */
    public void setPlaceNumber(String placeNumber) {
        this.placeNumber = placeNumber;
    }

    /**
     * 获取训练场地名称
     *
     * @return place_name - 训练场地名称
     */
    public String getPlaceName() {
        return placeName;
    }

    /**
     * 设置训练场地名称
     *
     * @param placeName 训练场地名称
     */
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    /**
     * 获取场地缩略图
     *
     * @return place_icon - 场地缩略图
     */
    public String getPlaceIcon() {
        return placeIcon;
    }

    /**
     * 设置场地缩略图
     *
     * @param placeIcon 场地缩略图
     */
    public void setPlaceIcon(String placeIcon) {
        this.placeIcon = placeIcon;
    }

    /**
     * 获取经度
     *
     * @return longitude - 经度
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * 设置经度
     *
     * @param longitude 经度
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取纬度
     *
     * @return latitude - 纬度
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * 设置纬度
     *
     * @param latitude 纬度
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取实测面积
     *
     * @return measured_area - 实测面积
     */
    public String getMeasuredArea() {
        return measuredArea;
    }

    /**
     * 设置实测面积
     *
     * @param measuredArea 实测面积
     */
    public void setMeasuredArea(String measuredArea) {
        this.measuredArea = measuredArea;
    }

    /**
     * 获取核实面积
     *
     * @return verification_area - 核实面积
     */
    public String getVerificationArea() {
        return verificationArea;
    }

    /**
     * 设置核实面积
     *
     * @param verificationArea 核实面积
     */
    public void setVerificationArea(String verificationArea) {
        this.verificationArea = verificationArea;
    }

    /**
     * 获取状态
     *
     * @return state - 状态
     */
    public String getState() {
        return state;
    }

    /**
     * 设置状态
     *
     * @param state 状态
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取经办人
     *
     * @return create_user - 经办人
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置经办人
     *
     * @param createUser 经办人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取审核人
     *
     * @return auditor - 审核人
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * 设置审核人
     *
     * @param auditor 审核人
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    /**
     * 获取审核时间
     *
     * @return auditor_time - 审核时间
     */
    public String getAuditorTime() {
        return auditorTime;
    }

    /**
     * 设置审核时间
     *
     * @param auditorTime 审核时间
     */
    public void setAuditorTime(String auditorTime) {
        this.auditorTime = auditorTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取培训车型
     *
     * @return tech_driver_type - 培训车型
     */
    public String getTechDriverType() {
        return techDriverType;
    }

    /**
     * 设置培训车型
     *
     * @param techDriverType 培训车型
     */
    public void setTechDriverType(String techDriverType) {
        this.techDriverType = techDriverType;
    }

    /**
     * 获取围栏面积
     *
     * @return enclosure_area - 围栏面积
     */
    public String getEnclosureArea() {
        return enclosureArea;
    }

    /**
     * 设置围栏面积
     *
     * @param enclosureArea 围栏面积
     */
    public void setEnclosureArea(String enclosureArea) {
        this.enclosureArea = enclosureArea;
    }

    /**
     * 获取驾校名称
     *
     * @return school_name - 驾校名称
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * 设置驾校名称
     *
     * @param schoolName 驾校名称
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    /**
     * 获取备案图片
     *
     * @return place_beianImg - 备案图片
     */
    public String getPlaceBeianimg() {
        return placeBeianimg;
    }

    /**
     * 设置备案图片
     *
     * @param placeBeianimg 备案图片
     */
    public void setPlaceBeianimg(String placeBeianimg) {
        this.placeBeianimg = placeBeianimg;
    }

    /**
     * 获取车辆数
     *
     * @return approved_car_num - 车辆数
     */
    public String getApprovedCarNum() {
        return approvedCarNum;
    }

    /**
     * 设置车辆数
     *
     * @param approvedCarNum 车辆数
     */
    public void setApprovedCarNum(String approvedCarNum) {
        this.approvedCarNum = approvedCarNum;
    }

    /**
     * 获取地理信息坐标（场地电子地图）
     *
     * @return place_coordinate - 地理信息坐标（场地电子地图）
     */
    public String getPlaceCoordinate() {
        return placeCoordinate;
    }

    /**
     * 设置地理信息坐标（场地电子地图）
     *
     * @param placeCoordinate 地理信息坐标（场地电子地图）
     */
    public void setPlaceCoordinate(String placeCoordinate) {
        this.placeCoordinate = placeCoordinate;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public enum InnerColumn {
        placeId("place_id"),
        schoolCode("school_code"),
        placeNumber("place_number"),
        placeName("place_name"),
        placeIcon("place_icon"),
        longitude("longitude"),
        latitude("latitude"),
        address("address"),
        measuredArea("measured_area"),
        verificationArea("verification_area"),
        state("state"),
        createUser("create_user"),
        createTime("create_time"),
        auditor("auditor"),
        auditorTime("auditor_time"),
        updateTime("update_time"),
        remark("remark"),
        techDriverType("tech_driver_type"),
        enclosureArea("enclosure_area"),
        schoolName("school_name"),
        placeBeianimg("place_beianImg"),
        approvedCarNum("approved_car_num"),
        regionCode("region_code"),
        placeCoordinate("place_coordinate");

        private final String column;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        InnerColumn(String column) {
            this.column = column;
        }

        public String desc() {
            return this.column + " DESC";
        }

        public String asc() {
            return this.column + " ASC";
        }
    }
}