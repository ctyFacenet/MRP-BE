package com.facenet.mrp.domain.sap;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class Prq1EntityPK implements Serializable {
    @Column(name = "DocEntry")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer docEntry;
    @Column(name = "LineNum")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lineNum;

    public Integer getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Integer docEntry) {
        this.docEntry = docEntry;
    }

    public Integer getLineNum() {
        return lineNum;
    }

    public void setLineNum(Integer lineNum) {
        this.lineNum = lineNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prq1EntityPK that = (Prq1EntityPK) o;

        if (docEntry != null ? !docEntry.equals(that.docEntry) : that.docEntry != null) return false;
        if (lineNum != null ? !lineNum.equals(that.lineNum) : that.lineNum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = docEntry != null ? docEntry.hashCode() : 0;
        result = 31 * result + (lineNum != null ? lineNum.hashCode() : 0);
        return result;
    }
}
