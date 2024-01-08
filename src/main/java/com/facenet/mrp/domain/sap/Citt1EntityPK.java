package com.facenet.mrp.domain.sap;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class Citt1EntityPK implements Serializable {
    private int docEntry;
    private int lineId;

    @Column(name = "DocEntry")
    @Id
    public int getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(int docEntry) {
        this.docEntry = docEntry;
    }

    @Column(name = "LineId")
    @Id
    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Citt1EntityPK that = (Citt1EntityPK) o;
        return docEntry == that.docEntry && lineId == that.lineId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(docEntry, lineId);
    }
}
