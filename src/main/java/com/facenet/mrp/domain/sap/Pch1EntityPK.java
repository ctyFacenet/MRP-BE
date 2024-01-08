package com.facenet.mrp.domain.sap;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class Pch1EntityPK implements Serializable {
    private int docEntry;
    private int lineNum;

    @Column(name = "DocEntry")
    @Id
    public int getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(int docEntry) {
        this.docEntry = docEntry;
    }

    @Column(name = "LineNum")
    @Id
    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pch1EntityPK that = (Pch1EntityPK) o;
        return docEntry == that.docEntry && lineNum == that.lineNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(docEntry, lineNum);
    }
}
