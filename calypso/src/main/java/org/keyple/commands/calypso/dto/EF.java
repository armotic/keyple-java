package org.keyple.commands.calypso.dto;

import java.util.Arrays;

/**
 * The Class EF. EF:Elementary File, as defined in ISO/IEC 7816-4. File
 * containing data. The types of EF defined by Calypso are: Linear,
 * Cyclic,Counters, Simulated Counter and Binary files.
 */
public class EF {

    /** The lid.(Long File Identifier.) */
    private byte[] lid;

    /** The sfi. */
    private byte sfi;

    /** The file type. */
    private byte fileType;

    /** The rec size. */
    private byte recSize;

    /** The number rec. */
    private byte numberRec;

    /**
     * Instantiates a new EF.
     */
    public EF() {
    }

    /**
     * Instantiates a new EF.
     *
     * @param lid
     *            the lid
     * @param sfi
     *            the sfi
     * @param fileType
     *            the file type
     * @param recSize
     *            the rec size
     * @param numberRec
     *            the number rec
     */
    public EF(byte[] lid, byte sfi, byte fileType, byte recSize, byte numberRec) {
        this.lid = lid;
        this.sfi = sfi;
        this.fileType = fileType;
        this.recSize = recSize;
        this.numberRec = numberRec;
    }

    /**
     * Gets the lid.
     *
     * @return the lid
     */
    public byte[] getLid() {
        return lid;
    }

    /**
     * Gets the sfi.
     *
     * @return the sfi
     */
    public byte getSfi() {
        return sfi;
    }

    /**
     * Gets the file type.
     *
     * @return the file type
     */
    public byte getFileType() {
        return fileType;
    }

    /**
     * Gets the file type name.
     *
     * @return the file type name
     */
    public String getFileTypeName() {
        switch (fileType) {
        case (byte) 0x00:
            return "DF";
        case (byte) 0x01:
            return "Binary file";
        case (byte) 0x02:
            return "Linear file";
        case (byte) 0x04:
            return "Cyclic file";
        case (byte) 0x08:
            return "Simulated Counter file";
        case (byte) 0x09:
            return "Counter file";
        default:
            return "";
        }
    }

    /**
     * Gets the rec size.
     *
     * @return the rec size
     */
    public byte getRecSize() {
        return recSize;
    }

    /**
     * Gets the number rec.
     *
     * @return the number rec
     */
    public byte getNumberRec() {
        return numberRec;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + fileType;
        result = prime * result + Arrays.hashCode(lid);
        result = prime * result + numberRec;
        result = prime * result + recSize;
        result = prime * result + sfi;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEquals = false;

        if (this == obj) {
            isEquals = true;
        }
        if (obj != null) {
            if (obj.getClass() == getClass()) {
                EF other = (EF) obj;
                if ((fileType != other.fileType) || !Arrays.equals(lid, other.lid) || numberRec != other.numberRec
                        || recSize != other.recSize || sfi != other.sfi) {
                    isEquals = false;
                }
            } else {
                isEquals = false;
            }
        } else {
            isEquals = false;
        }

        return isEquals;
    }

    /**
     * Sets the lid.
     *
     * @param lid
     *            the new lid
     */
    public void setLid(byte[] lid) {
        this.lid = lid;
    }

    /**
     * Sets the sfi.
     *
     * @param sfi
     *            the new sfi
     */
    public void setSfi(byte sfi) {
        this.sfi = sfi;
    }

    /**
     * Sets the file type.
     *
     * @param fileType
     *            the new file type
     */
    public void setFileType(byte fileType) {
        this.fileType = fileType;
    }

    /**
     * Sets the rec size.
     *
     * @param recSize
     *            the new rec size
     */
    public void setRecSize(byte recSize) {
        this.recSize = recSize;
    }

    /**
     * Sets the number rec.
     *
     * @param numberRec
     *            the new number rec
     */
    public void setNumberRec(byte numberRec) {
        this.numberRec = numberRec;
    }

}