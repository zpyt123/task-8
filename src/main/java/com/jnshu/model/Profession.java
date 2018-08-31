package com.jnshu.model;

import java.io.Serializable;

public class Profession implements Serializable{
    private static final long serialVersionUID = 4871815182434372089L;
    private Long id;
    private String proName;
    private String introducation;
    private Integer companyCount;
    private Long creat_at;
    private  Long update_at;
    private  Long workDiffcult ;
    private  Long workYear;
    private  Long needs;
    private  Long payMin;
    private  Long payMax;

    public  Profession(){}
    @Override
    public String toString() {
        return "Profession{" +
                "id=" + id +
                ", proName='" + proName + '\'' +
                ", introducation='" + introducation + '\'' +
                ", companyCount=" + companyCount +
                ", creat_at=" + creat_at +
                ", update_at=" + update_at +
                ", workDiffcult=" + workDiffcult +
                ", workYear=" + workYear +
                ", needs=" + needs +
                ", payMin=" + payMin +
                ", payMax=" + payMax +
                '}';
    }

    public Long getWorkYear() {
        return workYear;
    }

    public void setWorkYear(Long workYear) {
        this.workYear = workYear;
    }

    public Long getNeeds() {
        return needs;
    }

    public void setNeeds(Long needs) {
        this.needs = needs;
    }

    public Long getPayMin() {
        return payMin;
    }

    public void setPayMin(Long payMin) {
        this.payMin = payMin;
    }

    public Long getPayMax() {
        return payMax;
    }

    public void setPayMax(Long payMax) {
        this.payMax = payMax;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProName() {
        return proName;

    }
    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getIntroducation() {
        return introducation;
    }

    public void setIntroducation(String introducation) {
        this.introducation = introducation;
    }

    public Integer getCompanyCount() {
        return companyCount;
    }

    public void setCompanyCount(Integer companyCount) {
        this.companyCount = companyCount;
    }

    public Long getCreat_at() {
        return creat_at;
    }

    public void setCreat_at(Long creat_at) {
        this.creat_at = creat_at;
    }

    public Long getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Long update_at) {
        this.update_at = update_at;
    }

    public Long getWorkDiffcult() {
        return workDiffcult;
    }

    public void setWorkDiffcult(Long workDiffcult) {
        this.workDiffcult = workDiffcult;
    }









}
