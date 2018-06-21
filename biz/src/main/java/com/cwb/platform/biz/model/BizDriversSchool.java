package com.cwb.platform.biz.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "biz_drivers_school")
public class BizDriversSchool implements Serializable {
    /**
     * 驾校代码
     */
    @Id
    @Column(name = "school_code")
    @GeneratedValue(generator = "JDBC")
    private String schoolCode;

    /**
     * 区县行政区划代码
     */
    @Column(name = "region_code")
    private String regionCode;

    /**
     * 培训机构名称
     */
    @Column(name = "school_name")
    private String schoolName;

    /**
     * 培训机构简称
     */
    @Column(name = "school_short_name")
    private String schoolShortName;

    /**
     * 经营许可证编号
     */
    @Column(name = "license_number")
    private String licenseNumber;

    /**
     * 经营许可日期
     */
    @Column(name = "license_date")
    private String licenseDate;

    /**
     * 营业执照注册号
     */
    @Column(name = "business_license_code")
    private String businessLicenseCode;

    /**
     * 统一社会信用代码
     */
    @Column(name = "social_credit_code")
    private String socialCreditCode;

    /**
     * 营业执照副本电子照
     */
    @Column(name = "yyzz_file_id")
    private String yyzzFileId;

    /**
     * 经营许可证副本电子照
     */
    @Column(name = "jyxkz_file_id")
    private String jyxkzFileId;

    /**
     * 培训机构地址
     */
    @Column(name = "school_address")
    private String schoolAddress;

    /**
     * 邮政编码
     */
    @Column(name = "postal_code")
    private String postalCode;

    /**
     * 法人代表
     */
    @Column(name = "legal_person")
    private String legalPerson;

    /**
     * 联系人
     */
    @Column(name = "link_man")
    private String linkMan;

    /**
     * 联系电话
     */
    @Column(name = "link_tel")
    private String linkTel;

    /**
     * 注册资金
     */
    @Column(name = "registered_capital")
    private String registeredCapital;

    /**
     * 经营范围
     */
    @Column(name = "business_scope")
    private String businessScope;

    /**
     * 经营状态
     */
    @Column(name = "business_state")
    private String businessState;

    /**
     * 驾校级别
     */
    @Column(name = "school_level")
    private String schoolLevel;

    /**
     * 分类等级
     */
    @Column(name = "classify_level")
    private String classifyLevel;

    /**
     * 可培训车型
     */
    @Column(name = "trainable_vehicle_type")
    private String trainableVehicleType;

    /**
     * 教练员总数
     */
    @Column(name = "coach_total")
    private Integer coachTotal;

    /**
     * 考核员总数
     */
    @Column(name = "check_total")
    private Integer checkTotal;

    /**
     * 安全员总数
     */
    @Column(name = "safety_officer_total")
    private Integer safetyOfficerTotal;

    /**
     * 教练车总数
     */
    @Column(name = "car_total")
    private Integer carTotal;

    /**
     * 教室总面积
     */
    @Column(name = "total_area_classroom")
    private String totalAreaClassroom;

    /**
     * 理论教室面积
     */
    @Column(name = "total_area_lljs")
    private String totalAreaLljs;

    /**
     * 教练场总面积
     */
    @Column(name = "total_area_jlc")
    private String totalAreaJlc;

    /**
     * 驾校状态
     */
    private String state;

    /**
     * bj
     */
    private String bj;

    private Double lat;
    private Double lng;

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
     * 背景图片
     */
    @Column(name = "bg_file_id")
    private String bgFileId;

    /**
     * 基本信息表
     */
    @Column(name = "base_info_table")
    private String baseInfoTable;

    /**
     * 道路运输许可证副本
     */
    @Column(name = "dlysxkz_fb")
    private String dlysxkzFb;

    /**
     * 道路运输许可证正本
     */
    @Column(name = "dlysxkz_zb")
    private String dlysxkzZb;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    private static final long serialVersionUID = 1L;

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
     * 获取区县行政区划代码
     *
     * @return region_code - 区县行政区划代码
     */
    public String getRegionCode() {
        return regionCode;
    }

    /**
     * 设置区县行政区划代码
     *
     * @param regionCode 区县行政区划代码
     */
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    /**
     * 获取培训机构名称
     *
     * @return school_name - 培训机构名称
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * 设置培训机构名称
     *
     * @param schoolName 培训机构名称
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    /**
     * 获取培训机构简称
     *
     * @return school_short_name - 培训机构简称
     */
    public String getSchoolShortName() {
        return schoolShortName;
    }

    /**
     * 设置培训机构简称
     *
     * @param schoolShortName 培训机构简称
     */
    public void setSchoolShortName(String schoolShortName) {
        this.schoolShortName = schoolShortName;
    }

    /**
     * 获取经营许可证编号
     *
     * @return license_number - 经营许可证编号
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * 设置经营许可证编号
     *
     * @param licenseNumber 经营许可证编号
     */
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    /**
     * 获取经营许可日期
     *
     * @return license_date - 经营许可日期
     */
    public String getLicenseDate() {
        return licenseDate;
    }

    /**
     * 设置经营许可日期
     *
     * @param licenseDate 经营许可日期
     */
    public void setLicenseDate(String licenseDate) {
        this.licenseDate = licenseDate;
    }

    /**
     * 获取营业执照注册号
     *
     * @return business_license_code - 营业执照注册号
     */
    public String getBusinessLicenseCode() {
        return businessLicenseCode;
    }

    /**
     * 设置营业执照注册号
     *
     * @param businessLicenseCode 营业执照注册号
     */
    public void setBusinessLicenseCode(String businessLicenseCode) {
        this.businessLicenseCode = businessLicenseCode;
    }

    /**
     * 获取统一社会信用代码
     *
     * @return social_credit_code - 统一社会信用代码
     */
    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    /**
     * 设置统一社会信用代码
     *
     * @param socialCreditCode 统一社会信用代码
     */
    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    /**
     * 获取营业执照副本电子照
     *
     * @return yyzz_file_id - 营业执照副本电子照
     */
    public String getYyzzFileId() {
        return yyzzFileId;
    }

    /**
     * 设置营业执照副本电子照
     *
     * @param yyzzFileId 营业执照副本电子照
     */
    public void setYyzzFileId(String yyzzFileId) {
        this.yyzzFileId = yyzzFileId;
    }

    /**
     * 获取经营许可证副本电子照
     *
     * @return jyxkz_file_id - 经营许可证副本电子照
     */
    public String getJyxkzFileId() {
        return jyxkzFileId;
    }

    /**
     * 设置经营许可证副本电子照
     *
     * @param jyxkzFileId 经营许可证副本电子照
     */
    public void setJyxkzFileId(String jyxkzFileId) {
        this.jyxkzFileId = jyxkzFileId;
    }

    /**
     * 获取培训机构地址
     *
     * @return school_address - 培训机构地址
     */
    public String getSchoolAddress() {
        return schoolAddress;
    }

    /**
     * 设置培训机构地址
     *
     * @param schoolAddress 培训机构地址
     */
    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    /**
     * 获取邮政编码
     *
     * @return postal_code - 邮政编码
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * 设置邮政编码
     *
     * @param postalCode 邮政编码
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * 获取法人代表
     *
     * @return legal_person - 法人代表
     */
    public String getLegalPerson() {
        return legalPerson;
    }

    /**
     * 设置法人代表
     *
     * @param legalPerson 法人代表
     */
    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    /**
     * 获取联系人
     *
     * @return link_man - 联系人
     */
    public String getLinkMan() {
        return linkMan;
    }

    /**
     * 设置联系人
     *
     * @param linkMan 联系人
     */
    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    /**
     * 获取联系电话
     *
     * @return link_tel - 联系电话
     */
    public String getLinkTel() {
        return linkTel;
    }

    /**
     * 设置联系电话
     *
     * @param linkTel 联系电话
     */
    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    /**
     * 获取注册资金
     *
     * @return registered_capital - 注册资金
     */
    public String getRegisteredCapital() {
        return registeredCapital;
    }

    /**
     * 设置注册资金
     *
     * @param registeredCapital 注册资金
     */
    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    /**
     * 获取经营范围
     *
     * @return business_scope - 经营范围
     */
    public String getBusinessScope() {
        return businessScope;
    }

    /**
     * 设置经营范围
     *
     * @param businessScope 经营范围
     */
    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    /**
     * 获取经营状态
     *
     * @return business_state - 经营状态
     */
    public String getBusinessState() {
        return businessState;
    }

    /**
     * 设置经营状态
     *
     * @param businessState 经营状态
     */
    public void setBusinessState(String businessState) {
        this.businessState = businessState;
    }

    /**
     * 获取驾校级别
     *
     * @return school_level - 驾校级别
     */
    public String getSchoolLevel() {
        return schoolLevel;
    }

    /**
     * 设置驾校级别
     *
     * @param schoolLevel 驾校级别
     */
    public void setSchoolLevel(String schoolLevel) {
        this.schoolLevel = schoolLevel;
    }

    /**
     * 获取分类等级
     *
     * @return classify_level - 分类等级
     */
    public String getClassifyLevel() {
        return classifyLevel;
    }

    /**
     * 设置分类等级
     *
     * @param classifyLevel 分类等级
     */
    public void setClassifyLevel(String classifyLevel) {
        this.classifyLevel = classifyLevel;
    }

    /**
     * 获取可培训车型
     *
     * @return trainable_vehicle_type - 可培训车型
     */
    public String getTrainableVehicleType() {
        return trainableVehicleType;
    }

    /**
     * 设置可培训车型
     *
     * @param trainableVehicleType 可培训车型
     */
    public void setTrainableVehicleType(String trainableVehicleType) {
        this.trainableVehicleType = trainableVehicleType;
    }

    /**
     * 获取教练员总数
     *
     * @return coach_total - 教练员总数
     */
    public Integer getCoachTotal() {
        return coachTotal;
    }

    /**
     * 设置教练员总数
     *
     * @param coachTotal 教练员总数
     */
    public void setCoachTotal(Integer coachTotal) {
        this.coachTotal = coachTotal;
    }

    /**
     * 获取考核员总数
     *
     * @return check_total - 考核员总数
     */
    public Integer getCheckTotal() {
        return checkTotal;
    }

    /**
     * 设置考核员总数
     *
     * @param checkTotal 考核员总数
     */
    public void setCheckTotal(Integer checkTotal) {
        this.checkTotal = checkTotal;
    }

    /**
     * 获取安全员总数
     *
     * @return safety_officer_total - 安全员总数
     */
    public Integer getSafetyOfficerTotal() {
        return safetyOfficerTotal;
    }

    /**
     * 设置安全员总数
     *
     * @param safetyOfficerTotal 安全员总数
     */
    public void setSafetyOfficerTotal(Integer safetyOfficerTotal) {
        this.safetyOfficerTotal = safetyOfficerTotal;
    }

    /**
     * 获取教练车总数
     *
     * @return car_total - 教练车总数
     */
    public Integer getCarTotal() {
        return carTotal;
    }

    /**
     * 设置教练车总数
     *
     * @param carTotal 教练车总数
     */
    public void setCarTotal(Integer carTotal) {
        this.carTotal = carTotal;
    }

    /**
     * 获取教室总面积
     *
     * @return total_area_classroom - 教室总面积
     */
    public String getTotalAreaClassroom() {
        return totalAreaClassroom;
    }

    /**
     * 设置教室总面积
     *
     * @param totalAreaClassroom 教室总面积
     */
    public void setTotalAreaClassroom(String totalAreaClassroom) {
        this.totalAreaClassroom = totalAreaClassroom;
    }

    /**
     * 获取理论教室面积
     *
     * @return total_area_lljs - 理论教室面积
     */
    public String getTotalAreaLljs() {
        return totalAreaLljs;
    }

    /**
     * 设置理论教室面积
     *
     * @param totalAreaLljs 理论教室面积
     */
    public void setTotalAreaLljs(String totalAreaLljs) {
        this.totalAreaLljs = totalAreaLljs;
    }

    /**
     * 获取教练场总面积
     *
     * @return total_area_jlc - 教练场总面积
     */
    public String getTotalAreaJlc() {
        return totalAreaJlc;
    }

    /**
     * 设置教练场总面积
     *
     * @param totalAreaJlc 教练场总面积
     */
    public void setTotalAreaJlc(String totalAreaJlc) {
        this.totalAreaJlc = totalAreaJlc;
    }

    /**
     * 获取驾校状态
     *
     * @return state - 驾校状态
     */
    public String getState() {
        return state;
    }

    /**
     * 设置驾校状态
     *
     * @param state 驾校状态
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取bj
     *
     * @return bj - bj
     */
    public String getBj() {
        return bj;
    }

    /**
     * 设置bj
     *
     * @param bj bj
     */
    public void setBj(String bj) {
        this.bj = bj;
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
     * 获取背景图片
     *
     * @return bg_file_id - 背景图片
     */
    public String getBgFileId() {
        return bgFileId;
    }

    /**
     * 设置背景图片
     *
     * @param bgFileId 背景图片
     */
    public void setBgFileId(String bgFileId) {
        this.bgFileId = bgFileId;
    }

    /**
     * 获取基本信息表
     *
     * @return base_info_table - 基本信息表
     */
    public String getBaseInfoTable() {
        return baseInfoTable;
    }

    /**
     * 设置基本信息表
     *
     * @param baseInfoTable 基本信息表
     */
    public void setBaseInfoTable(String baseInfoTable) {
        this.baseInfoTable = baseInfoTable;
    }

    /**
     * 获取道路运输许可证副本
     *
     * @return dlysxkz_fb - 道路运输许可证副本
     */
    public String getDlysxkzFb() {
        return dlysxkzFb;
    }

    /**
     * 设置道路运输许可证副本
     *
     * @param dlysxkzFb 道路运输许可证副本
     */
    public void setDlysxkzFb(String dlysxkzFb) {
        this.dlysxkzFb = dlysxkzFb;
    }

    /**
     * 获取道路运输许可证正本
     *
     * @return dlysxkz_zb - 道路运输许可证正本
     */
    public String getDlysxkzZb() {
        return dlysxkzZb;
    }

    /**
     * 设置道路运输许可证正本
     *
     * @param dlysxkzZb 道路运输许可证正本
     */
    public void setDlysxkzZb(String dlysxkzZb) {
        this.dlysxkzZb = dlysxkzZb;
    }

    public enum InnerColumn {
        schoolCode("school_code"),
        regionCode("region_code"),
        schoolName("school_name"),
        schoolShortName("school_short_name"),
        licenseNumber("license_number"),
        licenseDate("license_date"),
        businessLicenseCode("business_license_code"),
        socialCreditCode("social_credit_code"),
        yyzzFileId("yyzz_file_id"),
        jyxkzFileId("jyxkz_file_id"),
        schoolAddress("school_address"),
        postalCode("postal_code"),
        legalPerson("legal_person"),
        linkMan("link_man"),
        linkTel("link_tel"),
        registeredCapital("registered_capital"),
        businessScope("business_scope"),
        businessState("business_state"),
        schoolLevel("school_level"),
        classifyLevel("classify_level"),
        trainableVehicleType("trainable_vehicle_type"),
        coachTotal("coach_total"),
        checkTotal("check_total"),
        safetyOfficerTotal("safety_officer_total"),
        carTotal("car_total"),
        totalAreaClassroom("total_area_classroom"),
        totalAreaLljs("total_area_lljs"),
        totalAreaJlc("total_area_jlc"),
        state("state"),
        bj("bj"),
        createUser("create_user"),
        createTime("create_time"),
        auditor("auditor"),
        auditorTime("auditor_time"),
        updateTime("update_time"),
        remark("remark"),
        bgFileId("bg_file_id"),
        baseInfoTable("base_info_table"),
        dlysxkzFb("dlysxkz_fb"),
        dlysxkzZb("dlysxkz_zb");

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