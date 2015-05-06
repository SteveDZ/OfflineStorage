package be.ordina.offlinestorage.utils;

import java.util.Date;

/**
 * Created by stevedezitter on 05/05/15.
 */
public final class PublicKeyInfo {
    private final String subject;
    private final String issuer;
    private final Date validFrom;
    private final Date validUntil;

    public PublicKeyInfo(String subject, String issuer, Date validFrom, Date validUntil) {
        this.subject = subject;
        this.issuer = issuer;
        this.validFrom = new Date(validFrom.getTime());
        this.validUntil = new Date(validUntil.getTime());
    }

    public String getSubject() {
        return subject;
    }

    public String getIssuer() {
        return issuer;
    }

    public Date getValidFrom() {
        return new Date(validFrom.getTime());
    }

    public Date getValidUntil() {
        return new Date(validUntil.getTime());
    }
}
